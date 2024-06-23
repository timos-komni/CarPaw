package com.timkom.carpaw.ui.screens


import SearchResultCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.ui.viewmodels.SearchRideViewModel

@Composable
fun AvailableRidesScreen(
    viewModel: SearchRideViewModel = viewModel(),
    onViewRideDetailsClick: (Ride) -> Unit

) {
    val availableRides = viewModel.getAvailableRides()
    val startLocation = viewModel.startSearchText.value
    val destination = viewModel.destinationSearchText.value
    val date = viewModel.selectedDate.value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        SearchTitle(startLocation = startLocation, destination = destination, date = date)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(availableRides) { data ->
                SearchResultCard(
                    data = data,
                    onClick = { onViewRideDetailsClick(data.ride)}
                )
            }
        }
    }
}

@Composable
fun SearchTitle(startLocation: String, destination: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.route),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$startLocation to $destination",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = stringResource(R.string.decorative_icon),
                modifier = Modifier.padding(end = 10.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = date,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AvailableRidesScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        AvailableRidesScreen(onViewRideDetailsClick = {})
    }
}

