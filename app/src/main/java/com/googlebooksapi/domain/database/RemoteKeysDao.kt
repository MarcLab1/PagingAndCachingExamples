package com.googlebooksapi.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.googlebooksapi.domain.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM remotekeys WHERE id =:id")
    suspend fun getRemoteKeys(id: String): RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM remotekeys")
    suspend fun deleteRemoteKeys()

}