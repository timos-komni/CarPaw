package com.timkom.carpaw.util

/**
 * Represents a type that can be either *[A]* or *[B]*.
 * @param A The value type of the left type.
 * @param B The value type of the right type.
 */
sealed class Either<out A, out B> {
    /**
     * The left type.
     * @param value The value of the left type.
     * @param A The value type.
     */
    class Left<A>(val value: A) : Either<A, Nothing>()

    /**
     * The right type.
     * @param value The value of the right type.
     * @param B The value type.
     */
    class Right<B>(val value: B) : Either<Nothing, B>()
}