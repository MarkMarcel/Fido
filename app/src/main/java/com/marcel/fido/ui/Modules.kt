package com.marcel.fido.ui

import com.marcel.fido.ui.headlines.HeadlinesScreenViewModel
import org.koin.dsl.module

val uiModule = module {
    single {
        HeadlinesScreenViewModel(get(), get())
    }
}