/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter

sealed class Callback<R> {

    abstract val listener: () -> R

    data class Hashable<R>(
        override val listener: () -> R
    ) : Callback<R>() {

        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }

    data class NonHashable<R>(
        override val listener: () -> R
    ) : Callback<R>() {

        override fun equals(other: Any?): Boolean {
            return other !is Hashable<*>
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }
}

sealed class Callback1<T1, R> {

    abstract val listener: (T1) -> R

    data class Hashable<T1, R>(
        override val listener: (T1) -> R
    ) : Callback1<T1, R>() {

        override fun equals(other: Any?): Boolean {
            return false
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }

    data class NonHashable<T1, R>(
        override val listener: (T1) -> R
    ) : Callback1<T1, R>() {

        override fun equals(other: Any?): Boolean {
            return other !is Hashable<*, *>
        }

        override fun hashCode(): Int {
            return listener.hashCode()
        }
    }
}

fun <R> callback(hashable: Boolean = false, listener: () -> R) =
    if (hashable) {
        Callback.Hashable(
            listener
        )
    } else {
        Callback.NonHashable(
            listener
        )
    }

fun <T1, R> callback1(hashable: Boolean = false, listener: (T1) -> R) =
    if (hashable) {
        Callback1.Hashable(
            listener
        )
    } else {
        Callback1.NonHashable(
            listener
        )
    }