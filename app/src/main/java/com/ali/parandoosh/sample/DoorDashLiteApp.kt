package com.ali.parandoosh.sample

import com.ali.parandoosh.sample.base.Proxy
import com.facebook.stetho.Stetho
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class DoorDashLiteApp : DaggerApplication() {
    @field:[Inject Named("All")]
    lateinit var proxy: Lazy<Proxy>
    @Inject
    lateinit var tree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        Timber.plant(tree)

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            proxy.get().init()
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return ComponentFactory.create(this)
    }
}