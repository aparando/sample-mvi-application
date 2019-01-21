package com.ali.parandoosh.sample.domain.interactor.main

import com.ali.parandoosh.sample.domain.interactor.FlowableUseCase
import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.domain.repository.RestaurantRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetRestaurants @Inject constructor(private val restaurantRepository: RestaurantRepository) :
    FlowableUseCase<List<Restaurant>, Void?>() {

    public override fun buildUseCaseObservable(params: Void?): Flowable<List<Restaurant>> {
        return restaurantRepository.getRestaurants()
    }

}