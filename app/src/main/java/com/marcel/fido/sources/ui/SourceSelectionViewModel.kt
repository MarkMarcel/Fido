/*
package com.marcel.fido.sources.ui

import androidx.lifecycle.viewModelScope
import com.marcel.fido.core.FidoViewModel
import com.marcel.fido.core.FidoViewModelArguments
import com.marcel.fido.core.FidoViewModelIntent
import com.marcel.fido.core.FidoViewModelState
import com.marcel.fido.sources.domain.Source
import com.marcel.fido.sources.domain.SourceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SourceSelectionViewModel(
    private val sourceRepository: SourceRepository
) : FidoViewModel<
        SourceSelectionViewModelArguments,
        SourceSelectionViewModelIntent,
        SourceSelectionViewModelState>() {
    override val state: StateFlow<SourceSelectionViewModelState>
        get() = stateChanges

    private val stateChanges = MutableStateFlow(SourceSelectionViewModelState())

    override fun onIntent(intent: FidoViewModelIntent) {
        when (intent) {
            SourceSelectionViewModelIntent.OnLoadSources -> {
                viewModelScope.launch {
                    sourceRepository.getSources().onSuccess {
                            stateChanges.emit(state.value.copy(allSources = it))
                    }.onFailure {
                        stateChanges.emit(
                            state.value.copy(errorMessage = it.message?:"Couldn't load sources")
                        )
                    }
                }
            }

            is SourceSelectionViewModelIntent.OnSourceRemoved -> {
                val currentSelection = state.value.selectedSources.toMutableSet()
                currentSelection.remove(intent.id)
                viewModelScope.launch {
                    stateChanges.emit(state.value.copy(selectedSources = currentSelection.toSet()))
                }
            }

            is SourceSelectionViewModelIntent.OnSourceSelected -> {
                val currentSelection = state.value.selectedSources.toMutableSet()
                currentSelection.clear()
                currentSelection.add(intent.id)
                viewModelScope.launch {
                    stateChanges.emit(state.value.copy(selectedSources = currentSelection.toSet()))
                }
            }
        }
    }

    override fun attachArguments(arguments: SourceSelectionViewModelArguments) {}
}

class SourceSelectionViewModelArguments : FidoViewModelArguments()

data class SourceSelectionViewModelState(
    val allSources: List<Source> = emptyList(),
    val selectedSources: Set<String> = emptySet(),
    val errorMessage: String? = null
) : FidoViewModelState()

sealed class SourceSelectionViewModelIntent : FidoViewModelIntent() {
    data object OnLoadSources : SourceSelectionViewModelIntent()
    data class OnSourceRemoved(val id: String) : SourceSelectionViewModelIntent()
    data class OnSourceSelected(val id: String) : SourceSelectionViewModelIntent()
}*/
