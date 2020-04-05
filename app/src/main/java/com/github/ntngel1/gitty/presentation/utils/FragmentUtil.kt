/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T> Fragment.argument(key: String) = object : ReadOnlyProperty<Fragment, T> {

    override fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T {
        return arguments?.get(key) as? T
            ?: throw IllegalStateException(
                "Cannot cast argument with key=$key to type ${T::class.java.simpleName}"
            )
    }
}