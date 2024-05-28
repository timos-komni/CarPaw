package com.timkom.carpaw.ui.content

import InfoCard
import InfoCardSpec
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun ListInfoContent(
    modifier: Modifier = Modifier,
    specs: List<InfoCardSpec>,
    navController: NavController,
    @StringRes title: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            lineHeight = 1.43.em,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .offset(
                    x = 0.5.dp,
                    y = 8.dp
                )
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.CenterVertically))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                start = 8.dp,
                end = 8.dp
            )
        ){
            items(specs) { spec ->
                InfoCard(
                    spec = spec,
                    navController = navController
                )
            }
        }
    }

    }



@Preview(showBackground = true)
@Composable
fun ListInfoContentPreview() {
    CarPawTheme(dynamicColor = false) {
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
                    icon = R.drawable.add_location
                ),
                InfoCardSpec(
                    id = 3,
                    info = R.string.info_item__pickup,
                    icon = R.drawable.add_location
                )
            ),
            navController = rememberNavController(),
            title =  R.string.info_card__title1
        )
    }
}
