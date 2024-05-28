package com.timkom.carpaw.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.ui.theme.backgroundDark


@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    @DrawableRes icon: Int
) {
    AssistChip(
        onClick = {  },
        //colors = ChipColors(MaterialTheme.colorScheme.primaryContainer),
        label = {
            Text(
                text = stringResource(id = title),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                lineHeight = 1.33.em,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.decorative_icon),
                Modifier.requiredSize(size = 22.dp)
            )

        },
        shape = RoundedCornerShape(12.dp),
        border = AssistChipDefaults.assistChipBorder(enabled = false),
        elevation = AssistChipDefaults.assistChipElevation(elevation = 1.dp),
        colors = AssistChipDefaults.assistChipColors(containerColor= MaterialTheme.colorScheme.primaryContainer),
        //FilterChipDefaults.filterChipColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
private fun IconButtonPreview() {
    CarPawTheme(dynamicColor = false){
        IconButton(
            title = R.string.create_ride__title,
            icon =  R.drawable.add_location


        )
    }

}