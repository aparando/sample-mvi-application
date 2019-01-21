package com.ali.parandoosh.sample.cache

import android.content.Context
import android.content.SharedPreferences

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private const val PREF_RESTAURANT_PACKAGE_NAME =
            "com.interview.doordash.doordashlite.preferences"
        private const val PREF_KEY_LAST_CACHE = "last_cache"
    }

    private val restaurantPref: SharedPreferences

    init {
        restaurantPref =
            context.getSharedPreferences(PREF_RESTAURANT_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    var lastCacheTime: Long
        get() = restaurantPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = restaurantPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()
}
