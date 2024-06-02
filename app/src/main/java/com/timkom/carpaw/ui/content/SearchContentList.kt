package com.timkom.carpaw.ui.content

import androidx.compose.runtime.Composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.Content

enum class SearchContentType : Content.ContentType<SearchContentType> {
    STARTING_POINT,
    DESTINATION
}

@Composable
fun searchContentList(): List<Content<SearchContentType>> {
    return listOf(
        Content(0, SearchContentType.STARTING_POINT, R.string.leaving_from__title,
            R.string.search_departure__placeholder, R.string.search_departure__label,
            0,0),
        Content(1, SearchContentType.DESTINATION, R.string.travelling_to__title,
            R.string.search_destination__placeholder, R.string.search_destination__label,
            0,0)
    )
}