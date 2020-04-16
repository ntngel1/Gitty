/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.single_load

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ntngel1.gitty.presentation.ui.views.ErrorStubView
import com.github.ntngel1.gitty.presentation.utils.isRefreshingSafe
import com.github.ntngel1.gitty.presentation.utils.visibleOrGone

object SingleLoad {

    data class State<out T>(
        val isRefreshing: Boolean = false,
        val data: T? = null,
        val error: Throwable? = null
    )

    sealed class Action<out T> {
        object Refresh : Action<Nothing>()

        data class Refreshed<out T : Any>(
            val data: T
        ) : Action<T>()

        data class RefreshError(
            val throwable: Throwable
        ) : Action<Nothing>()
    }

    fun <T> reduce(
        state: State<T>,
        action: Action<T>
    ): State<T> = when (action) {
        is Action.Refresh -> state.copy(isRefreshing = true, error = null)
        is Action.Refreshed -> state.copy(isRefreshing = false, data = action.data)
        is Action.RefreshError -> state.copy(isRefreshing = false, error = action.throwable)
    }

    class StateToUiAdapter<T>(
        private val shimmerLayout: View,
        private val swipeRefreshLayout: SwipeRefreshLayout,
        private val errorStubView: ErrorStubView
    ) {

        fun render(state: State<T>, onRenderData: (data: T) -> Unit) {
            swipeRefreshLayout.isRefreshingSafe = state.isRefreshing
            swipeRefreshLayout.visibleOrGone(state.data != null)

            shimmerLayout.visibleOrGone(
                state.isRefreshing && state.data == null
            )

            errorStubView.visibleOrGone(
                isVisible = state.error != null && state.data == null
            )

            state.data?.let(onRenderData)
        }
    }
}