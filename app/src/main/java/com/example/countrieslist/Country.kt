package com.example.countrieslist

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)
