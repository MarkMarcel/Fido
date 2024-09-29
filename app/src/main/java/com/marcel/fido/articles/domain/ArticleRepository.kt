package com.marcel.fido.articles.domain

import com.marcel.fido.sources.domain.Source

interface ArticleRepository {
    fun getArticle(id: String): Result<Article?>

    suspend fun getHeadlinesFromSources(sources: List<Source>): Result<List<Article>>
}