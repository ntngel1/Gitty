/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

import com.github.ntngel1.gitty.presentation.utils.logErrors
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.withLatestFrom

class Paginator<T>(
    private val delegate: PaginatorDelegate<T>
) : Consumer<PaginatorAction<T>> {

    private val actions = PublishRelay.create<PaginatorAction<T>>()
    private val state = BehaviorRelay.createDefault<PaginatorState<T>>(PaginatorState.EmptyLoading)

    fun subscribeState(onStateChanged: (state: PaginatorState<T>) -> Unit): Disposable {
        val compositeDisposable = CompositeDisposable()

        actions.observeOn(AndroidSchedulers.mainThread())
            .withLatestFrom(state) { action, currentState -> action to currentState }
            .map { (action, currentState) ->
                reduce(currentState, action)
            }
            .distinctUntilChanged()
            .logErrors()
            .subscribe(state::accept)
            .let(compositeDisposable::add)

        actions
            .publish { shared ->
                Observable.merge(
                    initialActionMiddleware(shared),
                    loadNextPageActionMiddleware(shared),
                    refreshActionMiddleware(shared)
                )
            }
            .logErrors()
            .subscribe(actions::accept)
            .let(compositeDisposable::add)

        state.observeOn(AndroidSchedulers.mainThread())
            .subscribe(onStateChanged)
            .let(compositeDisposable::add)

        return compositeDisposable
    }

    private fun initialActionMiddleware(shared: Observable<PaginatorAction<T>>): Observable<PaginatorAction<T>>? {
        return shared.ofType<PaginatorAction.Initial>()
            .flatMap {
                delegate.loadFirstPage()
                    .toObservable()
                    .map<PaginatorAction<T>> { page ->
                        PaginatorAction.PageLoaded(page)
                    }
                    .onErrorReturn { throwable ->
                        PaginatorAction.Error(throwable)
                    }
            }
    }

    private fun loadNextPageActionMiddleware(shared: Observable<PaginatorAction<T>>): Observable<PaginatorAction<T>>? {
        return shared.ofType<PaginatorAction.LoadNextPage>()
            .observeOn(AndroidSchedulers.mainThread())
            .withLatestFrom(state) { _, currentState -> currentState }
            .flatMap { currentState ->
                val cursor = (currentState as? PaginatorState.LoadingNextPage)?.nextPageCursor
                if (cursor != null) {
                    delegate.loadNextPage(cursor)
                        .toObservable()
                        .map<PaginatorAction<T>> { page ->
                            PaginatorAction.PageLoaded(page)
                        }
                        .onErrorReturn { throwable ->
                            PaginatorAction.Error(throwable)
                        }
                } else {
                    Observable.empty()
                }
            }
    }

    private fun refreshActionMiddleware(shared: Observable<PaginatorAction<T>>): Observable<PaginatorAction<T>>? {
        return shared.ofType<PaginatorAction.Refresh>()
            .flatMap {
                delegate.loadFirstPage()
                    .toObservable()
                    .map<PaginatorAction<T>> { page ->
                        PaginatorAction.PageLoaded(page)
                    }
                    .onErrorReturn { throwable ->
                        PaginatorAction.Error(throwable)
                    }
            }
    }

    private fun reduce(
        currentState: PaginatorState<T>,
        action: PaginatorAction<T>
    ): PaginatorState<T> = when (currentState) {
        is PaginatorState.EmptyLoading -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    currentState
                }
                is PaginatorAction.Refresh -> {
                    throw IllegalStateException("Already refreshing")
                }
                is PaginatorAction.LoadNextPage -> {
                    throw IllegalStateException("Cannot load next page on empty loading")
                }
                is PaginatorAction.PageLoaded -> {
                    if (!action.page.isInitialPage) {
                        throw IllegalStateException("Page is not initial!")
                    }

                    if (action.page.hasNextPage && action.page.cursor != null) {
                        PaginatorState.Data(action.page.items, action.page.cursor)
                    } else {
                        PaginatorState.FullData(action.page.items)
                    }
                }
                is PaginatorAction.Error -> {
                    PaginatorState.EmptyError(action.error)
                }
            }
        }
        is PaginatorState.EmptyContent -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.Refresh -> {
                    PaginatorState.EmptyLoading
                }
                is PaginatorAction.LoadNextPage -> {
                    throw IllegalStateException("Cannot load next page on empty content")
                }
                is PaginatorAction.PageLoaded -> {
                    throw IllegalStateException("State should be empty loading")
                }
                is PaginatorAction.Error -> {
                    throw IllegalStateException("State should be empty loading")
                }
            }
        }
        is PaginatorState.EmptyError -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.Refresh -> {
                    PaginatorState.EmptyLoading
                }
                is PaginatorAction.LoadNextPage -> {
                    throw IllegalStateException("Cannot load next page on empty content")
                }
                is PaginatorAction.PageLoaded -> {
                    throw IllegalStateException("State should be empty loading")
                }
                is PaginatorAction.Error -> {
                    throw IllegalStateException("State should be empty loading")
                }
            }
        }
        is PaginatorState.Data -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    PaginatorState.LoadingNextPage(currentState.items, currentState.nextPageCursor)
                }
                is PaginatorAction.Refresh -> {
                    PaginatorState.Refreshing(currentState.items, currentState.nextPageCursor)
                }
                is PaginatorAction.PageLoaded<T> -> {
                    // Skipping because we can receive this action when we're triggered loading next
                    // page and after that refreshed all data, then this next page loading ends up
                    // and we're receiving this action here...
                    currentState
                }
                is PaginatorAction.Error -> {
                    throw IllegalStateException("State should be loading next page or refreshing")
                }
            }
        }
        is PaginatorState.FullData -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    currentState
                }
                is PaginatorAction.Refresh -> {
                    PaginatorState.RefreshingWithFullData(currentState.items)
                }
                is PaginatorAction.PageLoaded -> {
                    // Skipping because we can receive this action when we're triggered loading next
                    // page and after that refreshed all data, then this next page loading ends up
                    // and we're receiving this action here...
                    currentState
                }
                is PaginatorAction.Error -> {
                    throw IllegalStateException("State should be loading next page or refreshing")
                }
            }
        }
        is PaginatorState.RefreshingWithFullData -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    currentState
                }
                is PaginatorAction.Refresh -> {
                    throw IllegalStateException("Already refreshing")
                }
                is PaginatorAction.PageLoaded -> {
                    if (!action.page.isInitialPage) {
                        throw IllegalStateException("Page is not initial")
                    }

                    if (action.page.hasNextPage && action.page.cursor != null) {
                        PaginatorState.Data(
                            items = action.page.items,
                            nextPageCursor = action.page.cursor
                        )
                    } else {
                        PaginatorState.FullData(
                            items = action.page.items
                        )
                    }
                }
                is PaginatorAction.Error -> {
                    // TODO SHOW error toast
                    PaginatorState.FullData(items = currentState.items)
                }
            }
        }
        is PaginatorState.Refreshing -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    PaginatorState.LoadingNextPageWithRefreshing(
                        items = currentState.items,
                        nextPageCursor = currentState.nextPageCursor
                    )
                }
                is PaginatorAction.Refresh -> {
                    throw IllegalStateException("Already refreshing data")
                }
                is PaginatorAction.PageLoaded -> {
                    if (action.page.hasNextPage && action.page.cursor != null) {
                        PaginatorState.Data(
                            items = action.page.items,
                            nextPageCursor = action.page.cursor
                        )
                    } else {
                        PaginatorState.FullData(
                            items = action.page.items
                        )
                    }
                }
                is PaginatorAction.Error -> {
                    // TODO SHOW ERROR
                    PaginatorState.Data(
                        items = currentState.items,
                        nextPageCursor = currentState.nextPageCursor
                    )
                }
            }
        }
        is PaginatorState.LoadingNextPage -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    currentState
                }
                is PaginatorAction.Refresh -> {
                    PaginatorState.LoadingNextPageWithRefreshing(
                        items = currentState.items,
                        nextPageCursor = currentState.nextPageCursor
                    )
                }
                is PaginatorAction.PageLoaded -> {
                    if (action.page.hasNextPage && action.page.cursor != null) {
                        PaginatorState.Data(
                            items = currentState.items + action.page.items,
                            nextPageCursor = action.page.cursor
                        )
                    } else {
                        PaginatorState.FullData(
                            items = currentState.items + action.page.items
                        )
                    }
                }
                is PaginatorAction.Error -> {
                    // TODO SHOW ERROR
                    PaginatorState.Data(
                        items = currentState.items,
                        nextPageCursor = currentState.nextPageCursor
                    )
                }
            }
        }
        is PaginatorState.LoadingNextPageWithRefreshing -> {
            when (action) {
                is PaginatorAction.Initial -> {
                    throw IllegalStateException("Must be in empty loading state")
                }
                is PaginatorAction.LoadNextPage -> {
                    currentState
                }
                is PaginatorAction.Refresh -> {
                    throw IllegalStateException("Already refreshing")
                }
                is PaginatorAction.PageLoaded -> {
                    if (action.page.isInitialPage) {
                        // set data
                        if (action.page.hasNextPage && action.page.cursor != null) {
                            PaginatorState.Data(
                                items = action.page.items,
                                nextPageCursor = action.page.cursor
                            )
                        } else {
                            PaginatorState.FullData(
                                items = action.page.items
                            )
                        }
                    } else {
                        if (action.page.hasNextPage && action.page.cursor != null) {
                            PaginatorState.Refreshing(
                                items = currentState.items + action.page.items,
                                nextPageCursor = action.page.cursor
                            )
                        } else {
                            PaginatorState.RefreshingWithFullData(
                                items = currentState.items + action.page.items
                            )
                        }
                    }

                    if (action.page.hasNextPage && action.page.cursor != null) {
                        PaginatorState.Data(
                            items = currentState.items + action.page.items,
                            nextPageCursor = action.page.cursor
                        )
                    } else {
                        PaginatorState.FullData(
                            items = currentState.items + action.page.items
                        )
                    }
                }
                is PaginatorAction.Error -> {
                    // TODO SHOW ERROR
                    PaginatorState.Data(
                        items = currentState.items,
                        nextPageCursor = currentState.nextPageCursor
                    )
                }
            }
        }
    }

    override fun accept(t: PaginatorAction<T>?) {
        t?.let(actions::accept)
    }
}