package com.marcel.fido.ui.article

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

class ArticleScreenViewModel(
    private val articleRepository: ArticleRepository
) : FidoViewModel<
        ArticleScreenViewModelArguments,
        ArticleScreenViewModelIntent,
        ArticleScreenViewModelState>() {

    override val state: StateFlow<ArticleScreenViewModelState>
        get() = stateChanges

    override fun attachArguments(arguments: ArticleScreenViewModelArguments) {
        viewModelScope.launch {
            stateChanges.emit(state.value.copy(id = arguments.id))
        }
        onIntent(ArticleScreenViewModelIntent.OnLoadArticle(arguments.id))
    }

    override fun onIntent(intent: ArticleScreenViewModelIntent) {
        if(intent is ArticleScreenViewModelIntent.OnLoadArticle){
            viewModelScope.launch {
                val result = articleRepository.getArticle(intent.id)
                result.onSuccess {
                    stateChanges.emit(state.value.copy(article = it))
                }.onFailure {
                    stateChanges.emit(
                        state.value.copy(errorMessage = it.message ?: "Couldn't load article")
                    )
                }
            }
        }
    }

    private val stateChanges = MutableStateFlow(ArticleScreenViewModelState())
}

data class ArticleScreenViewModelArguments(val id: String) : FidoViewModelArguments()

data class ArticleScreenViewModelState(
    val id: String? = null,
    val article: Article? = null,
    val errorMessage: String? = null
) : FidoViewModelState()

sealed class ArticleScreenViewModelIntent : FidoViewModelIntent() {
    data class OnLoadArticle(val id: String): ArticleScreenViewModelIntent()
}