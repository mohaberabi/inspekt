package com.mohaberabi.inspekt.inspektlibrary.config

sealed interface InspektConfigStrategy {
    data object NoOperation : InspektConfigStrategy
    data class Actual(
        val config: InspektConfig,
        val notificationEnabled: Boolean = true,
        val shortcutEnabled: Boolean = true,
        val maxPersistenceEntry: Long = 100L
    ) : InspektConfigStrategy
}