package com.timkom.carpaw.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.cards.ExpandableCard
import com.timkom.carpaw.ui.content.SearchScreenContent
import com.timkom.carpaw.ui.content.searchContentList
import com.timkom.carpaw.ui.theme.CarPawTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.ui.content.CompanionAnimalList
import com.timkom.carpaw.ui.components.buttons.ElevatedIconButton
import com.timkom.carpaw.ui.content.AnimalListMode
import com.timkom.carpaw.ui.content.DatePickerContent
import com.timkom.carpaw.ui.content.SearchContentType
import com.timkom.carpaw.ui.viewmodels.SearchRideViewModel
import com.timkom.carpaw.util.Either
import java.lang.ref.WeakReference

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchRideViewModel = viewModel(),
    onSearchClick: () -> Unit
) {
    val contentList = searchContentList()
    val context = LocalContext.current

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
                        0 -> viewModel.startSearchText.value
                        1 -> viewModel.destinationSearchText.value
                        else -> ""
                    },
                    onClickExpanded = { viewModel.onItemClick(content.id) },
                    content = {
                        SearchScreenContent(
                            content.type as SearchContentType,
                            placeholder = content.locationPlaceholder,
                            label = content.locationLabel
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
            item {
                ExpandableCard(
                    title = R.string.date__title,
                    expanded = viewModel.expandedItem.intValue == 2,
                    selectedInfo = viewModel.selectedDate.value,
                    onClickExpanded = { viewModel.onItemClick(2) },
                    content = {
                        DatePickerContent(
                            selectedDate = viewModel.selectedDate,
                            label = R.string.select_date_for_animal__label,
                            isDialogOpen = viewModel.isDialogOpen,
                            setDate = { date -> viewModel.setDate(date) },
                            closeDialog = { viewModel.closeDialog() }
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
                ExpandableCard(
                    title = R.string.passengers__title,
                    expanded = viewModel.expandedItem.intValue == 3,
                    selectedInfo = viewModel.selectedAnimalsSummary.value,
                    onClickExpanded = { viewModel.onItemClick(3) },
                    content = {
                        CompanionAnimalList(
                            animals = viewModel.animals,
                            mode = AnimalListMode.ADD_REMOVE,
                            onAddClick = { animal -> viewModel.addAnimal(WeakReference(context), animal) },
                            onRemoveClick = { animal -> viewModel.removeAnimal(WeakReference(context), animal) }
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
                        title = R.string.search_ride__title,
                        icon = Either.Left(Icons.Default.Search),
                        onClick = onSearchClick,
                        enabled = viewModel.isFormValid()
                    )

                }

            }



        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        SearchScreen(onSearchClick ={})
    }
}