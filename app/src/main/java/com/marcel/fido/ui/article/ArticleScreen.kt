package com.marcel.fido.ui.article

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    id: String,
    viewModel: ArticleScreenViewModel
) {
    val state: ArticleScreenViewModelState by viewModel.state.collectAsState(
        ArticleScreenViewModelState()
    )
    val article by remember {
        derivedStateOf {
            Log.d("MARK_GIC",state.article.toString())
            ArticleScreenViewModelState::article.get(state)
        }
    }
    LazyColumn(modifier) {
        article?.let {
            item {
                AsyncImage(
                    it.urlToImage,
                    contentDescription = it.title,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.size(8.dp))
            }
            item {
                Column {
                    Text(it.author, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.size(8.dp))
                }
            }
            item {
                Text(it.content)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.attachArguments(ArticleScreenViewModelArguments(id))
    }
}