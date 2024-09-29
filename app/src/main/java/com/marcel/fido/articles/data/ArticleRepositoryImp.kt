package com.marcel.fido.articles.data

import com.marcel.fido.articles.domain.Article
import com.marcel.fido.articles.domain.ArticleRepository
import com.marcel.fido.core.Cache
import com.marcel.fido.sources.domain.Source

class ArticleRepositoryImp(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val cache: Cache<String, Article>
) : ArticleRepository {
    override fun getArticle(id: String): Result<Article?> {
        return try {
            Result.success(cache.get(id, Article::id))
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getHeadlinesFromSources(sources: List<String>): Result<List<Article>> {
        return try {
            val data = articleRemoteDataSource.getHeadlinesFromSources(sources).articles
            val articles = data.map { it.toDomain() }
            updateCacheWithLatestArticles(articles)
            return Result.success(cache.getAll())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun updateCacheWithLatestArticles(articles: List<Article>){
        cache.clear()
        cache.addAll(articles)
    }
}