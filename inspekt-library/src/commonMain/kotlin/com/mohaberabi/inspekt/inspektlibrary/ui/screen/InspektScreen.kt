package com.mohaberabi.inspekt.inspektlibrary.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inspekt.modelhub.httplogenetry.HttpLogEntry
import com.mohaberabi.inspekt.inspektlibrary.inspekt.impl.GlobalInspekt
import com.mohaberabi.inspekt.inspektlibrary.ui.components.LogListItem
import com.mohaberabi.inspekt.inspektlibrary.ui.viewmodel.LogsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InspektScreen(
    onDismiss: () -> Unit = {},
    rootAppName: String,
    onGoDetail: (HttpLogEntry) -> Unit,
    viewModel: LogsViewModel = viewModel { LogsViewModel() }

) {
    val logs by viewModel.logs.collectAsStateWithLifecycle()

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
                    Column {

                        Text("Inspekt")
                        Text(
                            rootAppName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(

            ) {
                itemsIndexed(
                    items = logs,
                ) { index, log ->
                    LogListItem(
                        log = log,
                        onClick = { onGoDetail(log) },
                    )
                    if (logs.isNotEmpty() && index != logs.lastIndex) {
                        HorizontalDivider(
                            thickness = .7.dp,
                            color = Color.LightGray,
                            modifier = Modifier.height(8.dp)
                        )
                    }
                }
            }
        }
    }
}




