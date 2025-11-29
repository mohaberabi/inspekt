package com.erabipt.database.erabifit.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.inspekt.modelhub.httplogenetry.HttpLogEntry

@Entity("http_log")
data class HttpLogEntity(
    val log: HttpLogEntry,
    val timeStamp: Long = log.timestampMillis,
    @PrimaryKey(autoGenerate = false) val id: String = log.id,
)



