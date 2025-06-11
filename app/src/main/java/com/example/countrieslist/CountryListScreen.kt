package com.example.countrieslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CountryListScreen(viewModel: CountryViewModel) {
    val countries = viewModel.filteredCountries.collectAsState()
    val searchText = viewModel.searchText.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchText.value,
            onValueChange = viewModel::onSearchTextChange,
            placeholder = { Text("Search by name or capital") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(countries.value) { country ->
                CountryRow(country)
            }
        }
    }
}

