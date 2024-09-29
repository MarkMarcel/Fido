package com.marcel.fido.ui

import com.marcel.fido.ui.article.ArticleScreenViewModel
import com.marcel.fido.ui.headlines.HeadlinesScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        ArticleScreenViewModel(get())
    }
    viewModel {
        HeadlinesScreenViewModel(get(), get())
    }
}