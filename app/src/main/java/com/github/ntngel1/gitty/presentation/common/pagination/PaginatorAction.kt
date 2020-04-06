/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

sealed class PaginatorAction {

    // User actions
    object Initial : PaginatorAction()
    object Refresh : PaginatorAction()
    object LoadNextPage : PaginatorAction()

    // Internal Paginator actions (shouldn't be passed from user)
    data class PageLoaded<T>(
        val page: PaginatorPage<T>
    ) : PaginatorAction()

    data class Error(
        val error: Throwable
    ) : PaginatorAction()
}