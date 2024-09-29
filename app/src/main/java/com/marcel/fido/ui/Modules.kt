package com.marcel.fido.ui

import com.marcel.fido.ui.headlines.HeadlinesScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HeadlinesScreenViewModel(get(), get())
    }
}