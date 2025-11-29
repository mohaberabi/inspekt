package com.mohaberabi.inspekt.inspektlibrary.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogBodyBox
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogListItem
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogOverviewBox
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogTab
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogTabBar
import com.mohaberabi.inspekt.inspektlibrary.ui.viewmodel.LogDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LogDetailScreen(
    onDismiss: () -> Unit,
    savedStateHandle: SavedStateHandle,
    viewmodel: LogDetailViewModel = viewModel { LogDetailViewModel(savedStateHandle) }
) {
    val log = viewmodel.log
    val state by viewmodel.state.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, "")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                title = {
                    Text(
                        "${log.method} ${log.endPoint}",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                actions = {

                },
            )
        }
    ) { paddingValues ->
        LogTabBar(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            selected = state.selectedTab,
            onClick = viewmodel::tabChanged,
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                when (state.selectedTab) {
                    LogTab.Overview -> LogOverviewBox(log = log)
                    LogTab.Request,
                    LogTab.Response -> LogBodyBox(tab = state.selectedTab, log = log)
                }
            }
        }
    }
}