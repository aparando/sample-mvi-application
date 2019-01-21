package com.ali.parandoosh.sample.presentation.base

import io.reactivex.Observable

interface BaseView<I : BaseIntent, in S : BaseViewState> {
    fun intents(): Observable<I>

    fun render(state: S)
}