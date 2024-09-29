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

    abstract fun onIntent(intent: I)
}