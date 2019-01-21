package com.ali.parandoosh.sample.data.mapper

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.test.factory.RestaurantFactory
import com.ali.parandoosh.sample.domain.model.Restaurant
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class RestaurantMapperTest {

    private lateinit var restaurantMapper: RestaurantMapper

    @Before
    fun setUp() {
        restaurantMapper = RestaurantMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val entity = RestaurantFactory.makeEntity()
        val restaurant = restaurantMapper.mapFromEntity(entity)

        assertDataEquality(entity, restaurant)
    }

    @Test
    fun mapToEntityMapsData() {
        val cached = RestaurantFactory.makeRestaurant()
        val entity = restaurantMapper.mapToEntity(cached)

        assertDataEquality(entity, cached)
    }

    private fun assertDataEquality(entity: RestaurantEntity,
                                   restaurant: Restaurant) {
        assertEquals(entity.name, restaurant.name)
        assertEquals(entity.description, restaurant.description)
        assertEquals(entity.coverUrl, restaurant.coverUrl)
        assertEquals(entity.status, restaurant.status)
    }

}