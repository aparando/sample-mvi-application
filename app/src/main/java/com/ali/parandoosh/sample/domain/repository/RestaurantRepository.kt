package com.ali.parandoosh.sample.domain.repository

import com.ali.parandoosh.sample.domain.model.Restaurant
import io.reactivex.Completable
import io.reactivex.Flowable

interface RestaurantRepository {

    fun clearRestaurants(): Completable

    fun saveRestaurants(restaurants: List<Restaurant>): Completable

    fun getRestaurants(): Flowable<List<Restaurant>>

}