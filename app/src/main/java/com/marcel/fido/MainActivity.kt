package com.marcel.fido

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.marcel.fido.ui.headlines.HeadlinesScreen
import com.marcel.fido.ui.headlines.HeadlinesScreenCallbacks
import com.marcel.fido.ui.headlines.HeadlinesScreenViewModel
import com.marcel.fido.ui.headlines.HeadlinesScreenViewModelIntent
import com.marcel.fido.ui.theme.FidoTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val headlinesScreenViewModel: HeadlinesScreenViewModel by inject()
        setContent {
            FidoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    stringResource(R.string.title_headlines_screen),
                                    style = MaterialTheme.typography.displayLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(Modifier.fillMaxSize().padding(innerPadding)) {
                        val callbacks = HeadlinesScreenCallbacks(
                            onSourceSelected = { id ->
                                headlinesScreenViewModel.onIntent(
                                    HeadlinesScreenViewModelIntent.OnSourceSelected(id)
                                )
                            },
                            onViewArticle = { id -> }
                        )
                        HeadlinesScreen(
                            Modifier.fillMaxSize(), headlinesScreenViewModel, callbacks
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FidoTheme {
        Greeting("Android")
    }
}