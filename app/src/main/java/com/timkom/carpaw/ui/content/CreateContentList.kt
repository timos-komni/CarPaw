package com.timkom.carpaw.ui.content

import androidx.compose.runtime.Composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.Content


enum class CreateContentType : Content.ContentType<CreateContentType> {
    STARTING_POINT,
    DESTINATION
}

@Composable
fun createContentList(): List<Content<CreateContentType>> {
    return listOf(
        Content(0, CreateContentType.STARTING_POINT, R.string.leaving_from__title,
            R.string.search_departure__placeholder, R.string.search_departure__label,
            R.string.pickup_address__placeholder,R.string.pickup_address__label),
        Content(1, CreateContentType.DESTINATION, R.string.travelling_to__title,
            R.string.search_destination__placeholder, R.string.search_destination__label,
            R.string.drop_off_address__placeholder,R.string.drop_off_address__label)
    )
}