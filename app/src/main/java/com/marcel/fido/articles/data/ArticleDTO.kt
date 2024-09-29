package com.marcel.fido.articles.data

import com.marcel.fido.articles.domain.Article
import com.marcel.fido.sources.data.SourceDTO
import com.marcel.fido.sources.data.toDomain
import kotlinx.datetime.LocalDateTime

data class ArticlesResponseBody(val articles: List<ArticleDTO>)

data class ArticleDTO(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceDTO,
    val title: String,
    val urlToImage: String
)

fun ArticleDTO.toDomain(): Article {
    val date = LocalDateTime.parse(publishedAt.removeSuffix("Z"))
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = date,
        source = source.toDomain(),
        title = title,
        urlToImage = urlToImage
    )
}