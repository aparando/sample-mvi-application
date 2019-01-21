package com.ali.parandoosh.sample.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ali.parandoosh.sample.cache.db.constant.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME)
data class CachedRestaurant(
    @PrimaryKey
    val id: Long,
    val name: String,
    val coverUrl: String,
    val description: String,
    val status: String
)
