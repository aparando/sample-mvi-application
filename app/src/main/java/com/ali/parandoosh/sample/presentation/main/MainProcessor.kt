package com.ali.parandoosh.sample.presentation.main

import com.ali.parandoosh.sample.domain.interactor.main.GetRestaurants
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MainProcessor @Inject constructor(private val getRestaurants: GetRestaurants) {

    private val conversationsProcessor: ObservableTransformer<
        MainAction.LoadRestaurants, MainResult> = ObservableTransformer {
        it.switchMap {
            getRestaurants.execute()
                .map {
                    MainResult.LoadRestaurantsTask.success(it)
                }
                .onErrorReturn {
                    MainResult.LoadRestaurantsTask.failure()
                }
                .toObservable()
                .startWith(MainResult.LoadRestaurantsTask.inFlight())
        }
    }

    var actionProcessor: ObservableTransformer<MainAction, MainResult>

    init {
        this.actionProcessor = ObservableTransformer {
            it.publish {
                it.ofType(MainAction.LoadRestaurants::class.java)
                    .compose(conversationsProcessor)
                    .mergeWith(it.filter { it !is MainAction.LoadRestaurants }
                        .flatMap {
                            Observable.error<MainResult>(
                                IllegalArgumentException("Unknown Action type")
                            )
                        })
            }
        }
    }

}