package com.mohaberabi.inspekt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohaberabi.inspekt.client.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: Int,
    val title: String,
    val content: String
)

@Composable
fun App() {
    MaterialTheme { HomeScreen(); }
}


@Composable
fun HomeScreen() {
    val scope = rememberCoroutineScope()
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("My Client App")
            Button(
                onClick = {
                    scope.launch {
                        val jobs = (1..10).map {
                            scope.launch {
                                val client = httpClient()
                                val post: PostDto =
                                    client.get("https://jsonplaceholder.org/posts/1").body()
                            }
                        }.joinAll()
                    }

                },
            ) {
                Text("Make 10 http call")
            }

        }
    }
}

