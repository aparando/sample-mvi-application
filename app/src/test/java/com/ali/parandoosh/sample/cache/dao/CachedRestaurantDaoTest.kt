package com.ali.parandoosh.sample.cache.dao

import android.arch.persistence.room.Room
import com.ali.parandoosh.sample.cache.db.AppDatabase
import com.ali.parandoosh.sample.cache.test.factory.RestaurantFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
open class CachedRestaurantDaoTest {
    private lateinit var database: AppDatabase
    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder( RuntimeEnvironment.application.baseContext, AppDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun instertRestaurantTest() {
        val cachedRestaurant = RestaurantFactory.makeCachedRestaurant()
        database.cachedRestaurantDao().insertRestaurant(cachedRestaurant)
        val restaurants = database.cachedRestaurantDao().getRestaurants()
        assert(restaurants.isNotEmpty())
    }

    @Test
    fun getRestaurantsTest() {
        val cachedRestaurants = RestaurantFactory.makeCachedRestaurantList(5)

        cachedRestaurants.forEach {
            database.cachedRestaurantDao().insertRestaurant(it)
        }

        val retrievedRestaurants = database.cachedRestaurantDao().getRestaurants()
        assert(retrievedRestaurants == cachedRestaurants.sortedWith(compareBy({ it.id }, { it.id })))
    }
}