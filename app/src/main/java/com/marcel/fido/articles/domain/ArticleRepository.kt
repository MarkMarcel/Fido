package com.marcel.fido.articles.domain

interface ArticleRepository {
    fun getArticle(id: String): Result<Article?>

    suspend fun getHeadlinesFromSources(sources: List<String>): Result<List<Article>>
}