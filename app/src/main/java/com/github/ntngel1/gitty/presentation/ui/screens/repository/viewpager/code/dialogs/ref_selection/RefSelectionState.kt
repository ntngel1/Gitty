/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection

data class RefSelectionState(
    val isInitialLoading: Boolean = true,
    val isLoading: Boolean = false,
    val nextPageCursor: String? = null,
    val branches: List<String> = emptyList(),
    val selectedBranch: String? = null,
    val isLoadingError: Boolean = false,
    val filter: Int = FILTER_BRANCHES
) {
    companion object {
        const val FILTER_BRANCHES = 1
        const val FILTER_TAGS = 2
    }
}