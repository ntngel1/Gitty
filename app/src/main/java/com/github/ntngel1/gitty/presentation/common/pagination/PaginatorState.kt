/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

sealed class PaginatorState<out T> {
    object EmptyLoading : PaginatorState<Nothing>()
    object EmptyContent : PaginatorState<Nothing>()

    data class EmptyError(
        val throwable: Throwable
    ) : PaginatorState<Nothing>()

    data class Data<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState<T>()

    data class FullData<out T>(
        val items: List<T>
    ) : PaginatorState<T>()

    data class Refreshing<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState<T>()

    data class RefreshingWithFullData<out T>(
        val items: List<T>
    ) : PaginatorState<T>()

    data class LoadingNextPage<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState<T>()

    data class LoadingNextPageWithRefreshing<out T>(
        val items: List<T>,
        val nextPageCursor: String
    ) : PaginatorState<T>()
}