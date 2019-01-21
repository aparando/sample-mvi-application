package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantCache
import com.ali.parandoosh.sample.data.repository.RestaurantDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class RestaurantCacheDataStore @Inject constructor(private val restaurantCache: RestaurantCache) :
    RestaurantDataStore {

    override fun clearRestaurants(): Completable = restaurantCache.clearRestaurants()


    override fun saveRestaurants(restaurants: List<RestaurantEntity>): Completable {
        return restaurantCache.saveRestaurants(restaurants)
            .doOnComplete {
                restaurantCache.setLastCacheTime(System.currentTimeMillis())
            }
    }

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> =
        restaurantCache.getRestaurants()


    override fun isCached(): Single<Boolean> = restaurantCache.isCached()

}