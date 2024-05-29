package com.timkom.carpaw.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.theme.CarPawTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationBar(
    @StringRes placeholder: Int,
){
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var items = remember {
        mutableStateListOf(
            "Athens",
            "Kavala"
        )
    }
    SearchBar(modifier = Modifier
            .fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
        }, onSearch = {
            items.add(text)
            active = false
            text = ""
        }, active = active,
        onActiveChange = {
            active = it
        }, placeholder = {
            Text(text = stringResource(id = placeholder))
        }, leadingIcon = {
            if(!active){
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            }else{
                Icon(
                    modifier = Modifier.clickable {
                        active = !active
                        text = ""
                    },
                    imageVector =  Icons.Default.ArrowBack,
                    contentDescription = "Search Icon")
            }

        }, trailingIcon = {
            if(active){
                Icon(
                    modifier = Modifier.clickable {
                        text = ""
                    },
                    imageVector =  Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        }
    ) {
        items.forEach{
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
            placeholder = R.string.search_departure__placeholder
        )
    }
}