package com.ali.parandoosh.sample.domain.test.factory

import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomLong
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomUuid
import com.ali.parandoosh.sample.domain.model.Restaurant

class RestaurantFactory {

    companion object Factory {

        fun makeList(count: Int): List<Restaurant> {
            val restaurants = mutableListOf<Restaurant>()
            repeat(count) {
                restaurants.add(makeRestaurant())
            }
            return restaurants
        }

        fun makeRestaurant(): Restaurant {
            return Restaurant(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

    }

}