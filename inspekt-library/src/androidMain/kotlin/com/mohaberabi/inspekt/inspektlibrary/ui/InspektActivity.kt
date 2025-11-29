package com.mohaberabi.inspekt.inspektlibrary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mohaberabi.inspekt.inspektlibrary.ui.navigation.InspektNavGraph
import com.mohaberabi.inspekt.inspektlibrary.ui.screen.InspektScreen

internal class InspektActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val intentLauncherName = intent.getStringExtra("root_launcher") ?: ""
        setContent { InspektNavGraph(rootAppName = intentLauncherName) }
    }
}