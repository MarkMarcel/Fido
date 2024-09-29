package com.marcel.fido.ui.headlines

import androidx.lifecycle.viewModelScope
import com.marcel.fido.articles.domain.Article
import com.marcel.fido.articles.domain.ArticleRepository
import com.marcel.fido.core.FidoViewModel
import com.marcel.fido.core.FidoViewModelArguments
import com.marcel.fido.core.FidoViewModelIntent
import com.marcel.fido.core.FidoViewModelState
import com.marcel.fido.sources.domain.Source
import com.marcel.fido.sources.domain.SourceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeadlinesScreenViewModel(
    private val articleRepository: ArticleRepository,
    private val sourceRepository: SourceRepository
) : FidoViewModel<
        HeadlinesScreenViewModelArguments,
        HeadlinesScreenViewModelIntent,
        HeadlinesScreenViewModelState>() {

    override val state: StateFlow<HeadlinesScreenViewModelState>
        get() = stateChanges

    override fun attachArguments(arguments: HeadlinesScreenViewModelArguments) {
        onIntent(HeadlinesScreenViewModelIntent.OnLoadSources)
    }

    override fun onIntent(intent: HeadlinesScreenViewModelIntent) {
        when (intent) {
            HeadlinesScreenViewModelIntent.OnLoadLatestArticles -> {
                viewModelScope.launch {
                    articleRepository.getHeadlinesFromSources(state.value.selectedSources.toList())
                        .onSuccess {
                            stateChanges.emit(state.value.copy(articles = it))
                        }.onFailure {
                            stateChanges.emit(
                                state.value.copy(
                                    errorMessage = it.message ?: "Couldn't load articles"
                                )
                            )
                        }
                }
            }

            HeadlinesScreenViewModelIntent.OnLoadSources -> {
                viewModelScope.launch {
                    sourceRepository.getSources().onSuccess {
                        stateChanges.emit(state.value.copy(allSources = it.take(5)))
                    }.onFailure {
                        stateChanges.emit(
                            state.value.copy(errorMessage = it.message ?: "Couldn't load sources")
                        )
                    }
                }
            }

            is HeadlinesScreenViewModelIntent.OnSourceRemoved -> {
                val currentSelection = state.value.selectedSources.toMutableSet()
                currentSelection.remove(intent.id)
                viewModelScope.launch {
                    stateChanges.emit(state.value.copy(selectedSources = currentSelection.toSet()))
                }
            }

            is HeadlinesScreenViewModelIntent.OnSourceSelected -> {
                val currentSelection = state.value.selectedSources.toMutableSet()
                currentSelection.clear()
                currentSelection.add(intent.id)
                viewModelScope.launch {
                    stateChanges.emit(state.value.copy(selectedSources = currentSelection.toSet()))
                }
            }
        }
    }

    private val stateChanges = MutableStateFlow(HeadlinesScreenViewModelState())
}

class HeadlinesScreenViewModelArguments : FidoViewModelArguments()

data class HeadlinesScreenViewModelState(
    val allSources: List<Source> = emptyList(),
    val articles: List<Article> = emptyList(),
    val selectedSources: Set<String> = emptySet(),
    val errorMessage: String? = null
) : FidoViewModelState()

sealed class HeadlinesScreenViewModelIntent : FidoViewModelIntent() {
    data object OnLoadLatestArticles : HeadlinesScreenViewModelIntent()
    data object OnLoadSources : HeadlinesScreenViewModelIntent()
    data class OnSourceRemoved(val id: String) : HeadlinesScreenViewModelIntent()
    data class OnSourceSelected(val id: String) : HeadlinesScreenViewModelIntent()
}