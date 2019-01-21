package com.ali.parandoosh.sample.remote

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantRemote
import com.ali.parandoosh.sample.remote.mapper.RestaurantEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

class RestaurantRemoteImpl @Inject constructor(
    private val doorDashService: DoorDashService,
    private val entityMapper: RestaurantEntityMapper
) :
    RestaurantRemote {
    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        return doorDashService.getRestaurants()
            .map {
                val entities = mutableListOf<RestaurantEntity>()
                it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                entities
            }
    }

}