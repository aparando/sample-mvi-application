package com.ali.parandoosh.sample.data.repository

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import io.reactivex.Flowable


interface RestaurantRemote {

    fun getRestaurants(): Flowable<List<RestaurantEntity>>
}
