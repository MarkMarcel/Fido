package com.marcel.fido

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.marcel.fido.articles.data.ArticleRemoteDataSource
import com.marcel.fido.articles.domain.ArticleRepository
import com.marcel.fido.sources.data.SourceRemoteDataSource
import com.marcel.fido.sources.domain.Source
import com.marcel.fido.ui.theme.FidoTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val article: ArticleRepository by inject()
        lifecycleScope.launch {
            try {
                val articles = article.getHeadlinesFromSources(listOf(Source("abc-news","")))
                articles.onSuccess {
                    if(it.isNotEmpty()){
                        val a = article.getArticle(it.first().id)
                        println(a)
                    }
                }
                Log.d("MARK_GIC",articles.toString())
            }catch (e:Exception){
                Log.d("MARK_GIC",e.message?:"")
            }
        }
        setContent {
            FidoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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