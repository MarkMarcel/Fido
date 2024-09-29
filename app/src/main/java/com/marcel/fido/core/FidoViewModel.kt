package com.marcel.fido.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KProperty1

abstract class FidoViewModelArguments
abstract class FidoViewModelIntent
abstract class FidoViewModelState

abstract class FidoViewModel<
        A : FidoViewModelArguments,
        I : FidoViewModelIntent,
        S : FidoViewModelState> {
    abstract val state: StateFlow<S>

    abstract fun attachArguments(arguments: A)

    abstract fun onIntent(intent: FidoViewModelIntent)

    protected fun <V> getValueChanges(stateProperty: KProperty1<S, V>): Flow<V> {
        return state.map { state -> stateProperty.get(state) }
    }
}

abstract class CompositeFidoViewModel<
        A : FidoViewModelArguments,
        I : FidoViewModelIntent,
        S : FidoViewModelState>(
    private val childViewModels: List<FidoViewModel<
            FidoViewModelArguments,
            FidoViewModelIntent,
            FidoViewModelState>>
) : FidoViewModel<A, I, S>() {
    open override fun onIntent(intent: FidoViewModelIntent) {
        childViewModels.forEach {
            it.onIntent(intent)
        }
    }
}