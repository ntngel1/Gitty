/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ntngel1.gitty.presentation.ui.views.EmptyContentStubView
import com.github.ntngel1.gitty.presentation.ui.views.ErrorStubView
import com.github.ntngel1.gitty.presentation.utils.isRefreshingSafe
import com.github.ntngel1.gitty.presentation.utils.visibleOrGone

object Pagination {

    data class State<out T>(
        val isRefreshing: Boolean = false,
        val isLoadingNextPage: Boolean = false,
        val isPageLoadingError: Boolean = false,
        val items: List<T> = emptyList(),
        val nextPageCursor: String? = null,
        val error: Throwable? = null
    )

    // Internal actions
    sealed class Action<out T> {
        object Refresh : Action<Nothing>()
        object LoadNextPage : Action<Nothing>()

        data class PageLoaded<out T>(
            val items: List<T>,
            val nextPageCursor: String?
        ) : Action<T>()

        data class PageError(
            val throwable: Throwable
        ) : Action<Nothing>()

        data class Refreshed<out T>(
            val items: List<T>,
            val nextPageCursor: String?
        ) : Action<T>()

        data class RefreshError(
            val throwable: Throwable
        ) : Action<Nothing>()
    }

    fun <T> reduce(
        state: State<T>,
        action: Action<T>
    ): State<T> = when (action) {
        is Action.PageLoaded -> {
            if (state.isLoadingNextPage) {
                state.copy(
                    isLoadingNextPage = false,
                    items = state.items + action.items,
                    nextPageCursor = action.nextPageCursor
                )
            } else {
                state
            }
        }
        is Action.PageError -> state.copy(
            isLoadingNextPage = false,
            isPageLoadingError = true
        )
        is Action.Refreshed -> State(
            items = action.items,
            nextPageCursor = action.nextPageCursor
        )
        is Action.RefreshError -> state.copy(
            isRefreshing = false,
            error = action.throwable
        )
        is Action.Refresh -> state.copy(
            isRefreshing = true,
            error = null
        )
        is Action.LoadNextPage -> state.copy(
            isLoadingNextPage = true,
            isPageLoadingError = false
        )
    }

    class StateToUiAdapter<T>(
        private val shimmerLayout: View,
        private val swipeRefreshLayout: SwipeRefreshLayout,
        private val errorStubView: ErrorStubView,
        private val emptyContentStubView: EmptyContentStubView,
        private val scrollListener: PaginationScrollListener
    ) {

        fun render(
            state: State<T>,
            onRenderItems: (items: List<T>, isLoadingNextPage: Boolean, isPageLoadingError: Boolean) -> Unit
        ) {
            swipeRefreshLayout.isRefreshingSafe = state.isRefreshing
            swipeRefreshLayout.visibleOrGone(
                isVisible = state.items.isNotEmpty()
            )

            shimmerLayout.visibleOrGone(
                isVisible = state.isRefreshing && state.items.isEmpty()
            )

            emptyContentStubView.visibleOrGone(
                isVisible = state.items.isEmpty() && !state.isRefreshing && state.error == null
            )

            errorStubView.visibleOrGone(
                isVisible = state.error != null && state.items.isEmpty()
            )

            scrollListener.isEnabled = !state.isPageLoadingError

            if (state.items.isNotEmpty()) {
                onRenderItems.invoke(state.items, state.isLoadingNextPage, state.isPageLoadingError)
            }
        }
    }
}