package com.ali.parandoosh.sample.domain.usecase.restaurant

import com.ali.parandoosh.sample.domain.interactor.main.GetRestaurants
import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.domain.repository.RestaurantRepository
import com.ali.parandoosh.sample.domain.test.factory.RestaurantFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class GetRestaurantsTest {
    private lateinit var getRestaurants: GetRestaurants

    private lateinit var mockRepository: RestaurantRepository

    @Before
    fun setUp() {
        mockRepository = mock()
        getRestaurants = GetRestaurants(mockRepository)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getRestaurants.buildUseCaseObservable(null)
        verify(mockRepository).getRestaurants()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubRepositoryGet(Flowable.just(RestaurantFactory.makeList(2)))
        val testObserver = getRestaurants.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val list = RestaurantFactory.makeList(2)
        stubRepositoryGet(Flowable.just(list))
        val testObserver = getRestaurants.buildUseCaseObservable(null).test()
        testObserver.assertValue(list)
    }

    private fun stubRepositoryGet(single: Flowable<List<Restaurant>>) {
        whenever(mockRepository.getRestaurants())
                .thenReturn(single)
    }
}