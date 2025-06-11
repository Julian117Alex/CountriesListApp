package com.example.countrieslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.countrieslist.ui.theme.CountriesListTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.activity.viewModels




class MainActivity : ComponentActivity() {
    private val viewModel: CountryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesListTheme(
                darkTheme = false // Force light mode
            ) {
                CountryListScreen(viewModel)
            }
        }
    }
}


@Composable
fun CountryRow(c: Country) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("${c.name}, ${c.region}", style = MaterialTheme.typography.bodyLarge)
                Text(c.code, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.End)
            }
            Spacer(Modifier.height(4.dp))
            Text(c.capital, style = MaterialTheme.typography.bodyMedium)
        }
    }
}



suspend fun loadCountriesFromUrl(): List<Country> = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
        .build()

    val response = client.newCall(request).execute()
    if (!response.isSuccessful) throw Exception("HTTP error ${response.code}")

    val jsonString = response.body?.string() ?: throw Exception("Empty response body")
    val json = Json { ignoreUnknownKeys = true }
    json.decodeFromString(ListSerializer(Country.serializer()), jsonString)
}