package com.ali.parandoosh.sample.remote.mapper

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.remote.model.RestaurantModel
import javax.inject.Inject


open class RestaurantEntityMapper @Inject constructor() :
    EntityMapper<RestaurantModel, RestaurantEntity> {

    override fun mapFromRemote(type: RestaurantModel): RestaurantEntity {
        return RestaurantEntity(type.id, type.name, type.coverUrl, type.description, type.status)
    }

}