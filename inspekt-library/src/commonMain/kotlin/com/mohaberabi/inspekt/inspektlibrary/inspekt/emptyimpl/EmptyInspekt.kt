package com.mohaberabi.inspekt.inspektlibrary.inspekt.emptyimpl

import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.Inspekt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object EmptyInspekt : Inspekt {
    override suspend fun logCall(entry: HttpLogEntry) {}
    override fun observeEntries(): Flow<List<HttpLogEntry>> = flowOf()
}