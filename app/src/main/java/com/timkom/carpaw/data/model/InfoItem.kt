package com.timkom.carpaw.data.model

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class InfoItem(
    val id: Int,
    @StringRes val info: Int,
    @DrawableRes val icon: Int
)
