package com.ali.parandoosh.sample.presentation.main

import com.ali.parandoosh.sample.presentation.base.BaseViewState
import com.ali.parandoosh.sample.presentation.main.model.RestaurantView

sealed class MainUIModel(
    val inProgress: Boolean = false,
    val restaurantView: List<RestaurantView>? = null
) : BaseViewState {

    object InProgress : MainUIModel(true, null)

    object Failed : MainUIModel()

    data class Success(private val result: List<RestaurantView>?) : MainUIModel(false, result)

    class Idle : MainUIModel(false, null)

}
