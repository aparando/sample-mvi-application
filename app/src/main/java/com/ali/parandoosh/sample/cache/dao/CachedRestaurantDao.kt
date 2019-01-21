package com.ali.parandoosh.sample.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ali.parandoosh.sample.cache.db.constant.DatabaseConstants
import com.ali.parandoosh.sample.cache.model.CachedRestaurant

@Dao
abstract class CachedRestaurantDao {
    @Query(DatabaseConstants.QUERY_RESTAURANTS)
    abstract fun getRestaurants(): List<CachedRestaurant>

    @Query(DatabaseConstants.DELETE_ALL_RESTAURANTS)
    abstract fun clearRestaurants()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRestaurant(cachedRestaurant: CachedRestaurant)
}