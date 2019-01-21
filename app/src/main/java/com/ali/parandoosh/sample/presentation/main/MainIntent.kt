package com.ali.parandoosh.sample.presentation.main

import com.ali.parandoosh.sample.presentation.base.BaseIntent

sealed class MainIntent : BaseIntent {

    object InitialIntent : MainIntent()

    object LoadRestaurantsIntent : MainIntent()

    object RefreshRestaurantsIntent : MainIntent()

}
