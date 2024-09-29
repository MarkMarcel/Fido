package com.marcel.fido.articles.domain

import com.marcel.fido.sources.domain.Source
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Article(
    val id: String = UUID.randomUUID().toString(),
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: LocalDateTime,
    val source: Source,
    val title: String,
    val urlToImage: String
)
