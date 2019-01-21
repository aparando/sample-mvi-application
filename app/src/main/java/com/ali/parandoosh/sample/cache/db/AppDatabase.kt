package com.ali.parandoosh.sample.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.ali.parandoosh.sample.BuildConfig
import com.ali.parandoosh.sample.cache.dao.CachedRestaurantDao
import com.ali.parandoosh.sample.cache.model.CachedRestaurant


@Database(
    entities = [CachedRestaurant::class],
    version = BuildConfig.DB_VERSION
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedRestaurantDao(): CachedRestaurantDao
}