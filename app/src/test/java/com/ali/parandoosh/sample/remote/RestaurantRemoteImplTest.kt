package com.ali.parandoosh.sample.remote

import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomLong
import com.ali.parandoosh.sample.cache.test.factory.DataFactory.Factory.randomUuid
import com.ali.parandoosh.sample.data.model.RestaurantEntity
import com.ali.parandoosh.sample.remote.mapper.RestaurantEntityMapper
import com.ali.parandoosh.sample.remote.model.RestaurantModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RestaurantRemoteImplTest {
    private lateinit var entityMapper: RestaurantEntityMapper
    private lateinit var doorDashService: DoorDashService
    private lateinit var remoteImpl: RestaurantRemoteImpl
    @Before
    fun setup() {
        entityMapper = mock()
        doorDashService = mock()
        remoteImpl = RestaurantRemoteImpl(doorDashService, entityMapper)
    }

    @Test
    fun getCompletes() {
        stubServiceGet(Flowable.just(makeResponse()))
        val testObserver = remoteImpl.getRestaurants().test()
        testObserver.assertComplete()
    }

    @Test
    fun getReturnsData() {
        val response = makeResponse()
        stubServiceGet(Flowable.just(response))
        val entities = mutableListOf<RestaurantEntity>()

        for (model in response) {
            entities.add(entityMapper.mapFromRemote(model))
        }

        val testObserver = remoteImpl.getRestaurants().test()
        testObserver.assertValue(entities)
    }


    private fun stubServiceGet(observable: Flowable<List<RestaurantModel>>) {
        whenever(doorDashService.getRestaurants())
                .thenReturn(observable)
    }

    private fun makeResponse(): List<RestaurantModel> {
        val response = mutableListOf<RestaurantModel>()
        repeat(5) {
            response.add(RestaurantModel(randomLong(), randomUuid(), randomUuid(), randomUuid(), randomUuid()))
        }
        return response
    }
}