package com.ali.parandoosh.sample

import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ali.parandoosh.sample.cache.RestaurantCacheImpl
import com.ali.parandoosh.sample.cache.db.AppDatabase
import com.ali.parandoosh.sample.data.RestaurantDataRepository
import com.ali.parandoosh.sample.data.repository.RestaurantCache
import com.ali.parandoosh.sample.data.repository.RestaurantRemote
import com.ali.parandoosh.sample.domain.repository.RestaurantRepository
import com.ali.parandoosh.sample.remote.NetworkModule
import com.ali.parandoosh.sample.remote.RestaurantRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DevSettingsModule::class, NetworkModule::class])
abstract class DataModule {
    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideRoomDatabase(app: Application): AppDatabase {
            return Room.databaseBuilder(app, AppDatabase::class.java, BuildConfig.DB_NAME)
                .build()
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesSharedPreferences(app: Application): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(app)
        }
    }


    @Binds
    abstract fun bindRestaurantRepository(restaurantRepositry: RestaurantDataRepository): RestaurantRepository

    @Binds
    abstract fun bindRestaurantCache(restaurantCacheImpl: RestaurantCacheImpl): RestaurantCache

    @Binds
    abstract fun bindRestaurantRemote(restaurantRemoteImpl: RestaurantRemoteImpl): RestaurantRemote

}