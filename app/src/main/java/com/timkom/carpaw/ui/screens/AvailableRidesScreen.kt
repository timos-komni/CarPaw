package com.timkom.carpaw.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.SearchResultCard
import com.timkom.carpaw.ui.components.cards.SearchResultCardData
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.AvailableRidesViewModel

@Composable
fun AvailableRidesScreen(
    pmStartLocation: String?,
    pmDestinationLocation: String?,
    pmDate: String?,
    pmAnimals: List<CompanionAnimalItem>?,
    onViewRideDetailsClick: (SearchResultCardData) -> Unit
) {
    val viewModel: AvailableRidesViewModel = viewModel()

    var startLocation by viewModel.startLocation
    var destination by viewModel.destinationLocation
    var date by viewModel.date
    startLocation = pmStartLocation ?: ""
    destination = pmDestinationLocation ?: ""
    date = pmDate ?: ""
    pmAnimals?.let { viewModel.setAnimalsFromList(it) }

    LaunchedEffect(Unit) {
        viewModel.availableRides.clear()
        viewModel.availableRides.addAll(viewModel.getAvailableRides().await())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //TODO see this Timo
        SearchTitle(startLocation = startLocation, destination = destination, date = date)
        Spacer(modifier = Modifier.height(16.dp))

        if(viewModel.availableRides.isEmpty()){
            NoAvailableRidesMessage()
        }else{
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(viewModel.availableRides) { data ->
                    SearchResultCard(
                        data = data,
                        onClick = { onViewRideDetailsClick(data) }
                    )
                }
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

@Composable
fun NoAvailableRidesMessage() {
   Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
       //verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_available_rides__text),
            lineHeight = 1.4.em,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
       Image(
           painter = painterResource(id = R.drawable.empty_street_foreground),
           //colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
           contentDescription = "decorative",
           modifier = Modifier.size(400.dp)
       )
    }
}


@Preview(showBackground = true)
@Composable
fun AvailableRidesScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        AvailableRidesScreen("", "", "", emptyList(), onViewRideDetailsClick = {})
    }
}

