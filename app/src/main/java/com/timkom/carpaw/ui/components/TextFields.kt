package com.timkom.carpaw.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = if (label != null) { { Text(text = label) } } else null,
        placeholder = if (placeholder != null) { { Text(text = placeholder) } } else null,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        isError = isError,
        supportingText = if (isError) {
            { ErrorTextField(text = errorText) }
        } else {
            null
        }
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String = ""
) {
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = if (label != null) { { Text(text = label) } } else null,
        placeholder = if (placeholder != null) { { Text(text = placeholder) } } else null,
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        isError = isError,
        supportingText = if (isError) {
            { ErrorTextField(text = errorText) }
        } else {
            null
        }
    )
}

@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorText: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = if (label != null) { { Text(text = label) } } else null,
        placeholder = if (placeholder != null) { { Text(text = placeholder) } } else null,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone
        ),
        isError = isError,
        supportingText = if (isError) {
            { ErrorTextField(text = errorText) }
        } else {
            null
        }
    )
}

@Composable
fun ErrorTextField(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview(showBackground = true)
@Composable
fun EmailTextFieldPreview() {
    CarPawTheme(dynamicColor = false) {
        EmailTextField(value = "", onValueChange = {}, placeholder = "")
    }
}