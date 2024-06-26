package com.timkom.carpaw.ui.components.cards

import androidx.annotation.StringRes
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.SearchLocationBar
import com.timkom.carpaw.ui.content.ExpandableContent
import com.timkom.carpaw.ui.theme.CarPawTheme

data class Content<T : Enum<T>>(
    val id: Int,
    val type: ContentType<T>,
    @StringRes val title: Int,
    @StringRes val locationPlaceholder: Int,
    @StringRes val locationLabel: Int,
    @StringRes val addressPlaceholder: Int,
    @StringRes val addressLabel: Int,
) {
    interface ContentType<T : Enum<T>>
}


@Composable
fun ExpandableCard(
    title: Int,
    expanded: Boolean,
    selectedInfo: String = "",
    onClickExpanded: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(targetState = expanded, label = "trans")
    val iconRotationDeg by transition.animateFloat(label = "icon change") { state ->
        if (state) 0f else 180f
    }
    val color by transition.animateColor(label = "color change") { state ->
        if (state) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceContainerHighest
        }
    }

    Card(
        colors = CardDefaults.cardColors(color),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier.clickable { onClickExpanded() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = stringResource(id = title),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    lineHeight = 1.25.em,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.outfit_bold)),
                    modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Drop-Up Arrow",
                    modifier = Modifier
                        .rotate(iconRotationDeg)
                        .align(Alignment.CenterVertically)
                )
            }
            if (!expanded && selectedInfo.isNotEmpty()) {
                Text(
                    text = selectedInfo,
                    fontFamily = FontFamily(Font(R.font.outfit_medium)),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            ExpandableContent(
                isExpanded = expanded,
                content = content
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExpandableCardPreview() {
    CarPawTheme {
        ExpandableCard(
            title = R.string.create_ride__title,
            expanded = true,
            onClickExpanded = {},
            content = {
                Column {
                    SearchLocationBar(
                        placeholder = R.string.search_departure__placeholder,
                        label = R.string.search_departure__label,
                        queryText = "",
                        onQueryChange = {},
                        active = false,
                        onActiveChange = {},
                        onSearch = {},
                        items = listOf("Athens", "Kavala")
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SearchLocationBar(
                        placeholder = R.string.pickup_address__placeholder,
                        label = R.string.pickup_address__label,
                        queryText = "",
                        onQueryChange = {},
                        active = false,
                        onActiveChange = {},
                        onSearch = {},
                        items = listOf("Athens", "Kavala")
                    )
                }
            }
        )
    }
}