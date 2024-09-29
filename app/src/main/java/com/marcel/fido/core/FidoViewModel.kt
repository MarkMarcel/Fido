package com.marcel.fido.core

import androidx.lifecycle.ViewModel
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
        S : FidoViewModelState> : ViewModel() {
    abstract val state: StateFlow<S>

    abstract fun attachArguments(arguments: A)

    abstract fun onIntent(intent: FidoViewModelIntent)
}

abstract class CompositeFidoViewModel<
        A : FidoViewModelArguments,
        I : FidoViewModelIntent,
        S : FidoViewModelState> : FidoViewModel<A, I, S>() {
    abstract val childViewModels: List<FidoViewModel<out FidoViewModelArguments, *, *>>

    open override fun onIntent(intent: FidoViewModelIntent) {
        childViewModels.forEach {
            it.onIntent(intent)
        }
    }
}