package com.mohaberabi.inspekt.inspektlibrary.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
internal data object HomeRoute


@Serializable
internal data class LogDetailRoute(
    val logJson: String
)