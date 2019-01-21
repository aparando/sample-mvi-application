package com.ali.parandoosh.sample.cache.test.factory

import com.ali.parandoosh.sample.cache.model.CachedRestaurant
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomLong
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomUuid
import com.ali.parandoosh.sample.data.model.RestaurantEntity

class RestaurantFactory {
    companion object Factory {

        fun makeCachedRestaurant(): CachedRestaurant {
            return CachedRestaurant(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeRestaurantEntity(): RestaurantEntity {
            return RestaurantEntity(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeRestaurantEntityList(count: Int): List<RestaurantEntity> {
            val restaurantEntities = mutableListOf<RestaurantEntity>()
            repeat(count) {
                restaurantEntities.add(makeRestaurantEntity())
            }
            return restaurantEntities
        }

        fun makeCachedRestaurantList(count: Int): List<CachedRestaurant> {
            val cachedRestaurants = mutableListOf<CachedRestaurant>()
            repeat(count) {
                cachedRestaurants.add(makeCachedRestaurant())
            }
            return cachedRestaurants
        }

    }

}