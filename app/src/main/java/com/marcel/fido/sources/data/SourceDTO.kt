package com.marcel.fido.sources.data

import com.marcel.fido.sources.domain.Source

data class SourcesResponseBody(val sources: List<SourceDTO>)

data class SourceDTO(val id: String, val name: String)

fun SourceDTO.toDomain() = Source(id = id, name = name)
