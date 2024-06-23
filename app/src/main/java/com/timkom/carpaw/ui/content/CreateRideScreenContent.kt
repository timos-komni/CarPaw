package com.timkom.carpaw.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.data.places.PlacesManager
import com.timkom.carpaw.ui.components.SearchLocationBar2
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.CreateRideViewModel

const val LOCATION_SEARCH_VIEW_MODEL_KEY = "CreateRideLocation"
const val ADDRESS_SEARCH_VIEW_MODEL_KEY = "CreateRideAddress"

@Composable
fun CreateRideScreenContent(
    contentType: CreateContentType,
    location1Placeholder: Int,
    location1Label: Int,
    location2Placeholder: Int,
    location2Label: Int,
    viewModel: CreateRideViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(8.dp)) {
        /*SearchLocationBar(
            placeholder = location1Placeholder,
            label = location1Label,
            queryText = when(contentType) {
                CreateContentType.STARTING_POINT -> viewModel.startData.value.searchLocationText.value
                CreateContentType.DESTINATION -> viewModel.destinationData.value.searchLocationText.value
            },
            onQueryChange = { viewModel.onLocationQueryChange(contentType, it) },
            active = when(contentType) {
                CreateContentType.STARTING_POINT -> viewModel.startData.value.isSearchLocationActive.value
                CreateContentType.DESTINATION -> viewModel.destinationData.value.isSearchLocationActive.value
            },
            onActiveChange = { viewModel.onLocationActiveChange(contentType, it) },
            onSearch = { viewModel.onLocationSearch(contentType) },
            items = viewModel.items
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchLocationBar(
            placeholder = location2Placeholder,
            label = location2Label,
            queryText = when(contentType) {
                CreateContentType.STARTING_POINT -> viewModel.startData.value.searchAddressText.value
                CreateContentType.DESTINATION -> viewModel.destinationData.value.searchAddressText.value
            },
            onQueryChange = { viewModel.onAddressQueryChange(contentType, it) },
            active = when(contentType) {
                CreateContentType.STARTING_POINT -> viewModel.startData.value.isSearchAddressActive.value
                CreateContentType.DESTINATION -> viewModel.destinationData.value.isSearchAddressActive.value
            },
            onActiveChange = { viewModel.onAddressActiveChange(contentType, it) },
            onSearch = { viewModel.onAddressSearch(contentType) },
            items = viewModel.items
        )*/
        SearchLocationBar2(
            placeholder = location1Placeholder,
            label = location1Label,
            onSelection = { viewModel.onLocationResultSelected(contentType, it?.getFullText(null).toString()) },
            viewModelKey = LOCATION_SEARCH_VIEW_MODEL_KEY
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchLocationBar2(
            placeholder = location2Placeholder,
            label = location2Label,
            onSelection = { viewModel.onAddressResultSelected(contentType, it?.getFullText(null).toString()) },
            typeFilter = PlacesManager.SearchType.ADDRESS,
            viewModelKey = ADDRESS_SEARCH_VIEW_MODEL_KEY
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenContentPreview() {
    CarPawTheme {
        CreateRideScreenContent(
            contentType = CreateContentType.STARTING_POINT,
            location1Placeholder = R.string.search_departure__placeholder,
            location1Label = R.string.search_departure__label,
            location2Placeholder = R.string.pickup_address__placeholder,
            location2Label = R.string.pickup_address__label
        )
    }
}
