/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ntngel1.gitty.presentation.ui.views.EmptyContentStubView
import com.github.ntngel1.gitty.presentation.ui.views.ErrorStubView
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.isRefreshingSafe
import com.github.ntngel1.gitty.presentation.utils.visible

class PaginatorStateAdapter<T>(
    private val swipeRefreshLayout: SwipeRefreshLayout,
    private val shimmerLayout: View,
    private val emptyContentStubView: EmptyContentStubView,
    private val errorStubView: ErrorStubView,
    private val onShowItems: (items: List<T>, isLoadingNextPage: Boolean) -> Unit
) {

    fun render(state: PaginatorState<T>) = when (state) {
        is PaginatorState.EmptyLoading -> {
            emptyContentStubView.gone()
            shimmerLayout.visible()
            errorStubView.gone()
        }
        is PaginatorState.EmptyContent -> {
            emptyContentStubView.visible()
            shimmerLayout.gone()
            errorStubView.gone()

            swipeRefreshLayout.isRefreshingSafe = false
            swipeRefreshLayout.gone()
        }
        is PaginatorState.EmptyError -> {
            errorStubView.visible()
            emptyContentStubView.gone()
            shimmerLayout.gone()

            swipeRefreshLayout.isRefreshingSafe = false
            swipeRefreshLayout.gone()
        }
        is PaginatorState.Refreshing -> {
            swipeRefreshLayout.isRefreshingSafe = true
            onShowItems.invoke(state.items, false)
        }
        is PaginatorState.RefreshingWithFullData -> {
            swipeRefreshLayout.isRefreshingSafe = true
            onShowItems.invoke(state.items, false)
        }
        is PaginatorState.FullData -> {
            emptyContentStubView.gone()
            shimmerLayout.gone()
            errorStubView.gone()

            swipeRefreshLayout.visible()
            swipeRefreshLayout.isRefreshingSafe = false
            onShowItems.invoke(state.items, false)
        }
        is PaginatorState.Data -> {
            emptyContentStubView.gone()
            shimmerLayout.gone()
            errorStubView.gone()

            swipeRefreshLayout.visible()
            swipeRefreshLayout.isRefreshingSafe = false
            onShowItems.invoke(state.items, false)
        }
        is PaginatorState.LoadingNextPage -> {
            onShowItems.invoke(state.items, true)
        }
        is PaginatorState.LoadingNextPageWithRefreshing -> {
            swipeRefreshLayout.isRefreshingSafe = true
            onShowItems.invoke(state.items, true)
        }
    }
}