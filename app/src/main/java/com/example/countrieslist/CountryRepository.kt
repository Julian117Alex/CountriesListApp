package com.example.countrieslist


object CountryRepository {
    suspend fun getCountries(): List<Country> {
        return RetrofitInstance.api.getCountries()
    }
}