package com.timkom.carpaw.ui.screens.createRide

import CreateRideScreenContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.cards.ExpandableCard
import com.timkom.carpaw.ui.content.createContentList
import com.timkom.carpaw.ui.theme.CarPawTheme
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun CreateRideScreen(
    viewModel: CreateRideViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val contentList = createContentList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PageHeading(
            modifier = Modifier,
            title = R.string.create_ride__title
        )
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 16.dp,
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            items(contentList) { content ->
                ExpandableCard(
                    title = content.title,
                    expanded = viewModel.expandedItem.value == content.id,
                    onClickExpanded = { viewModel.onItemClick(content.id) },
                    content = {
                        CreateRideScreenContent(
                            location1Placeholder = content.locationPlaceholder,
                            location1Label = content.locationLabel,
                            location2Placeholder = content.addressPlaceholder,
                            location2Label = content.addressLabel
                        )
                    }
                )
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateRideScreen()
    }
}