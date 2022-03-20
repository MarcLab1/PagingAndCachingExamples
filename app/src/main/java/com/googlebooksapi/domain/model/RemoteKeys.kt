package com.googlebooksapi.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "remotekeys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)