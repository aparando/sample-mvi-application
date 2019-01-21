package com.ali.parandoosh.sample.base

import android.content.Context
import android.support.annotation.LayoutRes


interface Item {
    @LayoutRes
    fun getLayoutId(): Int

    fun getSpanSize(): Int
}

interface Resetable {
    fun reset()
}

interface Initializable {
    fun initialize(context: Context)
}
