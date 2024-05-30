package com.timkom.carpaw.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timkom.carpaw.ui.screens.createRide.CreateRideViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
    @StringRes placeholder: Int,
    @StringRes label: Int,
    modifier: Modifier = Modifier,
    viewModel: CreateRideViewModel = viewModel()
){
    Text(
        text = stringResource(id = label),
        lineHeight = 1.33.em,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .requiredWidth(width = 283.dp)
            .wrapContentHeight(align = Alignment.CenterVertically))
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = viewModel.searchText.value,
        onQueryChange = {
            viewModel.onQueryChange(it)
        }, onSearch = {
            viewModel.onSearch()
        }, active = viewModel.searchActive.value,
        onActiveChange = {
            viewModel.onActiveChange(it)
        }, placeholder = {
            Text(text = stringResource(id = placeholder))
        }, leadingIcon = {
            if(!viewModel.searchActive.value){
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }else{
                Icon(
                    modifier = Modifier.clickable {
                        viewModel.onActiveChange(false)
                        viewModel.onQueryChange("")
                    },
                    imageVector =  Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Search Icon")
            }

        }, trailingIcon = {
            if(viewModel.searchActive.value){
                Icon(
                    modifier = Modifier.clickable {
                        viewModel.onQueryChange("")
                    },
                    imageVector =  Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    ) {
        viewModel.items.forEach{
            Row(
                modifier = Modifier
                    .padding(all = 14.dp)
            ){
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    painter = painterResource(R.drawable.history), contentDescription = "Close Icon"
                )
                Text(text = it)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SearchLocationBarPreview() {
    CarPawTheme(dynamicColor = false) {
        SearchLocationBar(
            placeholder = R.string.search_departure__placeholder,
            label = R.string.search_departure__label
        )
    }
}