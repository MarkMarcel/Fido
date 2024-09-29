package com.marcel.fido.ui.headlines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marcel.fido.articles.domain.Article
import com.marcel.fido.sources.domain.Source

class HeadlinesScreenCallbacks(
    val onSourceSelected: (String) -> Unit,
    val onViewArticle: (String) -> Unit
)

@Composable
fun HeadlinesScreen(
    modifier: Modifier = Modifier,
    viewModel: HeadlinesScreenViewModel,
    callbacks: HeadlinesScreenCallbacks
) {
    val state by viewModel.state.collectAsState(HeadlinesScreenViewModelState())
    val articleListViewModelState by remember {
        derivedStateOf {
            HeadlinesScreenViewModelState::articles.get(state)
        }
    }
    val sourceSelectionViewModelState by remember {
        derivedStateOf {
            HeadlinesScreenViewModelState::allSources.get(state)
        }
    }
    Column(modifier.fillMaxSize()) {
        Headlines(
            modifier = Modifier.weight(1f),
            articles = articleListViewModelState,
            onViewArticle = callbacks.onViewArticle
        )
        SourceSelection(
            sources = sourceSelectionViewModelState,
            onSourceSelected = callbacks.onSourceSelected
        )
    }
    LaunchedEffect(Unit) {
        viewModel.attachArguments(HeadlinesScreenViewModelArguments())
    }
    SideEffect {
        viewModel.onIntent(HeadlinesScreenViewModelIntent.OnLoadLatestArticles)
    }
}

@Composable
fun Headlines(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onViewArticle: (String) -> Unit
) {

    fun getKey(index: Int) = articles[index].id
    LazyColumn(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(articles.size, key = ::getKey) { index ->
            HeadlineItem(
                article = articles[index],
                onViewArticle = onViewArticle
            )
        }
    }
}

@Composable
fun HeadlineItem(
    modifier: Modifier = Modifier,
    article: Article,
    onViewArticle: (String) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clickable {
                onViewArticle(article.id)
            }
    ) {
        Text(article.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.size(16.dp))
        Text(article.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.size(8.dp))
    }
}

@Composable
fun SourceSelection(
    modifier: Modifier = Modifier,
    sources: List<Source>,
    onSourceSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text("",modifier.weight(1f))
            Spacer(Modifier.size(8.dp))
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            sources.forEach {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSourceSelected(it.id) }) {
                    Text(it.name, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}




