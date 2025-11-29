package com.mohaberabi.inspekt.inspektlibrary.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


enum class LogTab {
    Overview,
    Request,
    Response
}

@Composable
internal fun LogTabBar(
    modifier: Modifier = Modifier,
    selected: LogTab,
    onClick: (LogTab) -> Unit,
    tabContent: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            LogTab.entries.forEach {
                NavigationBarItem(
                    modifier = Modifier.wrapContentHeight(),
                    selected = it == selected,
                    onClick = { onClick(it) },
                    icon = {
                        Text(it.name)
                    }
                )
            }
        }
        tabContent()
    }

}