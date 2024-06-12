package com.timkom.carpaw.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Debug
import android.util.Log
import androidx.annotation.PluralsRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.timkom.carpaw.BuildConfig
import java.util.Arrays
import kotlin.reflect.KClass

@JvmOverloads
fun isDebug(checkForDebugger: Boolean = false): Boolean {
    return if (!checkForDebugger) {
        BuildConfig.DEBUG
    } else {
        BuildConfig.DEBUG && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())
    }
}

fun <T> createTAGForClass(clazz: Class<T>): String {
    val className = clazz.simpleName.toCharArray()
    return "@${Arrays.copyOfRange(className, 0, className.size.coerceAtMost(22))}"
}

fun <T: Any> createTAGForKClass(clazz: KClass<T>) = createTAGForClass(clazz.java)

fun triggerRebirth(ctx: Context) {
    try {
        val pm = ctx.packageManager
        val intent = pm.getLaunchIntentForPackage(ctx.packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        ctx.startActivity(mainIntent)
        (ctx as Activity).finishAffinity()
    } catch (e: Exception) {
        Log.e("GenericUtils", "App could not be restarted!")
        e.printStackTrace()
    }
}

fun triggerRebirth(activity: Activity) {
    try {
        val pm = activity.packageManager
        val intent = pm.getLaunchIntentForPackage(activity.packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        activity.startActivity(mainIntent)
        activity.finishAffinity()
    } catch (e: Exception) {
        Log.e("GenericUtils", "App could not be restarted!")
        e.printStackTrace()
    }
}

@Composable
fun getPluralString(@PluralsRes id: Int, quantity: Int): String {
    val context = LocalContext.current
    return context.resources.getQuantityString(id, quantity, quantity)
}
