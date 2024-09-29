package com.marcel.fido.core

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val client = OkHttpClient.Builder().apply {
    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor)
        .callTimeout(5, TimeUnit.SECONDS)
    addInterceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("X-Api-Key", "1d7284956dd244678576464a5260d256")
            .build()
        chain.proceed(request)
    }
}.build()

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://newsapi.org/v2/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()