package com.ali.parandoosh.sample

import android.app.Application

class ComponentFactory private constructor() {
    companion object {
        fun create(app: Application): AppComponent {
            return DaggerAppComponent.builder().app(app).build()
        }
    }
}