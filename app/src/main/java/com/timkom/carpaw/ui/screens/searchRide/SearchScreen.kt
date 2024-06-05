package com.timkom.carpaw.ui.screens.searchRide

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
import com.timkom.carpaw.ui.content.DatePickerContent
import com.timkom.carpaw.ui.content.SearchContentType
import com.timkom.carpaw.util.Either

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchRideViewModel = viewModel()

) {
    val contentList = searchContentList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PageHeading(
            modifier = Modifier,
            title = R.string.search_ride__title
        )
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
                    onClickExpanded = { viewModel.onItemClick(3) },
                    content = {
                        CompanionAnimalList(viewModel = viewModel)

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
                        icon = Either.Left(Icons.Default.Search)
                    ) {
                        // TODO
                    }
                }

            }



        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        SearchScreen()
    }
}