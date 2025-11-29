package com.mohaberabi.inspekt.inspektlibrary.inspekt.impl

import com.erabipt.database.erabifit.dao.HttpLogDao
import com.erabipt.database.erabifit.entity.HttpLogEntity
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.config.InspektConfigStrategy
import com.mohaberabi.inspekt.inspektlibrary.providers.NotificationConfigProvider
import com.mohaberabi.inspekt.notification.InspektNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

internal class InspektImpl(
    private val strategy: InspektConfigStrategy.Actual,
    private val notificationManager: InspektNotificationManager,
    private val dao: HttpLogDao,
    private val notificationConfigProvider: NotificationConfigProvider
) : Inspekt {
    private val mutex = Mutex()
    override suspend fun logCall(
        entry: HttpLogEntry,
    ) = supervisorScope {
        mutex.withLock {
            val jobs = buildList {
                addIf(strategy.notificationEnabled) { sendNotification(entry) }
                add(upsert(entry))
            }
            jobs.joinAll()
        }
    }

    private fun CoroutineScope.upsert(
        entry: HttpLogEntry,
    ) = launch {
        val count = dao.count()
        if (count >= strategy.maxPersistenceEntry) {
            dao.clear()
        }
        dao.upsert(HttpLogEntity(entry))
    }

    private fun CoroutineScope.sendNotification(
        entry: HttpLogEntry,
    ) = launch {
        val status = entry.statusCode?.let { "$it" } ?: ""
        notificationManager.showLocalNotification(
            title = entry.url,
            body = "Logging the call $status",
            id = entry.id.hashCode(),
            config = notificationConfigProvider.provideInspektNotificationConfig()
        )
    }

    override fun observeEntries(): Flow<List<HttpLogEntry>> {
        return dao.getAll().map { list -> list.map { it.log } }
    }

    private inline fun <reified T> MutableList<T>.addIf(
        predicate: Boolean,
        block: () -> T
    ) {
        if (predicate) {
            add(block())
        }
    }
}