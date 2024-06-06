package com.timkom.carpaw.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date

fun openLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    context.startActivity(intent)
}

fun convertMillisecondsToDate(milliseconds: Long): String {
    val date = Date(milliseconds)
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(date)
}
