package com.ali.parandoosh.sample.presentation.main.mapper

import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.presentation.main.model.RestaurantView
import com.ali.parandoosh.sample.presentation.mapper.Mapper
import javax.inject.Inject

open class RestaurantMapper @Inject constructor() : Mapper<RestaurantView, Restaurant> {


    override fun mapToView(type: Restaurant): RestaurantView {
        return RestaurantView(type.name, type.coverUrl, type.description, type.status)
    }


}
