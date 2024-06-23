package com.timkom.carpaw.util

import kotlin.reflect.KClass

inline fun <R> (() -> R).multiCatch(onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return try {
        this()
    } catch (e: Throwable) {
        if (e::class in exceptions) onCatch(e) else throw e
    }
}

suspend inline fun <R> (suspend () -> R).multiCatch(onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return try {
        this()
    } catch (e: Throwable) {
        if (e::class in exceptions) onCatch(e) else throw e
    }
}

inline fun <R> tryMultiCatch(noinline runThis: () -> R, onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return runThis.multiCatch(
        onCatch,
        *exceptions
    )
}

suspend inline fun <R> tryMultiCatchSuspend(noinline runThis: suspend () -> R, onCatch: (Throwable) -> R, vararg exceptions: KClass<out Throwable>): R {
    return runThis.multiCatch(
        onCatch,
        *exceptions
    )
}

fun containsInAny(check: CharSequence, vararg items: CharSequence, ignoreCase: Boolean = false): Boolean {
    var result = false
    for (item in items) {
        if (item.contains(check, ignoreCase)) {
            result = true
        }
    }
    return result
}

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