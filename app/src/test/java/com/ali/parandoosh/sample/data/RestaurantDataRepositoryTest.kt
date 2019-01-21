package com.ali.parandoosh.sample.data

import com.ali.parandoosh.sample.data.mapper.RestaurantMapper
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantDataStore
import com.ali.parandoosh.sample.data.source.RestaurantCacheDataStore
import com.ali.parandoosh.sample.data.source.RestaurantDataStoreFactory
import com.ali.parandoosh.sample.data.source.RestaurantRemoteDataStore
import com.ali.parandoosh.sample.data.test.factory.RestaurantFactory
import com.ali.parandoosh.sample.domain.model.Restaurant
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantDataRepositoryTest {

    private lateinit var dataRepository: RestaurantDataRepository

    private lateinit var dataStoreFactory: RestaurantDataStoreFactory
    private lateinit var mapper: RestaurantMapper
    private lateinit var cacheDataStore: RestaurantCacheDataStore
    private lateinit var remoteDataStore: RestaurantRemoteDataStore

    @Before
    fun setUp() {
        dataStoreFactory = mock()
        mapper = mock()
        cacheDataStore = mock()
        remoteDataStore = mock()
        dataRepository = RestaurantDataRepository(dataStoreFactory, mapper)
        stubDataStoreFactoryRetrieveCacheDataStore()
        stubDataStoreFactoryRetrieveRemoteDataStore()
    }


    @Test
    fun clearCompletes() {
        stubCacheClear(Completable.complete())
        val testObserver = dataRepository.clearRestaurants().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearCallsCacheDataStore() {
        stubCacheClear(Completable.complete())
        dataRepository.clearRestaurants().test()
        verify(cacheDataStore).clearRestaurants()
    }

    @Test
    fun clearNeverCallsRemoteDataStore() {
        stubCacheClear(Completable.complete())
        dataRepository.clearRestaurants().test()
        verify(remoteDataStore, never()).clearRestaurants()
    }

    @Test
    fun saveCompletes() {
        stubCacheSave(Completable.complete())
        val testObserver = dataRepository.saveRestaurants(
                RestaurantFactory.makeRestaurantList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveCallsCacheDataStore() {
        stubCacheSave(Completable.complete())
        dataRepository.saveRestaurants(RestaurantFactory.makeRestaurantList(2)).test()
        verify(cacheDataStore).saveRestaurants(any())
    }

    @Test
    fun saveNeverCallsRemoteDataStore() {
        stubCacheSave(Completable.complete())
        dataRepository.saveRestaurants(RestaurantFactory.makeRestaurantList(2)).test()
        verify(remoteDataStore, never()).saveRestaurants(any())
    }

    @Test
    fun getCompletes() {
        stubCacheDataStoreIsCached(Single.just(true))
        stubDataStoreFactoryRetrieveDataStore(cacheDataStore)
        stubCacheDataStoreGet(Flowable.just(
                RestaurantFactory.makeEntityList(22)))
        stubCacheSave(Completable.complete())
        val testObserver = dataRepository.getRestaurants().test()
        testObserver.assertComplete()
    }

    @Test
    fun getReturnsData() {
        stubCacheDataStoreIsCached(Single.just(true))
        stubDataStoreFactoryRetrieveDataStore(cacheDataStore)
        stubCacheSave(Completable.complete())
        val list = RestaurantFactory.makeRestaurantList(2)
        val entities = RestaurantFactory.makeEntityList(2)
        list.forEachIndexed { index, restaurant ->
            stubMapperMapFromEntity(entities[index], restaurant)
        }
        stubCacheDataStoreGet(Flowable.just(entities))

        val testObserver = dataRepository.getRestaurants().test()
        testObserver.assertValue(list)
    }

    @Test
    fun getSavesWhenFromCacheDataStore() {
        stubDataStoreFactoryRetrieveDataStore(cacheDataStore)
        stubCacheSave(Completable.complete())
        dataRepository.saveRestaurants(RestaurantFactory.makeRestaurantList(2)).test()
        verify(cacheDataStore).saveRestaurants(any())
    }

    @Test
    fun getNeverSavesWhenFromRemoteDataStore() {
        stubDataStoreFactoryRetrieveDataStore(remoteDataStore)
        stubCacheSave(Completable.complete())
        dataRepository.saveRestaurants(RestaurantFactory.makeRestaurantList(2)).test()
        verify(remoteDataStore, never()).saveRestaurants(any())
    }

    private fun stubCacheSave(completable: Completable) {
        whenever(cacheDataStore.saveRestaurants(any()))
                .thenReturn(completable)
    }

    private fun stubCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(cacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubCacheDataStoreGet(single: Flowable<List<RestaurantEntity>>) {
        whenever(cacheDataStore.getRestaurants())
                .thenReturn(single)
    }

    private fun stubCacheClear(completable: Completable) {
        whenever(cacheDataStore.clearRestaurants())
                .thenReturn(completable)
    }

    private fun stubDataStoreFactoryRetrieveCacheDataStore() {
        whenever(dataStoreFactory.retrieveCacheDataStore())
                .thenReturn(cacheDataStore)
    }

    private fun stubDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(dataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(cacheDataStore)
    }

    private fun stubDataStoreFactoryRetrieveDataStore(dataStore: RestaurantDataStore) {
        whenever(dataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }

    private fun stubMapperMapFromEntity(entity: RestaurantEntity,
                                        restaurant: Restaurant) {
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(restaurant)
    }


}