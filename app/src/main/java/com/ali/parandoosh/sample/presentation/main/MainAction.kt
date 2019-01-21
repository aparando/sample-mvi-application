package com.ali.parandoosh.sample.presentation.main

import com.ali.parandoosh.sample.presentation.base.BaseAction


sealed class MainAction : BaseAction {

    object LoadRestaurants : MainAction()

}