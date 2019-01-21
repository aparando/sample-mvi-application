package com.ali.parandoosh.sample.presentation.main

import android.arch.lifecycle.ViewModel
import com.ali.parandoosh.sample.presentation.base.BaseIntent
import com.ali.parandoosh.sample.presentation.base.BaseViewModel
import com.ali.parandoosh.sample.presentation.base.model.TaskStatus
import com.ali.parandoosh.sample.presentation.main.mapper.RestaurantMapper
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import javax.inject.Inject

open class MainViewModel @Inject internal constructor(
    private val mainProcessor: MainProcessor, private val restaurantMapper: RestaurantMapper
) : ViewModel()
    , BaseViewModel<MainIntent, MainUIModel> {
    private var intentsSubject: PublishRelay<MainIntent> = PublishRelay.create()
    private val intentFilter: ObservableTransformer<MainIntent, MainIntent> =
        ObservableTransformer {
            it.publish {
                Observable.merge(it.ofType(MainIntent.InitialIntent::class.java).take(1),
                    it.filter { intent -> intent !is MainIntent.InitialIntent })
            }
        }

    private val reducer: BiFunction<MainUIModel, MainResult, MainUIModel> =
        BiFunction { _, result ->
            when (result) {
                is MainResult.LoadRestaurantsTask -> {
                    when {
                        result.status == TaskStatus.SUCCESS -> MainUIModel.Success(
                            result.restaurants?.map { restaurantMapper.mapToView(it) })
                        result.status == TaskStatus.FAILURE -> MainUIModel.Failed
                        result.status == TaskStatus.IN_FLIGHT -> MainUIModel.InProgress
                        else -> MainUIModel.Idle()
                    }
                }
            }
        }
    private val statesSubject: Observable<MainUIModel> = compose()


    override fun processIntents(intents: Observable<MainIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<MainUIModel> {
        return statesSubject
    }

    private fun compose(): Observable<MainUIModel> {
        return intentsSubject
            .compose(intentFilter)
            .map { this.actionFromIntent(it) }
            .compose(mainProcessor.actionProcessor)
            .scan(MainUIModel.Idle(), reducer)
            .replay(1)
            .autoConnect(0)
    }

    private fun actionFromIntent(intent: BaseIntent): MainAction {
        return when (intent) {
            is MainIntent.LoadRestaurantsIntent -> MainAction.LoadRestaurants
            is MainIntent.RefreshRestaurantsIntent -> MainAction.LoadRestaurants
            is MainIntent.InitialIntent -> MainAction.LoadRestaurants
            else -> throw UnsupportedOperationException(
                "Oops, that looks like an unknown intent: $intent"
            )
        }
    }
}