package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantCache
import com.ali.parandoosh.sample.data.test.factory.RestaurantFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class RestaurantCacheDataStoreTest {
    private lateinit var restaurantCacheDataStore: RestaurantCacheDataStore
    private lateinit var restaurantCache: RestaurantCache
    @Before
    fun setUp() {
        restaurantCache = mock()
        restaurantCacheDataStore = RestaurantCacheDataStore(restaurantCache)
    }

    @Test
    fun clearCompletes() {
        stubCacheClear(Completable.complete())
        val testObserver = restaurantCacheDataStore.clearRestaurants().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveCompletes() {
        stubCacheSave(Completable.complete())
        val testObserver = restaurantCacheDataStore.saveRestaurants(
                RestaurantFactory.makeEntityList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getCompletes() {
        stubCacheGet(Flowable.just(RestaurantFactory.makeEntityList(2)))
        val testObserver = restaurantCacheDataStore.getRestaurants().test()
        testObserver.assertComplete()
    }

    private fun stubCacheSave(completable: Completable) {
        whenever(restaurantCache.saveRestaurants(any()))
                .thenReturn(completable)
    }

    private fun stubCacheGet(single: Flowable<List<RestaurantEntity>>) {
        whenever(restaurantCache.getRestaurants())
                .thenReturn(single)
    }

    private fun stubCacheClear(completable: Completable) {
        whenever(restaurantCache.clearRestaurants())
                .thenReturn(completable)
    }
}