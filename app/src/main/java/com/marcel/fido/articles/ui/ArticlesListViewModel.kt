/*
package com.marcel.fido.articles.ui

import androidx.lifecycle.viewModelScope
import com.marcel.fido.articles.domain.Article
import com.marcel.fido.articles.domain.ArticleRepository
import com.marcel.fido.core.FidoViewModel
import com.marcel.fido.core.FidoViewModelArguments
import com.marcel.fido.core.FidoViewModelIntent
import com.marcel.fido.core.FidoViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleListViewModel(
    private val articleRepository: ArticleRepository
) : FidoViewModel<
        ArticleListViewModelArguments,
        ArticleListViewModelIntent,
        ArticleListViewModelState>() {
    override val state: StateFlow<ArticleListViewModelState>
        get() = stateChanges

    private val stateChanges = MutableStateFlow(ArticleListViewModelState())

    override fun onIntent(intent: FidoViewModelIntent) {
        when(intent){
            is ArticleListViewModelIntent.OnLoadArticles -> {
                viewModelScope.launch {
                    articleRepository.getHeadlinesFromSources(intent.sources).onSuccess {
                        stateChanges.emit(state.value.copy(articles = it))
                    }.onFailure {
                        stateChanges.emit(
                            state.value.copy(errorMessage = it.message?:"Couldn't load articles")
                        )
                    }
                }
            }
        }
    }

    override fun attachArguments(arguments: ArticleListViewModelArguments) {}
}

class ArticleListViewModelArguments : FidoViewModelArguments()

data class ArticleListViewModelState(
    val articles: List<Article> = emptyList(),
    val errorMessage: String? = null
) : FidoViewModelState()

sealed class ArticleListViewModelIntent : FidoViewModelIntent() {
    data class OnLoadArticles(val sources: List<String>): ArticleListViewModelIntent()
}*/
