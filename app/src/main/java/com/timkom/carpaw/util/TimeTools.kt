package com.timkom.carpaw.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

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

fun formatDateTime(dateTime: String): String {
    val possibleFormats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd"
    )

    for (format in possibleFormats) {
        try {
            val parser = SimpleDateFormat(format, Locale.getDefault())
            val formatter = SimpleDateFormat("EEE, dd MMM, yyyy - hh:mm a", Locale.getDefault())
            return parser.parse(dateTime)?.let {
                formatter.format(it)
            } ?: dateTime
        } catch (e: ParseException) {
            // Continue to the next format
        }
    }

    return dateTime // Return the original string if no formats matched
}

fun formatDateString(dateString: String, from: String, to: String, locale: Locale = Locale.ROOT): String? {
    return tryMultiCatch(
        runThis = {
            val parsedDate = SimpleDateFormat(from, locale).parse(dateString)
            parsedDate?.let {
                SimpleDateFormat(to, locale).format(parsedDate)
            }
        },
        onCatch = {
            Log.e("@formatDateString", "Could not format \"$dateString\" from \"$from\" to \"$to\": ${it.message}")
            it.printStackTrace()
            null
        },
        NullPointerException::class,
        IllegalStateException::class,
        ParseException::class
    )
}