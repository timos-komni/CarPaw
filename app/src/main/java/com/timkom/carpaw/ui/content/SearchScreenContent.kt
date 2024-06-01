package com.timkom.carpaw.ui.content

import androidx.compose.runtime.Composable
import com.timkom.carpaw.ui.components.SearchLocationBar
import com.timkom.carpaw.ui.screens.searchRide.SearchRideViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun SearchScreenContent(
    placeholder: Int,
    label: Int,
    viewModel: SearchRideViewModel = viewModel()
) {
    SearchLocationBar(
        placeholder = placeholder,
        label = label,
        queryText = viewModel.searchText.value,
        onQueryChange = { viewModel.onQueryChange(it) },
        active = viewModel.searchActive.value,
        onActiveChange = { viewModel.onActiveChange(it) },
        onSearch = { viewModel.onSearch() },
        items = viewModel.items
    )
}


