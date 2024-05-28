package com.timkom.carpaw.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.card.ExpandableCard
import com.timkom.carpaw.ui.card.contentList
import com.timkom.carpaw.ui.components.PageHeading
import com.timkom.carpaw.ui.theme.CarPawTheme

@Composable
fun CreateRideScreen(modifier: Modifier = Modifier){
    var expandedItem by remember {
        mutableStateOf(-1)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    )  {
        PageHeading(
            Modifier,
            title = R.string.create_ride__title
        )
        LazyColumn {
            items(contentList){content->
                ExpandableCard(
                    content = content,
                    expanded = expandedItem == content.id,
                    onClickExpanded = {id->
                        expandedItem = if(expandedItem == id){
                            -1
                        }else{
                            id
                        }


                    })

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateRideScreenPreview() {
    CarPawTheme(dynamicColor = false) {
        CreateRideScreen()
    }
}