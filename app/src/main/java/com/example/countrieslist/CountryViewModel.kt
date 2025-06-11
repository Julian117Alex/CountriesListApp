package com.example.countrieslist


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted


class CountryViewModel : ViewModel() {

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    val filteredCountries: StateFlow<List<Country>> = combine(_countries, _searchText) { countries, query ->
        if (query.isBlank()) countries
        else countries.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.capital.contains(query, ignoreCase = true)
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        fetchCountries()
    }

    fun onSearchTextChange(newText: String) {
        _searchText.value = newText
    }

    private fun fetchCountries() {
        viewModelScope.launch {
            try {
//                val list = loadCountriesFromUrl()
                val list = CountryRepository.getCountries()

                _countries.value = list
            } catch (e: Exception) {
                _countries.value = emptyList() // Optionally handle errors
            }
        }
    }
}


