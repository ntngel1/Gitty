/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.internal.checkMainThread
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

fun RecyclerView.paginationEvents(): Observable<Unit> =
    RecyclerViewPaginationEventObservable(this)

private class RecyclerViewPaginationEventObservable(
    private val recyclerView: RecyclerView
) : Observable<Unit>() {

    override fun subscribeActual(observer: Observer<in Unit>) {
        if (!checkMainThread(observer)) {
            return
        }

        val listener = Listener(recyclerView, observer)
        observer.onSubscribe(listener)
        recyclerView.addOnScrollListener(listener.scrollListener)
    }

    class Listener(
        private val recyclerView: RecyclerView,
        observer: Observer<in Unit>
    ) : MainThreadDisposable() {

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isDisposed) {
                    return
                }

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    observer.onNext(Unit)
                }
            }
        }

        override fun onDispose() {
            recyclerView.removeOnScrollListener(scrollListener)
        }
    }
}