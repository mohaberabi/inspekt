package com.mohaberabi.inspekt.inspektlibrary.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.GlobalInspekt
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

internal class LogsViewModel : ViewModel() {
    private val inspekt = GlobalInspekt.sharedInstance
    val logs = inspekt.observeEntries().stateIn(
        scope = viewModelScope,
        initialValue = listOf(),
        started = SharingStarted.WhileSubscribed(5_000)
    )
}