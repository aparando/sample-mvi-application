package com.ali.parandoosh.sample.ui.detail.injection

import com.ali.parandoosh.sample.ui.detail.view.DetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DetailBindingModule {

    @ContributesAndroidInjector
    abstract fun detailFragment(): DetailFragment
}
