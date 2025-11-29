package com.mohaberabi.inspekt.inspektlibrary.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogTab
import com.mohaberabi.inspekt.inspektlibrary.ui.navigation.InspektJson
import com.mohaberabi.inspekt.inspektlibrary.ui.navigation.LogDetailRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class LogDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val route = savedStateHandle.toRoute<LogDetailRoute>()
    val log = route.logJson.let { InspektJson.decodeFromString<HttpLogEntry>(it) }

    private val _state = MutableStateFlow(LogDetailState())
    val state = _state.asStateFlow()


    fun tabChanged(tab: LogTab) = _state.update { it.copy(selectedTab = tab) }
}