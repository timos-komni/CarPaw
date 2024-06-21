package com.timkom.carpaw.ui.content

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme
import com.timkom.carpaw.util.convertMillisecondsToDate
import kotlinx.datetime.Clock
import kotlinx.datetime.*
import java.time.ZoneId


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerContent(
    selectedDate: MutableState<String>,
    @StringRes label: Int,
    isDialogOpen: MutableState<Boolean>,
    setDate: (String) -> Unit,
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
                    imageVector = Icons.Default.DateRange,
                    contentDescription = stringResource(R.string.decorative_icon),
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = selectedDate.value.ifEmpty { stringResource(R.string.select_date) },
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    fontSize = 16.sp
                )
            }
        }
    }
    if (isDialogOpen.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }

        DatePickerDialog(
            onDismissRequest = {
                closeDialog()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                        var date = "Select Date"
                        if (datePickerState.selectedDateMillis != null) {
                            date = convertMillisecondsToDate(datePickerState.selectedDateMillis!!)
                        }
                        setDate(date)
                    },
                    enabled = confirmEnabled.value
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
        ) {
            //val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            DatePicker(
                state = datePickerState
            )

        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DatePickerContentPreview() {
    CarPawTheme(dynamicColor = false) {
        DatePickerContent(
            selectedDate = mutableStateOf("Select Date"),
            label = R.string.select_date__label,
            isDialogOpen = mutableStateOf(false),
            setDate = {},
            closeDialog = {}
        )
    }
}
