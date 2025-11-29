package com.erabipt.database.erabifit.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.erabipt.database.erabifit.entity.HttpLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HttpLogDao {

    @Upsert
    suspend fun upsert(entity: HttpLogEntity)

    @Query("SELECT COUNT(*) FROM http_log")
    suspend fun count(): Long


    @Query("DELETE FROM http_log")
    suspend fun clear()

    @Query("SELECT * FROM http_log ORDER BY timeStamp DESC")
    fun getAll(): Flow<List<HttpLogEntity>>
}