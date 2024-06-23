package com.timkom.carpaw.ui.components

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.timkom.carpaw.R
import com.timkom.carpaw.data.places.PlacesManager
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.createTAGForKClass
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class SearchLocationViewModel : ViewModel() {
    val items = mutableStateListOf<AutocompletePrediction>()
    var queryText = mutableStateOf("")
    var isActive = mutableStateOf(false)
    var result = mutableStateOf<AutocompletePrediction?>(null)

    fun searchLocation(context: Context, query: String, searchType: PlacesManager.SearchType) {
        val placesManager = PlacesManager.getInstance(context)
        viewModelScope.launch {
            Executors.newSingleThreadExecutor().execute {
                val result = placesManager.request(query, searchType)
                result?.let {
                    items.clear()
                    for (prediction in it.autocompletePredictions) {
                        Log.e(createTAGForKClass(SearchLocationViewModel::class), prediction.toString())
                        items.add(prediction)
                    }
                }
            }
        }
    }

    fun reset() {
        items.clear()
        queryText.value = ""
        isActive.value = false
        result.value = null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
    @StringRes placeholder: Int,
    @StringRes label: Int,
    queryText: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = label),
            lineHeight = 1.33.em,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.height(10.dp))
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 200.dp),
            query = queryText,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(
                    text = stringResource(id = placeholder),
                    fontFamily = FontFamily(Font(R.font.outfit_regular)))
            },
            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.background),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
                if (!active) {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                } else {
                    Icon(
                        modifier = Modifier.clickable {
                            onActiveChange(false)
                            onQueryChange("")
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Search Icon"
                    )
                }
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            onQueryChange("")
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        ) {
            items.forEach {
                Row(
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(R.drawable.history),
                        contentDescription = "History Icon"
                    )
                    Text(text = it, fontFamily = FontFamily(Font(R.font.outfit_regular)))
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar2(
    @StringRes placeholder: Int,
    @StringRes label: Int,
    onSelection: (AutocompletePrediction?) -> Unit,
    modifier: Modifier = Modifier,
    typeFilter: PlacesManager.SearchType = PlacesManager.SearchType.CITIES,
    viewModelKey: String? = null
) {
    val context = LocalContext.current
    val searchLocationViewModel: SearchLocationViewModel = viewModel(key = viewModelKey)
    var queryText by searchLocationViewModel.queryText
    var isActive by searchLocationViewModel.isActive

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = label),
            lineHeight = 1.33.em,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.height(10.dp))
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 200.dp),
            query = queryText,
            onQueryChange = { query ->
                queryText = query
                searchLocationViewModel.searchLocation(context, query, typeFilter)
            },
            onSearch = {
                searchLocationViewModel.searchLocation(context, queryText, typeFilter)
            },
            active = isActive,
            onActiveChange = { isActive = it },
            placeholder = {
                Text(
                    text = stringResource(id = placeholder),
                    fontFamily = FontFamily(Font(R.font.outfit_regular)))
            },
            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.background),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(14.dp),
            leadingIcon = {
                if (!isActive) {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                } else {
                    Icon(
                        modifier = Modifier.clickable {
                            queryText = ""
                            searchLocationViewModel.items.clear()
                            isActive = false
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Search Icon"
                    )
                }
            },
            trailingIcon = {
                if (isActive) {
                    Icon(
                        modifier = Modifier.clickable {
                            queryText = ""
                            searchLocationViewModel.items.clear()
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        ) {
            searchLocationViewModel.items.forEach {
                Row(
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .clickable {
                            searchLocationViewModel.items.clear()
                            searchLocationViewModel.result.value = it
                            isActive = false
                            queryText = it.getFullText(null).toString()
                            onSelection.invoke(searchLocationViewModel.result.value)
                            //searchLocationViewModel.reset()
                        }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(R.drawable.history),
                        contentDescription = "History Icon"
                    )
                    Text(text = it.getFullText(null).toString(), fontFamily = FontFamily(Font(R.font.outfit_regular)))
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchLocationBarPreview() {
    CarPawTheme(dynamicColor = false) {
        SearchLocationBar(
            placeholder = R.string.search_departure__placeholder,
            label = R.string.search_departure__label,
            queryText = "",
            onQueryChange = {},
            active = false,
            onActiveChange = {},
            onSearch = {},
            items = listOf("Athens", "Kavala")
        )
    }
}
