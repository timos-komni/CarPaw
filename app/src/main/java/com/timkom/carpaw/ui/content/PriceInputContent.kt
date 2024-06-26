package com.timkom.carpaw.ui.content

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

/**
 * A composable function that displays a price input on CreateRide Screen.
 *
 * @param price The price that the user gives.
 * @param isDialogOpen Checks if the input dialog is open or closed.
 * @param  setPrice The action to perform when the user gives a price.
 * @param closeDialog The action to perform when the close button is clicked.
 */
@Composable
fun PriceInputContent(
    price: MutableState<String>,
    @StringRes label: Int,
    isDialogOpen: MutableState<Boolean>,
    setPrice: (String) -> Unit,
    closeDialog: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = label),
            lineHeight = 1.33.em,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.outfit_regular)),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            elevation = ButtonDefaults.buttonElevation(1.dp),
            shape = RoundedCornerShape(14.dp),
            onClick = {
                isDialogOpen.value = true
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource( id = R.drawable.euro_symbol),
                    contentDescription = stringResource(R.string.decorative_icon),
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = price.value.ifEmpty { stringResource(R.string.insert_amount) },
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 16.sp
                )
            }
        }
    }
    if (isDialogOpen.value) {
        val isPriceValid = price.value.isNotEmpty()
        AlertDialog(
            onDismissRequest = {
                closeDialog()
            },
            title = {
                Text(text = stringResource(R.string.insert_amount))
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = price.value,
                        onValueChange = setPrice,
                        label = { Text(stringResource(R.string.amount)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = !isPriceValid && price.value.isNotEmpty()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                    },
                    enabled = isPriceValid
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                    }
                ) {
                    Text("Cancel")
                }
            }

        )
    }
}

/**
 * Preview for PriceInputContent.
 */
@Preview(showBackground = true)
@Composable
fun PriceInputContentPreview() {
    CarPawTheme(dynamicColor = false) {
        PriceInputContent(
            price= mutableStateOf(""),
            label = R.string.price__label,
            isDialogOpen = mutableStateOf(false),
            setPrice = {},
            closeDialog = {}
        )
    }
}