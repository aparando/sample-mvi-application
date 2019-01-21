package com.ali.parandoosh.sample.data.test.factory

import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomLong
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomUuid
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.domain.model.Restaurant

class RestaurantFactory {
    companion object Factory {

        fun makeEntity(): RestaurantEntity {
            return RestaurantEntity(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeRestaurant(): Restaurant {
            return Restaurant(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeEntityList(count: Int): List<RestaurantEntity> {
            val entities = mutableListOf<RestaurantEntity>()
            repeat(count) {
                entities.add(makeEntity())
            }
            return entities
        }

        fun makeRestaurantList(count: Int): List<Restaurant> {
            val bufferoos = mutableListOf<Restaurant>()
            repeat(count) {
                bufferoos.add(makeRestaurant())
            }
            return bufferoos
        }

    }
}