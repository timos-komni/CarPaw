package com.timkom.carpaw.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.Ride
import com.timkom.carpaw.ui.components.PriceRow
import com.timkom.carpaw.ui.components.buttons.CustomButton
import com.timkom.carpaw.ui.components.cards.DriverDetailsCard
import com.timkom.carpaw.ui.data.CompanionAnimalItem
import com.timkom.carpaw.ui.viewmodels.SearchRideViewModel
import com.timkom.carpaw.util.formatDateTime

@SuppressLint("ResourceType")
@Composable
fun RideDetailsScreen(
    viewModel: SearchRideViewModel = viewModel(),
    rideId: Long
) {
    val ride = viewModel.selectedRide.value
    val user = ride?.let { viewModel.getUserById(it.ownerId!!.toLong()) }
    val selectedAnimals = ride?.let { viewModel.getSelectedAnimalsByRideId(it.id) }.orEmpty()
    val sortedAnimals = CompanionAnimalItem.entries.sortedByDescending { selectedAnimals.contains(it) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        item{
            ride?.let {
                RideDetails(ride = it)
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = "Animals that ${user?.firstName ?: ""} wants to travel with:",
                    style = MaterialTheme.typography.titleMedium,
                    //fontWeight = FontWeight.Bold
                )
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sortedAnimals) { animal ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = animal.icon),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                colorFilter = if (selectedAnimals.contains(animal)) {
                                    ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                                } else {
                                    ColorFilter.tint(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f))
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = pluralStringResource(id = animal.animalName, count = 1),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                PriceRow(price = ride.price)
                CustomButton(
                    title = R.string.ask_ride__button,
                    onClick = { /*TODO*/ },
                    enabled = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.driver_details__title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                user?.let {
                    DriverDetailsCard(user = it)
                }
            }
        }

    }
}


@Composable
fun RideDetails(ride: Ride) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()

    ) {
        RideInfoRow(
            icon = R.drawable.route,
            location = ride.start,
            address = ride.startAddress,
            dateTime = ride.date
        )
        Spacer(modifier = Modifier.height(8.dp))
        RideInfoRow(
            icon = R.drawable.route,
            location = ride.destination,
            address = ride.destinationAddress,
            dateTime = ride.date
        )
    }
}

@Composable
fun RideInfoRow(icon: Int, location: String, dateTime: String, address: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column {
            Text(
                text = "$address, $location",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatDateTime(dateTime),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}
