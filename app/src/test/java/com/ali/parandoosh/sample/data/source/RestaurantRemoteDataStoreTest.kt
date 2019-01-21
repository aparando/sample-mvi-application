package com.ali.parandoosh.sample.data.source

import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.data.repository.RestaurantRemote
import com.ali.parandoosh.sample.data.test.factory.RestaurantFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantRemoteDataStoreTest {

    private lateinit var remoteDataStore: RestaurantRemoteDataStore

    private lateinit var remote: RestaurantRemote

    @Before
    fun setUp() {
        remote = mock()
        remoteDataStore = RestaurantRemoteDataStore(remote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearThrowsException() {
        remoteDataStore.clearRestaurants().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveThrowsException() {
        remoteDataStore.saveRestaurants(RestaurantFactory.makeEntityList(2)).test()
    }

    @Test
    fun getCompletes() {
        stubCacheGet(Flowable.just(RestaurantFactory.makeEntityList(2)))
        val testObserver = remote.getRestaurants().test()
        testObserver.assertComplete()
    }

    private fun stubCacheGet(single: Flowable<List<RestaurantEntity>>) {
        whenever(remote.getRestaurants())
                .thenReturn(single)
    }


}