package com.timkom.carpaw.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.FullScreenDialog
import com.timkom.carpaw.ui.FullScreenDialogWithoutTitle
import com.timkom.carpaw.ui.components.buttons.CustomButton
import com.timkom.carpaw.ui.components.buttons.ElevatedIconButton
import com.timkom.carpaw.ui.components.cards.ExpandableCard
import com.timkom.carpaw.ui.content.AnimalListMode
import com.timkom.carpaw.ui.content.CompanionAnimalList
import com.timkom.carpaw.ui.content.CreateContentType
import com.timkom.carpaw.ui.content.CreateRideScreenContent
import com.timkom.carpaw.ui.content.DatePickerContent
import com.timkom.carpaw.ui.content.PriceInputContent
import com.timkom.carpaw.ui.content.createContentList
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.viewmodels.CreateRideViewModel
import com.timkom.carpaw.util.Either
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

@Composable
fun CreateRideScreen(
    viewModel: CreateRideViewModel = viewModel(),
    modifier: Modifier = Modifier,
    onNavigateToMyRides: () -> Unit
) {
    // List of content items for creating a ride
    val contentList = createContentList()
    // Coroutine scope for launching asynchronous operations
    val coroutineScope = rememberCoroutineScope()
    //State to control the visibility of the confirmation dialog
    var showDialog by remember { mutableStateOf(false)}

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp,
            ),
            modifier = Modifier
                .padding(20.dp)
        ) {
            items(contentList) { content ->
                ExpandableCard(
                    title = content.title,
                    expanded = viewModel.expandedItem.intValue == content.id,
                    selectedInfo = when(content.id) {
                        0 -> viewModel.startData.value.searchLocationText.value
                        1 -> viewModel.destinationData.value.searchLocationText.value
                        else -> ""
                    },
                    onClickExpanded = { viewModel.onItemClick(content.id) },
                    content = {
                        CreateRideScreenContent(
                            content.type as CreateContentType,
                            location1Placeholder = content.locationPlaceholder,
                            location1Label = content.locationLabel,
                            location2Placeholder = content.addressPlaceholder,
                            location2Label = content.addressLabel,
                            searchBarLocationViewModelKey = "CreateRideScreenContent_Location${content.id}",
                            searchBarAddressViewModelKey = "CreateRideScreenContent_Address${content.id}"
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                ExpandableCard(
                    title = R.string.date_time__title,
                    expanded = viewModel.expandedItem.intValue == 2,
                    selectedInfo = viewModel.selectedDate.value,
                    onClickExpanded = { viewModel.onItemClick(2) },
                    content = {
                        DatePickerContent(
                            selectedDate = viewModel.selectedDate,
                            label = R.string.select_date__label,
                            isDialogOpen = viewModel.isDialogOpen,
                            setDate = { date -> viewModel.setDate(date) },
                            closeDialog = { viewModel.closeDialog() }
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                ExpandableCard(
                    title = R.string.preferred_passengers__title,
                    expanded = viewModel.expandedItem.intValue == 3,
                    selectedInfo = viewModel.selectedAnimalsSummary.value,
                    onClickExpanded = { viewModel.onItemClick(3) },
                    content = {
                        val context = LocalContext.current
                        CompanionAnimalList(
                            animals = viewModel.animals,
                            mode = AnimalListMode.SELECTION,
                            onAnimalSelect = { animal, isSelected ->
                                viewModel.toggleAnimalSelected(WeakReference(context), animal, isSelected)
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                ExpandableCard(
                    title = R.string.set_price__title,
                    expanded = viewModel.expandedItem.intValue == 4,
                    selectedInfo = viewModel.price.value,
                    onClickExpanded = { viewModel.onItemClick(4) },
                    content = {
                        PriceInputContent(
                            price = viewModel.price,
                            label = R.string.price__label,
                            isDialogOpen = viewModel.isPriceDialogOpen,
                            setPrice = { newPrice -> viewModel.setPrice(newPrice) },
                            closeDialog = { viewModel.closePriceDialog() }
                        )
                    }
                )
                Spacer(modifier = Modifier.size(30.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    ElevatedIconButton(
                        title = R.string.create_ride__button,
                        icon = Either.Left(Icons.Default.Add),
                        onClick = {
                            coroutineScope.launch {
                                val ride = viewModel.createRide().await()
                                ride?.let { r ->
                                    Log.e("@CreateRideScreen", "Created Ride: $r")
                                    showDialog = true
                                }
                            }
                        },
                        enabled = viewModel.isFormValid()
                    )
                }
            }
        }
    }
    // Display the full-screen confirmation dialog when showDialog is true
    if (showDialog) {
        FullScreenDialogWithoutTitle(
            onDismissRequest = { showDialog = false },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.online__text),
                        color = MaterialTheme.colorScheme.onPrimary,
                        lineHeight = 1.4.em,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.go_to_my_rides__text),
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        lineHeight = 1.4.em,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.fun_foreground),
                        contentDescription = "decorative",
                        modifier = Modifier.size(400.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    CustomButton(title = R.string.my_rides__title,
                        onClick = {
                            showDialog = false
                            onNavigateToMyRides() },
                        enabled = true
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateRideScreen(onNavigateToMyRides = {})
    }
}