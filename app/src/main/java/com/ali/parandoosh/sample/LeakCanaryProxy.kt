package com.ali.parandoosh.sample

import android.app.Application
import com.ali.parandoosh.sample.base.Proxy
import com.squareup.leakcanary.LeakCanary

class LeakCanaryProxy(private val app: Application) : Proxy {
    override fun init() {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return
        }

        LeakCanary.install(app)
    }
}