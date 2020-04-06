/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

sealed class PaginatorState {
    object EmptyLoading : PaginatorState()
    object EmptyContent : PaginatorState()

    data class EmptyError(
        val throwable: Throwable
    ) : PaginatorState()

    data class Data<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState()

    data class FullData<out T>(
        val items: List<T>
    ) : PaginatorState()

    data class Refreshing<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState()

    data class RefreshingWithFullData<out T>(
        val items: List<T>
    ) : PaginatorState()

    data class LoadingNextPage<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState()

    data class LoadingNextPageWithRefreshing<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState()
}