package com.marcel.fido.ui.headlines

import androidx.lifecycle.viewModelScope
import com.marcel.fido.articles.ui.ArticleListViewModel
import com.marcel.fido.articles.ui.ArticleListViewModelState
import com.marcel.fido.core.CompositeFidoViewModel
import com.marcel.fido.core.FidoViewModel
import com.marcel.fido.core.FidoViewModelArguments
import com.marcel.fido.core.FidoViewModelIntent
import com.marcel.fido.core.FidoViewModelState
import com.marcel.fido.sources.ui.SourceSelectionViewModel
import com.marcel.fido.sources.ui.SourceSelectionViewModelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HeadlinesScreenViewModel(
    private val articleListViewModel: ArticleListViewModel,
    private val sourceSelectionViewModel: SourceSelectionViewModel
) : CompositeFidoViewModel<
        HeadlinesScreenViewModelArguments,
        HeadlinesScreenViewModelIntent,
        HeadlinesScreenViewModelState>() {
    override val childViewModels: List<FidoViewModel<out FidoViewModelArguments, *, *>>
        get() = listOf(articleListViewModel, sourceSelectionViewModel)

    override val state: StateFlow<HeadlinesScreenViewModelState>
        get() = stateChanges

    override fun attachArguments(arguments: HeadlinesScreenViewModelArguments) {
        viewModelScope.launch {
            articleListViewModel.state.collectLatest {
                stateChanges.emit(state.value.copy(articleListViewModelState = it))
            }
        }
        viewModelScope.launch {
            sourceSelectionViewModel.state.collectLatest {
                stateChanges.emit(state.value.copy(sourceSelectionViewModelState = it))
            }
        }
    }

    private val stateChanges = MutableStateFlow(HeadlinesScreenViewModelState())
}

class HeadlinesScreenViewModelArguments : FidoViewModelArguments()

data class HeadlinesScreenViewModelState(
    val articleListViewModelState: ArticleListViewModelState = ArticleListViewModelState(),
    val sourceSelectionViewModelState: SourceSelectionViewModelState = SourceSelectionViewModelState()
) : FidoViewModelState()

sealed class HeadlinesScreenViewModelIntent : FidoViewModelIntent()