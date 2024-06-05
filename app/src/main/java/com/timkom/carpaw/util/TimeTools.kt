package com.timkom.carpaw.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date

/**
 * TODO (Chloe->informative) You don't need an "empty" `class` with a companion for "static" functions,
 * you can just declare an `object` or even better make them top-level functions (look at GenericUtils file)
 */
object TimeTools {
    /**
     * TODO (Chloe->informative) Never use m<NAME> for parameters. m<NAME> stands for member (class member).
     * Use either nothing, or pm<NAME> (for parameters of class methods). Also, a common name for
     * Context is "ctx".
     * Also, keep in mind that Android apps don't have one [Context] (e.g. app's Context ([Context.getApplicationContext])
     * and activity's Context ([android.app.Activity] -> can be casted)).
     */
    fun openLink(context: Context, url: String) {
        val openURL = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(openURL)
    }

    /**
     * TODO (Chloe) Why "[...]Time"? "Date" or even "DateString" is better. Also, maybe don't call it Long,
     * not every [Long] should be used -> find a name based on "milliseconds" or "time".
     */
    fun convertLongToTime(time: Long) : String {
        val date = Date(time)
        val format = SimpleDateFormat("dd/MM/yyyy")
        return format.format(date)
    }
}