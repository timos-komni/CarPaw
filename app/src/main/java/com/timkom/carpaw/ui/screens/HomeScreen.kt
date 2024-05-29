package com.timkom.carpaw.ui.screens

import InfoCardSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.HeroCard
import com.timkom.carpaw.ui.components.buttons.ElevatedIconButton
import com.timkom.carpaw.ui.content.ListInfoContent
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.Either

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ){
        HeroCard()
        ListInfoContent(
            specs = listOf(
                InfoCardSpec(
                    id = 1,
                    info = R.string.info_item__create_ride,
                    icon = R.drawable.route
                ),
                InfoCardSpec(
                    id = 2,
                    info = R.string.info_item__answer_questions,
                    icon = R.drawable.chat
                ),
                InfoCardSpec(
                    id = 3,
                    info = R.string.info_item__pickup,
                    icon = R.drawable.directions_car
                )
            ),
            navController = rememberNavController(),
            title = R.string.info_card__title1
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            ElevatedIconButton(
                title = R.string.create_ride__title,
                icon = Either.Left(Icons.Default.Add)
            ) {
                // TODO
            }
        }
        ListInfoContent(
            specs = listOf(
                InfoCardSpec(
                    id = 1,
                    info = R.string.info_item__search_ride,
                    icon = R.drawable.route
                ),
                InfoCardSpec(
                    id = 2,
                    info = R.string.info_item__ask_questions,
                    icon = R.drawable.chat
                ),
                InfoCardSpec(
                    id = 3,
                    info = R.string.info_item__journey,
                    icon = R.drawable.directions_car
                )
            ),
            navController = rememberNavController(),
            title = R.string.info_card__title2
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ){
            ElevatedIconButton(
                title = R.string.search_ride__title,
                icon = Either.Right(R.drawable.search)
            ) {
                // TODO
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        HomeScreen()
    }
}
