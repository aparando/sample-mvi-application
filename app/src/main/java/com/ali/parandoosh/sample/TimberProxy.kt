package com.ali.parandoosh.sample

import com.ali.parandoosh.sample.base.Proxy
import timber.log.Timber


class TimberProxy(private val tree: Timber.Tree) : Proxy {
    override fun init() {
        Timber.plant(tree)
    }
}