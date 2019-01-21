package com.ali.parandoosh.sample.data.mapper

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.domain.model.Restaurant
import javax.inject.Inject


open class RestaurantMapper @Inject constructor() : Mapper<RestaurantEntity, Restaurant> {

    override fun mapToEntity(type: Restaurant): RestaurantEntity =
        RestaurantEntity(type.id, type.name, type.coverUrl, type.description, type.status)

    override fun mapFromEntity(type: RestaurantEntity): Restaurant =
        Restaurant(type.id, type.name, type.coverUrl, type.description, type.status)
}
