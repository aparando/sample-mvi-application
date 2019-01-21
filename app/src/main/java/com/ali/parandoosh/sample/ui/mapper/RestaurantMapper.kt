package com.ali.parandoosh.sample.ui.mapper

import com.ali.parandoosh.sample.presentation.main.model.RestaurantView
import com.ali.parandoosh.sample.ui.model.RestaurantViewModel
import javax.inject.Inject

open class RestaurantMapper @Inject constructor() : Mapper<RestaurantViewModel, RestaurantView> {


    override fun mapToViewModel(type: RestaurantView): RestaurantViewModel {
        return RestaurantViewModel(type.name, type.coverUrl, type.description, type.status)
    }

}