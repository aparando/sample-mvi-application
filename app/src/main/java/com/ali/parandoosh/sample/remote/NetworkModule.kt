package com.ali.parandoosh.sample.remote

import android.content.Context
import com.ali.parandoosh.sample.AppModule
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [(AppModule::class)])
class NetworkModule {
    @Provides
    @Singleton
    fun loggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.i(it) })
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }


    @Provides
    @Singleton
    fun moshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun cache(cacheFile: File): Cache {
        return Cache(cacheFile, (10 * 1000 * 1000).toLong()) //10MB Cache
    }

    @Provides
    @Singleton
    fun cacheFile(context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }


    @Provides
    @Singleton
    fun okHttpClient(
        logging: Interceptor, cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .cache(cache)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun retrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.doordash.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(Schedulers.io())
            )
            .build()
    }

    @Provides
    @Singleton
    fun doordashService(retrofit: Retrofit): DoorDashService =
        retrofit.newBuilder()
            .build()
            .create(DoorDashService::class.java)
}