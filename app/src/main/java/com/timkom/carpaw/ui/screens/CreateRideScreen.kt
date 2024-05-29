package com.timkom.carpaw.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.components.cards.ExpandableCard
import com.timkom.carpaw.ui.components.cards.contentList
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun CreateRideScreen(modifier: Modifier = Modifier){
    // TODO (Chloe) always use rememberSaveable to survive configuration changes
    var expandedItem by rememberSaveable {
        // TODO (Chloe) look at suggested-by-linter code changes (do not suppress if unsure)
        mutableIntStateOf(0)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            //.verticalScroll(rememberScrollState())
    )  {
        PageHeading(
            Modifier,
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
            // TODO (Chloe) don't forget indentation
            items(contentList) { content ->
                ExpandableCard(
                    content = content,
                    expanded = expandedItem == content.id,
                    onClickExpanded = { id ->
                        // TODO (Chloe) use single-line if expression for ternary-operator statements
                        expandedItem = if (expandedItem == id) -1 else id
                    })
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