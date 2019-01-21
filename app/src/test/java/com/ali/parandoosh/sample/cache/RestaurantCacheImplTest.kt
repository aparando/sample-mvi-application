package com.ali.parandoosh.sample.cache

import android.arch.persistence.room.Room
import com.ali.parandoosh.sample.cache.db.AppDatabase
import com.ali.parandoosh.sample.cache.mapper.RestaurantEntityMapper
import com.ali.parandoosh.sample.cache.test.factory.RestaurantFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21], manifest = Config.NONE)
class RestaurantCacheImplTest {
    private var database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application.baseContext,
            AppDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = RestaurantEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)


    private val databaseHelper = RestaurantCacheImpl(database,
            entityMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearRestaurants().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveRestaurantsCompletes() {
        val entities = RestaurantFactory.makeRestaurantEntityList(2)

        val testObserver = databaseHelper.saveRestaurants(entities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSavesData() {
        val count = 2
        val entities = RestaurantFactory.makeRestaurantEntityList(count)

        databaseHelper.saveRestaurants(entities).test()
        checkNumRowsInRestaurantTable(count)
    }

    @Test
    fun getRestaurantsCompletes() {
        val testObserver = databaseHelper.getRestaurants().test()
        testObserver.assertComplete()
    }

    private fun checkNumRowsInRestaurantTable(expectedRows: Int) {
        val numberOfRows = database.cachedRestaurantDao().getRestaurants().size
        assertEquals(expectedRows, numberOfRows)
    }
}