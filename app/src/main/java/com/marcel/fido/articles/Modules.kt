package com.marcel.fido.articles

import com.marcel.fido.articles.data.ArticleRemoteDataSource
import com.marcel.fido.articles.data.ArticleRepositoryImp
import com.marcel.fido.articles.domain.Article
import com.marcel.fido.articles.domain.ArticleRepository
import com.marcel.fido.articles.ui.ArticleListViewModel
import com.marcel.fido.core.Cache
import org.koin.dsl.module
import retrofit2.Retrofit

val articleModule = module {
    factory {
        Cache<String, Article>()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit.create(ArticleRemoteDataSource::class.java)
    }
    single<ArticleRepository> {
        ArticleRepositoryImp(get(), get())
    }
    single {
        ArticleListViewModel(get())
    }
}