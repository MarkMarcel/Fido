package com.marcel.fido.sources.data

import com.marcel.fido.sources.domain.Source
import com.marcel.fido.sources.domain.SourceRepository

class SourceRepositoryImp(private val sourceRemoteDataSource: SourceRemoteDataSource) : SourceRepository {
    override suspend fun getSources(): Result<List<Source>> {
        return try {
            val data = sourceRemoteDataSource.getSources().sources
            val sources = data.map { it.toDomain() }
            return Result.success(sources)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}