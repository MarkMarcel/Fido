package com.marcel.fido.sources

import com.marcel.fido.sources.data.SourceRemoteDataSource
import com.marcel.fido.sources.data.SourceRepositoryImp
import com.marcel.fido.sources.domain.SourceRepository
import org.koin.dsl.module
import retrofit2.Retrofit

val sourceModule = module {
    single {
        val retrofit: Retrofit = get()
        retrofit.create(SourceRemoteDataSource::class.java)
    }
    single<SourceRepository> {
        SourceRepositoryImp(get())
    }
}