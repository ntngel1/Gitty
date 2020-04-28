/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code

import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry

data class RepositoryCodeState(
    val path: List<String> = emptyList(),
    val fileTree: List<FileTreeEntry>? = null,
    val visibleFileTree: List<FileTreeEntry>? = null,
    val shouldDisplayBackButton: Boolean = false,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)