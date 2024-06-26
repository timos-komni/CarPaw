package com.timkom.carpaw.ui.screens

import com.timkom.carpaw.ui.components.cards.CreatedRideCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.NoRidesMessage
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.MyRidesViewModel

@Composable
fun MyRidesScreen(
    modifier: Modifier = Modifier,
    viewModel: MyRidesViewModel = viewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Booked", "Created")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> BookedRidesTab(viewModel)
            1 -> CreatedRidesTab(viewModel)
        }
    }
}

@Composable
fun BookedRidesTab(viewModel: MyRidesViewModel) {
 NoRidesMessage(
     message = stringResource(id = R.string.no_booked_rides__text),
     imagePainter = painterResource(id = R.drawable.empty_street_foreground))

}

@Composable
fun CreatedRidesTab(viewModel: MyRidesViewModel) {
    LaunchedEffect(Unit) {
        viewModel.createdRides.clear()
        viewModel.createdRides.addAll(viewModel.loadCreatedRides().await())
    }

    if (viewModel.createdRides.isEmpty()) {
        NoRidesMessage(
            message = stringResource(id = R.string.no_created_rides__text),
            imagePainter = painterResource(id = R.drawable.empty_street_foreground)
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.createdRides) { rideData ->
                CreatedRideCard(data = rideData, onClick = {
                    // Handle edit ride click
                })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyRidesScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        MyRidesScreen()
    }
}