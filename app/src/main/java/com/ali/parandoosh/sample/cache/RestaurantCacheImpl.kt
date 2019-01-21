package com.ali.parandoosh.sample.cache

import com.ali.parandoosh.sample.cache.db.AppDatabase
import com.ali.parandoosh.sample.cache.mapper.RestaurantEntityMapper
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RestaurantCacheImpl @Inject constructor(
    private val database: AppDatabase
    , private val entityMapper: RestaurantEntityMapper
    , private val preferencesHelper: PreferencesHelper
) : RestaurantCache {


    companion object {
        private const val EXPIRATION_TIME = (60 * 10 * 1000).toLong()
    }

    override fun saveRestaurants(restaurants: List<RestaurantEntity>): Completable {
        return Completable.defer {
            restaurants.forEach {
                database.cachedRestaurantDao().insertRestaurant(
                    entityMapper.mapToCached(it)
                )
            }
            Completable.complete()
        }
    }

    override fun getRestaurants(): Flowable<List<RestaurantEntity>> {
        return Flowable.defer {
            Flowable.just(database.cachedRestaurantDao().getRestaurants())
        }.map { cachedRestaurants ->
            cachedRestaurants.map { entityMapper.mapFromCached(it) }
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(database.cachedRestaurantDao().getRestaurants().isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    override fun clearRestaurants(): Completable {
        return Completable.defer {
            database.cachedRestaurantDao().clearRestaurants()
            Completable.complete()
        }
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }
}