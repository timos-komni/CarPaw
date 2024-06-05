package com.timkom.carpaw.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.CompanionAnimalItem
import com.timkom.carpaw.ui.screens.searchRide.SearchRideViewModel
import com.timkom.carpaw.ui.theme.CarPawTheme


@Composable
fun CompanionAnimalList(
    viewModel: SearchRideViewModel = viewModel(),

) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(380.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.animals) { animal ->
                CompanionAnimalListContentAddRemove(
                    animal = animal,
                    onAddClick = { viewModel.addAnimal(animal) },
                    onRemoveClick = { viewModel.removeAnimal(animal) }
                )
            }
        }
    }
}



@Composable
fun CompanionAnimalListContentAddRemove(
    animal: CompanionAnimalItem,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = animal.icon),
            contentDescription = animal.name,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = animal.name,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 14.sp
            )
            if (animal.description.isNotEmpty()) {
                Text(
                    text = animal.description,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 12.sp
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.14f)
                ),
                onClick = onRemoveClick,
                enabled = animal.count > 0 // Disable button when count is zero
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.remove_icon),
                    contentDescription = "Remove"
                )
            }
            Text(
                text = animal.count.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = FontFamily(Font(R.font.outfit_medium)),
                fontSize = 14.sp
            )
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    disabledContentColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                onClick = onAddClick
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CompanionAnimalListPreview() {
    CarPawTheme(dynamicColor = false){
        CompanionAnimalList()
    }
}
