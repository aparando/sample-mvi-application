package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantDataStore
import com.ali.parandoosh.sample.data.repository.RestaurantRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

open class RestaurantRemoteDataStore @Inject constructor(private val restaurantRemote: RestaurantRemote) :
    RestaurantDataStore {
    override fun clearRestaurants(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveRestaurants(restaurants: List<RestaurantEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> =
        restaurantRemote.getRestaurants()


    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}