package com.ali.parandoosh.sample.data.repository

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface RestaurantDataStore {

    fun clearRestaurants(): Completable

    fun saveRestaurants(restaurants: List<RestaurantEntity>): Completable

    fun getRestaurants(): Flowable<List<RestaurantEntity>>

    fun isCached(): Single<Boolean>

}