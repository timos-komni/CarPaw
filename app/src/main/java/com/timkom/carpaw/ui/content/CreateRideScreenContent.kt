import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.SearchLocationBar
import com.timkom.carpaw.ui.screens.createRide.CreateRideViewModel
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun CreateRideScreenContent(
    location1Placeholder: Int,
    location1Label: Int,
    location2Placeholder: Int,
    location2Label: Int,
    viewModel: CreateRideViewModel = viewModel()
) {
    Column(modifier = Modifier.padding(8.dp)) {
        SearchLocationBar(
            placeholder = location1Placeholder,
            label = location1Label,
            queryText = viewModel.searchLocationText.value,
            onQueryChange = { viewModel.onLocationQueryChange(it) },
            active = viewModel.searchLocationActive.value,
            onActiveChange = { viewModel.onLocationActiveChange(it) },
            onSearch = { viewModel.onLocationSearch() },
            items = viewModel.items
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchLocationBar(
            placeholder = location2Placeholder,
            label = location2Label,
            queryText = viewModel.searchAddressText.value,
            onQueryChange = { viewModel.onAddressQueryChange(it) },
            active = viewModel.searchAddressActive.value,
            onActiveChange = { viewModel.onAddressActiveChange(it) },
            onSearch = { viewModel.onAddressSearch() },
            items = viewModel.items
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenContentPreview() {
    CarPawTheme {
        CreateRideScreenContent(
            location1Placeholder = R.string.search_departure__placeholder,
            location1Label = R.string.search_departure__label,
            location2Placeholder = R.string.pickup_address__placeholder,
            location2Label = R.string.pickup_address__label
        )
    }
}
