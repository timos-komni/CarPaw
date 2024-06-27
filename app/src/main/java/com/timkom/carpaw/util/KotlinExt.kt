package com.timkom.carpaw.util

import kotlin.reflect.KClass

/**
 * Calls the specified function block. If an exception is thrown and is one of the specified
 * [exceptions] then the [onCatch] blocked is invoked, else the exception is re-thrown.
 * @param R The return type of the function block.
 * @param onCatch The function to invoke if an exception is thrown and is one of the specified.
 * @param exceptions The exceptions to handle.
 * @return The result of the function block.
 */
inline fun <R> (() -> R).multiCatch(onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return try {
        this()
    } catch (e: Throwable) {
        if (e::class in exceptions) onCatch(e) else throw e
    }
}

/**
 * Calls the specified suspend function block. If an exception is thrown and is one of the specified
 * [exceptions] then the [onCatch] blocked is invoked, else the exception is re-thrown.
 * @param R The return type of the suspend function block.
 * @param onCatch The function to invoke if an exception is thrown and is one of the specified.
 * @param exceptions The exceptions to handle.
 * @return The result of the suspend function block.
 */
suspend inline fun <R> (suspend () -> R).multiCatch(onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return try {
        this()
    } catch (e: Throwable) {
        if (e::class in exceptions) onCatch(e) else throw e
    }
}

/**
 * Invokes the [runThis] function. If an exception is thrown and is one of the specified [exceptions]
 * then the [onCatch] blocked is invoked, else the exception is re-thrown.
 * @param R The return type of the function.
 * @param onCatch The function to invoke if an exception is thrown and is one of the specified.
 * @param exceptions The exceptions to handle.
 * @return The result of the function.
 */
inline fun <R> tryMultiCatch(noinline runThis: () -> R, onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return runThis.multiCatch(
        onCatch,
        *exceptions
    )
}

/**
 * Invokes the [runThis] suspend function. If an exception is thrown and is one of the specified
 * [exceptions] then the [onCatch] blocked is invoked, else the exception is re-thrown.
 * @param R The return type of the suspend function.
 * @param onCatch The function to invoke if an exception is thrown and is one of the specified.
 * @param exceptions The exceptions to handle.
 * @return The result of the suspend function.
 */
@Suppress("unused")
suspend inline fun <R> tryMultiCatchSuspend(noinline runThis: suspend () -> R, onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return runThis.multiCatch(
        onCatch,
        *exceptions
    )
}

/**
 * Checks if any of the [items] contains the [check] string.
 * @param check The string to check for.
 * @param items The items to check in.
 * @param ignoreCase Whether to ignore case when checking.
 * @return `true` if any of the [items] contains the [check] string, `false` otherwise.
 */
@Suppress("unused")
fun containsInAny(check: CharSequence, vararg items: CharSequence, ignoreCase: Boolean = false): Boolean {
    var result = false
    for (item in items) {
        if (item.contains(check, ignoreCase)) {
            result = true
        }
    }
    return result
}

/**
 * Checks if any of the [items] is empty. If [reverseCheck] is `true`, then it checks if any of the
 * [items] is not empty.
 * @param items The items to check.
 * @param reverseCheck Whether to check if any of the [items] is not empty (reverse check).
 * @return `true` if any of the [items] is empty (or not empty if [reverseCheck] is `true`), `false`
 * otherwise.
 */
@Suppress("unused")
fun checkIfAnyEmpty(vararg items: CharSequence, reverseCheck: Boolean = false): Boolean {
    var result = false
    val method = if (!reverseCheck) CharSequence::isEmpty else CharSequence::isNotEmpty
    for (item in items) {
        if (method.invoke(item)) {
            result = true
            break // no need to check others
        }
    }
    return result
}

/**
 * Checks if any of the [items] is blank. If [reverseCheck] is `true`, then it checks if any of the
 * [items] is not blank.
 * @param items The items to check.
 * @param reverseCheck Whether to check if any of the [items] is not blank (reverse check).
 * @return `true` if any of the [items] is blank (or not blank if [reverseCheck] is `true`), `false`
 * otherwise.
 */
fun checkIfAnyBlank(vararg items: CharSequence, reverseCheck: Boolean = false): Boolean {
    var result = false
    val method = if (!reverseCheck) CharSequence::isBlank else CharSequence::isNotBlank
    for (item in items) {
        if (method.invoke(item)) {
            result = true
            break // no need to check others
        }
    }
    return result
}