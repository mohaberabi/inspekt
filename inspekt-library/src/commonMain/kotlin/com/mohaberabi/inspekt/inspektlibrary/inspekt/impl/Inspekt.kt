package com.mohaberabi.inspekt.inspektlibrary.inspekt.impl

import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import kotlinx.coroutines.flow.Flow

interface Inspekt {

    suspend fun logCall(entry: HttpLogEntry)

    fun observeEntries(): Flow<List<HttpLogEntry>>
}