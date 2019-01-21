package com.ali.parandoosh.sample.presentation.main

import com.ali.parandoosh.sample.domain.model.Restaurant
import com.ali.parandoosh.sample.presentation.base.BaseResult
import com.ali.parandoosh.sample.presentation.base.model.TaskStatus

sealed class MainResult : BaseResult {

    class LoadRestaurantsTask(
        val status: TaskStatus,
        val restaurants: List<Restaurant>? = null
    ) :
        MainResult() {

        companion object {

            internal fun success(conversations: List<Restaurant>?): LoadRestaurantsTask {
                return LoadRestaurantsTask(TaskStatus.SUCCESS, conversations)
            }

            internal fun failure(): LoadRestaurantsTask {
                return LoadRestaurantsTask(TaskStatus.FAILURE, null)
            }

            internal fun inFlight(): LoadRestaurantsTask {
                return LoadRestaurantsTask(TaskStatus.IN_FLIGHT)
            }
        }
    }

}