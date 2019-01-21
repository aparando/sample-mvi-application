package com.ali.parandoosh.sample.cache.mapper

import com.ali.parandoosh.sample.cache.model.CachedRestaurant
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import javax.inject.Inject

open class RestaurantEntityMapper @Inject constructor() :
    EntityMapper<CachedRestaurant, RestaurantEntity> {
    override fun mapFromCached(type: CachedRestaurant): RestaurantEntity =
        RestaurantEntity(type.id, type.name, type.coverUrl, type.description, type.status)

    override fun mapToCached(type: RestaurantEntity): CachedRestaurant =
        CachedRestaurant(type.id, type.name, type.coverUrl, type.description, type.status)
}
