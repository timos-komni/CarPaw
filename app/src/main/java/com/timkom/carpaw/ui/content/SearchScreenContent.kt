package com.timkom.carpaw.ui.content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.ui.components.SearchLocationBar2
import com.timkom.carpaw.ui.viewmodels.SearchRideViewModel

@Composable
fun SearchScreenContent(
    contentType: SearchContentType,
    placeholder: Int,
    label: Int,
    viewModel: SearchRideViewModel = viewModel(),
    searchBarViewModelKey: String = "SearchScreenContent_Location"
) {
    /*SearchLocationBar(
        placeholder = placeholder,
        label = label,
        queryText = when(contentType) {
            SearchContentType.STARTING_POINT -> viewModel.startSearchText.value
            SearchContentType.DESTINATION -> viewModel.destinationSearchText.value
        },
        onQueryChange = { viewModel.onQueryChange(contentType, it) },
        active = when(contentType) {
            SearchContentType.STARTING_POINT -> viewModel.isStartSearchActive.value
            SearchContentType.DESTINATION -> viewModel.isDestinationActive.value
        },
        onActiveChange = { viewModel.onActiveChange(contentType, it) },
        onSearch = { viewModel.onSearch(contentType) },
        items = viewModel.items
    )*/
    SearchLocationBar2(
        placeholder = placeholder,
        label = label,
        onSelection = { viewModel.onResulSelected(contentType, it?.getFullText(null).toString()) },
        viewModelKey = searchBarViewModelKey
    )
}


