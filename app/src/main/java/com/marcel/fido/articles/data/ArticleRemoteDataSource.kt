package com.marcel.fido.articles.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRemoteDataSource {
    @GET("top-headlines")
    suspend fun getHeadlinesFromSources(
        @Query("sources") sources: List<String>
    ): ArticlesResponseBody
}