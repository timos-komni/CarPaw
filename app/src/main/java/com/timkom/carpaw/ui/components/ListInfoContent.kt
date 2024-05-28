package com.timkom.carpaw.ui.components

import InfoCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.data.model.InfoItem

@Composable
fun ListInfoContent(
    modifier: Modifier = Modifier,
    infos: List<InfoItem>,
    navController: NavController
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp
            )
    ){
        items(infos) { info->
            InfoCard(
                infoItem = info,
                navController = navController)
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun ListInfoContentPreview() {
    CarPawTheme(dynamicColor = false) {
        ListInfoContent(
            infos = listOf(
                InfoItem(
                    id = 1,
                    info = R.string.info_item__create_ride,
                    icon = R.drawable.add_location
                ),
                InfoItem(
                    id = 2,
                    info = R.string.info_item__create_ride,
                    icon = R.drawable.add_location
                ),
                InfoItem(
                    id = 3,
                    info = R.string.info_item__create_ride,
                    icon = R.drawable.add_location
                )
            ),
            navController = rememberNavController(),
        )
    }
}
