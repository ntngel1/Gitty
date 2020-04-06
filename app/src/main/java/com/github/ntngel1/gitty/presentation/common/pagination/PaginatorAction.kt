/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

sealed class PaginatorAction<out T> {

    // User actions
    object Initial : PaginatorAction<Nothing>()
    object Refresh : PaginatorAction<Nothing>()
    object LoadNextPage : PaginatorAction<Nothing>()

    // Internal Paginator actions (shouldn't be passed from user)
    data class PageLoaded<out T>(
        val page: PaginatorPage<out T>
    ) : PaginatorAction<T>()

    data class Error(
        val error: Throwable
    ) : PaginatorAction<Nothing>()
}