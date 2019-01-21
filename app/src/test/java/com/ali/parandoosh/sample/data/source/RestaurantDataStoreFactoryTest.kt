package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.repository.RestaurantCache
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantDataStoreFactoryTest {
    private lateinit var dataStoreFactory: RestaurantDataStoreFactory

    private lateinit var cache: RestaurantCache
    private lateinit var cacheDataStore: RestaurantCacheDataStore
    private lateinit var remoteDataStore: RestaurantRemoteDataStore

    @Before
    fun setUp() {
        cache = mock()
        cacheDataStore = mock()
        remoteDataStore = mock()
        dataStoreFactory = RestaurantDataStoreFactory(cache,
                cacheDataStore, remoteDataStore)
    }


    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubCacheIsCached(Single.just(false))
        val dataStore = dataStoreFactory.retrieveDataStore(false)
        assert(dataStore is RestaurantRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubCacheIsCached(Single.just(true))
        stubCacheIsExpired(true)
        val dataStore = dataStoreFactory.retrieveDataStore(true)
        assert(dataStore is RestaurantRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubCacheIsCached(Single.just(true))
        stubCacheIsExpired(false)
        val dataStore = dataStoreFactory.retrieveDataStore(true)
        assert(dataStore is RestaurantCacheDataStore)
    }


    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val dataStore = dataStoreFactory.retrieveRemoteDataStore()
        assert(dataStore is RestaurantRemoteDataStore)
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val dataStore = dataStoreFactory.retrieveCacheDataStore()
        assert(dataStore is RestaurantCacheDataStore)
    }

    private fun stubCacheIsCached(single: Single<Boolean>) {
        whenever(cache.isCached())
                .thenReturn(single)
    }

    private fun stubCacheIsExpired(isExpired: Boolean) {
        whenever(cache.isExpired())
                .thenReturn(isExpired)
    }


}