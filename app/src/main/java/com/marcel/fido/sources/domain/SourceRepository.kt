package com.marcel.fido.sources.domain

interface SourceRepository {
    suspend fun getSources(): Result<List<Source>>
}