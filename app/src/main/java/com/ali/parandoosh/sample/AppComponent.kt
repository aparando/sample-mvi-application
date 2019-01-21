package com.ali.parandoosh.sample

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [(AndroidSupportInjectionModule::class),
        (AppModule::class),
        (BindingModule::class),
        (DataModule::class),
        (ViewModelModule::class),
        (DomainModule::class)]
)
interface AppComponent : DebugComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }
}