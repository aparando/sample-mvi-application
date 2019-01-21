package com.ali.parandoosh.sample

import android.app.Application
import com.ali.parandoosh.sample.base.CompositeProxy
import com.ali.parandoosh.sample.base.Proxy
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
class DevSettingsModule {
    @Provides
    @Singleton
    fun providesTree(): Timber.Tree {
        return Timber.DebugTree()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingLevel(): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Named("LeakCanary")
    fun providesLeakCanaryProxy(app: Application): Proxy {
        return LeakCanaryProxy(app)
    }

    @Provides
    @Singleton
    @Named("All")
    fun providesCompositeProxy(@Named("LeakCanary") leakCanary: Proxy): Proxy {
        return CompositeProxy(arrayOf(leakCanary))
    }
}