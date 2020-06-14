/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.safeShow(
    childFragmentManager: FragmentManager,
    tag: String = this::class.java.simpleName
) {
    if (childFragmentManager.findFragmentByTag(tag) == null) {
        this.show(childFragmentManager, tag)
    }
}