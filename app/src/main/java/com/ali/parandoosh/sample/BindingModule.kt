package com.ali.parandoosh.sample

import com.ali.parandoosh.sample.base.ActivityScope
import com.ali.parandoosh.sample.ui.detail.injection.DetailBindingModule
import com.ali.parandoosh.sample.ui.detail.view.DetailActivity
import com.ali.parandoosh.sample.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BindingModule {

    @ActivityScope
    @ContributesAndroidInjector()
    abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailBindingModule::class])
    abstract fun detailActivity(): DetailActivity

}
