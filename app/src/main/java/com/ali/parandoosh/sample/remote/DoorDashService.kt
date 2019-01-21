package com.ali.parandoosh.sample.remote

import com.ali.parandoosh.sample.remote.model.RestaurantModel
import io.reactivex.Flowable
import retrofit2.http.GET

interface DoorDashService {
    @GET("v2/restaurant/?lat=37.422740&lng=-122.139956&offset=0&limit=50")
    fun getRestaurants(): Flowable<List<RestaurantModel>>
}