package com.ali.parandoosh.sample.remote.model

import com.squareup.moshi.Json

data class RestaurantModel(
    val id: Long,
    val name: String,
    @Json(name = "cover_img_url") val coverUrl: String,
    val description: String,
    val status: String
)
