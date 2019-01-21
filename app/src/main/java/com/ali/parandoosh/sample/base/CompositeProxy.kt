package com.ali.parandoosh.sample.base


class CompositeProxy(private val proxies: Array<Proxy>) : Proxy {
    override fun init() {
        for (proxy in proxies) {
            proxy.init()
        }
    }
}