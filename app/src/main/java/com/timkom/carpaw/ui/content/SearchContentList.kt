package com.timkom.carpaw.ui.content

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.timkom.carpaw.R
import com.timkom.carpaw.ui.components.cards.Content

@Composable
fun searchContentList(): List<Content> {
    return listOf(
        Content(0, R.string.leaving_from__title, R.string.search_departure__placeholder, R.string.search_departure__label),
        Content(1, R.string.travelling_to__title, R.string.search_destination__placeholder, R.string.search_destination__label)
    )
}