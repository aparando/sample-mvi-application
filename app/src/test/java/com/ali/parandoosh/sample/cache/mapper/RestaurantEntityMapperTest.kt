package com.ali.parandoosh.sample.cache.mapper

import com.ali.parandoosh.sample.cache.model.CachedRestaurant
import com.ali.parandoosh.sample.cache.test.factory.RestaurantFactory
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class RestaurantEntityMapperTest {
    private lateinit var restaurantEntityMapper: RestaurantEntityMapper

    @Before
    fun setUp() {
        restaurantEntityMapper = RestaurantEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val restaurantEntity = RestaurantFactory.makeRestaurantEntity()
        val cachedRestaurant = restaurantEntityMapper.mapToCached(restaurantEntity)

        assertDataEquality(restaurantEntity, cachedRestaurant)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedRestaurant = RestaurantFactory.makeCachedRestaurant()
        val restaurantEntity = restaurantEntityMapper.mapFromCached(cachedRestaurant)

        assertDataEquality(restaurantEntity, cachedRestaurant)
    }

    private fun assertDataEquality(restaurantEntity: RestaurantEntity,
                                           cachedRestaurant: CachedRestaurant) {
        assertEquals(restaurantEntity.name, cachedRestaurant.name)
        assertEquals(restaurantEntity.coverUrl, cachedRestaurant.coverUrl)
        assertEquals(restaurantEntity.description, cachedRestaurant.description)
        assertEquals(restaurantEntity.status, cachedRestaurant.status)
    }

}