package com.timkom.carpaw.ui.content

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.data.model.CompanionAnimalItem
import androidx.compose.ui.tooling.preview.Preview
import com.timkom.carpaw.ui.theme.CarPawTheme

enum class AnimalListMode {
    SELECTION, ADD_REMOVE
}


/**
 * A composable function that displays a list of companion animals.
 *
 * @param animals The list of animals to display.
 * @param mode The mode of the list, either selection or add/remove.
 * @param onAddClick The action to perform when the add button is clicked.
 * @param onRemoveClick The action to perform when the remove button is clicked.
 * @param onAnimalSelect The action to perform when an animal is selected or deselected.
 */
@Composable
fun CompanionAnimalList(
    animals: List<CompanionAnimalItem>,
    mode: AnimalListMode,
    onAddClick: (CompanionAnimalItem) -> Unit = {},
    onRemoveClick: (CompanionAnimalItem) -> Unit = {},
    onAnimalSelect: (CompanionAnimalItem, Boolean) -> Unit = { _, _ -> }
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
            items(animals) { animal ->
                when (mode) {
                    AnimalListMode.ADD_REMOVE -> {
                        CompanionAnimalListContentAddRemove(
                            animal = animal,
                            onAddClick = { onAddClick(animal) },
                            onRemoveClick = { onRemoveClick(animal) }
                        )
                    }
                    AnimalListMode.SELECTION -> {
                        CompanionAnimalSelectionContent(
                            animal = animal,
                            onAnimalSelect = { isSelected ->
                                onAnimalSelect(animal, isSelected)
                            }
                        )
                    }
                }
            }
        }
    }
}


/**
 * A composable function that displays a companion animal with add and remove buttons.
 *  Implemented on the "Create Ride" screen
 *
 * @param animal The animal to display.
 * @param onAddClick The action to perform when the add button is clicked.
 * @param onRemoveClick The action to perform when the remove button is clicked.
 */
@Composable
fun CompanionAnimalListContentAddRemove(
    animal: CompanionAnimalItem,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit
) {
    CompanionAnimalListItem(animal) {
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


/**
 * A composable function that displays a companion animal with a selection checkbox.
 * Implemented on the "Create Ride" screen
 *
 * @param animal The animal to display.
 * @param onAnimalSelect The action to perform when the selection state changes.
 */
@Composable
fun CompanionAnimalSelectionContent(
    animal: CompanionAnimalItem,
    onAnimalSelect: (Boolean) -> Unit
) {
    CompanionAnimalListItem(animal) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = animal.isSelected,
                onCheckedChange = onAnimalSelect,
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary,
                    uncheckedColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}


/**
 * A composable function that displays the common UI elements of a companion animal list item.
 * Implemented both in "Create" and "Search" screens but with different actions.
 *
 * @param animal The animal to display.
 * @param actions The composable actions to include in the list item.
 */
@Composable
fun CompanionAnimalListItem(
    animal: CompanionAnimalItem,
    actions: @Composable () -> Unit
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = animal.icon),
            contentDescription = context.resources.getQuantityString(animal.animalName, 1),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = context.resources.getQuantityString(animal.animalName, 1),
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
        actions()
    }
}


/**
 * Preview for CompanionAnimalList in SELECTION mode.
 */
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CompanionAnimalListSelectionPreview() {
    CarPawTheme {
        val animals = mutableStateListOf(*CompanionAnimalItem.entries.toTypedArray())
        CompanionAnimalList(
            animals = animals,
            mode = AnimalListMode.SELECTION,
            onAnimalSelect = { _, _ -> }
        )
    }
}

/**
 * Preview for CompanionAnimalList in ADD_REMOVE mode.
 */
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun CompanionAnimalListAddRemovePreview() {
    CarPawTheme {
        val animals = mutableStateListOf(*CompanionAnimalItem.entries.toTypedArray())
        CompanionAnimalList(
            animals = animals,
            mode = AnimalListMode.ADD_REMOVE,
            onAddClick = {},
            onRemoveClick = {}
        )
    }
}
