package com.timkom.carpaw.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
    @StringRes placeholder: Int,
    @StringRes label: Int,
    queryText: String,
    onQueryChange: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onSearch: (String) -> Unit,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier ) {
        Text(
            text = stringResource(id = label),
            lineHeight = 1.33.em,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.height(10.dp))
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp, max = 200.dp),
            query = queryText,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(text = stringResource(id = placeholder))
            },
            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.background),
            tonalElevation = 1.dp,
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(10.dp),
            leadingIcon = {
                if (!active) {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                } else {
                    Icon(
                        modifier = Modifier.clickable {
                            onActiveChange(false)
                            onQueryChange("")
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Search Icon"
                    )
                }
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            onQueryChange("")
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        ) {
            items.forEach {
                Row(
                    modifier = Modifier.padding(all = 10.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        painter = painterResource(R.drawable.history),
                        contentDescription = "History Icon"
                    )
                    Text(text = it)
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchLocationBarPreview() {
    CarPawTheme(dynamicColor = false) {
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
    }
}
