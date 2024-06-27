package com.timkom.carpaw.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Debug
import android.util.Log
import com.timkom.carpaw.BuildConfig
import java.util.Arrays
import kotlin.reflect.KClass

/**
 * Check if the app is in debug mode ([BuildConfig.DEBUG] is `true`).
 * @param checkForDebugger If `true`, also checks if the debugger is connected.
 * @return `true` if the app is debuggable.
 */
@Suppress("unused")
@JvmOverloads
fun isDebug(checkForDebugger: Boolean = false): Boolean {
    return if (!checkForDebugger) {
        BuildConfig.DEBUG
    } else {
        BuildConfig.DEBUG && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())
    }
}

/**
 * Creates a TAG (for use in logging) for the given class (Java class...[Class]). It respects the
 * 23 character limit that existed until Android 7 (API 25). The TAG has the format @[class name] (
 * max 23 characters - to get the class name the [Class.getSimpleName] is called). e.g. the TAG for
 * the MainActivity will be @MainActivity.
 * @param clazz The class to get the TAG for.
 * @param T The type param of the [Class].
 * @return The TAG for the given class.
 */
fun <T> createTAGForClass(clazz: Class<T>): String {
    val className = clazz.simpleName.toCharArray()
    return "@${String(Arrays.copyOfRange(className, 0, className.size.coerceAtMost(22)))}"
}

/**
 * Creates a TAG (for use in logging) for the given class (Kotlin class...[KClass]). It respects the
 * 23 character limit that existed until Android 7 (API 25). The TAG has the format @[class name] (
 * max 23 characters - to get the class name the [Class.getSimpleName] is called). e.g. the TAG for
 * the MainActivity will be @MainActivity.
 * @param clazz The class to get the TAG for.
 * @param T The type param of the [Class].
 * @return The TAG for the given class.
 */
fun <T: Any> createTAGForKClass(clazz: KClass<T>) = createTAGForClass(clazz.java)

/**
 * Restarts the app.
 * @param ctx The (activity) context to use.
 */
@Suppress("unused")
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

/**
 * Restarts the app.
 * @param activity The activity to use.
 */
@Suppress("unused")
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