package com.marcel.fido.sources.data

import retrofit2.http.GET

interface SourceRemoteDataSource {
    @GET("top-headlines/sources?language=en")
    suspend fun getSources(): SourcesResponseBody
}