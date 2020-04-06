/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

data class PaginatorPage<T>(
    val hasNextPage: Boolean,
    val cursor: String?,
    val isInitialPage: Boolean,
    val items: List<T>
)