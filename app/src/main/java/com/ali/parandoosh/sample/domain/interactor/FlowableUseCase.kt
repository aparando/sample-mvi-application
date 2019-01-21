package com.ali.parandoosh.sample.domain.interactor

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class FlowableUseCase<T, in Params> {

    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>
    open fun execute(params: Params? = null): Flowable<T> {
        return this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}