package com.example.countrieslist

import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory




object RetrofitInstance {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    val api: CountryApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(CountryApiService::class.java)
    }
}