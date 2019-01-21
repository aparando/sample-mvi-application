package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.repository.RestaurantCache
import com.ali.parandoosh.sample.data.repository.RestaurantDataStore
import javax.inject.Inject

open class RestaurantDataStoreFactory @Inject constructor(
    private val restaurantCache: RestaurantCache,
    private val restaurantCacheDataStore: RestaurantCacheDataStore,
    private val restaurantRemoteDataStore: RestaurantRemoteDataStore
) {

    open fun retrieveDataStore(isCached: Boolean): RestaurantDataStore {
        if (isCached && !restaurantCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    open fun retrieveCacheDataStore(): RestaurantDataStore = restaurantCacheDataStore


    open fun retrieveRemoteDataStore(): RestaurantDataStore = restaurantRemoteDataStore

}