package com.mohaberabi.inspekt.inspektlibrary.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohaberabi.inspekt.inspektlibrary.ui.screen.InspektScreen
import com.mohaberabi.inspekt.inspektlibrary.ui.screen.LogDetailScreen
import kotlinx.serialization.json.Json

internal val InspektJson = Json {
    encodeDefaults = true
    explicitNulls = false
    ignoreUnknownKeys = true
    prettyPrint = true
    prettyPrintIndent = "  "
}

@Composable
internal fun InspektNavGraph(
    onDismiss: () -> Unit = {},
    rootAppName: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            InspektScreen(
                onDismiss = onDismiss,
                rootAppName = rootAppName,
                onGoDetail = {
                    val logJson = InspektJson.encodeToString(it)
                    navController.navigate(LogDetailRoute(logJson))
                }
            )
        }
        composable<LogDetailRoute> {
            LogDetailScreen(
                savedStateHandle = it.savedStateHandle,
                onDismiss = navController::popBackStack
            )
        }
    }
}