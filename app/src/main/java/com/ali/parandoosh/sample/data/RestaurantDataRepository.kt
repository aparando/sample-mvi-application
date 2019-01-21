package com.ali.parandoosh.sample.data

import com.ali.parandoosh.sample.data.mapper.RestaurantMapper
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.source.RestaurantDataStoreFactory
import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.domain.repository.RestaurantRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class RestaurantDataRepository @Inject constructor(
    private val factory: RestaurantDataStoreFactory,
    private val restaurantMapper: RestaurantMapper
) :
    RestaurantRepository {
    override fun saveRestaurants(restaurants: List<Restaurant>): Completable {
        val restaurantEntities = mutableListOf<RestaurantEntity>()
        restaurants.map { restaurantEntities.add(restaurantMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveRestaurants(restaurantEntities)
    }

    override fun getRestaurants(): Flowable<List<Restaurant>> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher {
                factory.retrieveDataStore(it).getRestaurants()
            }
            .flatMap { restaurantEntities ->
                Flowable.just(restaurantEntities.map { restaurantMapper.mapFromEntity(it) })
            }
            .flatMap {
                saveRestaurants(it).toSingle { it }.toFlowable()
            }

    }

    override fun clearRestaurants(): Completable =
        factory.retrieveCacheDataStore().clearRestaurants()

}