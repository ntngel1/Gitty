/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

import com.github.ntngel1.gitty.presentation.common.pagination.Pagination.State
import org.junit.Assert.*
import org.junit.Test

class PaginationTest {

    @Test
    fun `reduce correctly handles refreshing`() {
        State<Int>() // empty state
            .assertions {
                assertFalse(isRefreshing)
                assert(items.isEmpty())
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware handles Refresh action and starts loading data
            .reduce(Pagination.Action.StartedRefreshing)
            .assertions {
                assert(isRefreshing)
                assert(items.isEmpty())
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware loads data successfully and there is no more pages (because
            // nextPageCursor == null)
            .reduce(
                Pagination.Action.Refreshed(
                    items = listOf(1),
                    nextPageCursor = null
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1), items)
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1), items)
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware handles action
            .reduce(Pagination.Action.StartedRefreshing)
            .assertions {
                assert(isRefreshing)
                assertEquals(listOf(1), items)
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Catched error (maybe bad internet)
            .reduce(
                Pagination.Action.RefreshError(
                    throwable = Exception()
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1), items)
                assertNotNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Okay, let's try again, maybe internet is now stable
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1), items)
                assertNotNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware handling action and starts loading
            .reduce(Pagination.Action.StartedRefreshing)
            .assertions {
                assert(isRefreshing)
                assertEquals(listOf(1), items)
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            .reduce(
                Pagination.Action.Refreshed(
                    items = listOf(1, 2),
                    nextPageCursor = "cursor"
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2), items)
                assertNull(error)
                assertEquals("cursor", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
    }

    @Test
    fun `reduce correctly handles next page loading`() {
        // Starting with some already loaded page
        State(items = listOf(1, 2), nextPageCursor = "cursor")
            // User scrolls at the bottom of list
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2), items)
                assertNull(error)
                assertEquals("cursor", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware handles action and starts loading next page
            .reduce(Pagination.Action.StartedLoadingNextPage)
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2), items)
                assertNull(error)
                assertEquals("cursor", nextPageCursor)
                assert(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware loaded page
            .reduce(
                Pagination.Action.PageLoaded(
                    items = listOf(3, 4),
                    nextPageCursor = "cursor2"
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // User scrolls at the bottom of list again
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // Middleware handles it
            .reduce(Pagination.Action.StartedLoadingNextPage)
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assert(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            // And loading was failed
            .reduce(
                Pagination.Action.PageError(
                    throwable = Exception()
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assert(isPageLoadingError)
            }
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assertFalse(isLoadingNextPage)
                assert(isPageLoadingError)
            }
            .reduce(Pagination.Action.StartedLoadingNextPage)
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4), items)
                assertNull(error)
                assertEquals("cursor2", nextPageCursor)
                assert(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
            .reduce(
                Pagination.Action.PageLoaded(
                    items = listOf(5, 6),
                    nextPageCursor = null
                )
            )
            .assertions {
                assertFalse(isRefreshing)
                assertEquals(listOf(1, 2, 3, 4, 5, 6), items)
                assertNull(error)
                assertNull(nextPageCursor)
                assertFalse(isLoadingNextPage)
                assertFalse(isPageLoadingError)
            }
    }

    private fun <T> State<T>.assertions(block: State<T>.() -> Unit): State<T> {
        return this.apply(block)
    }

    private fun <T> State<T>.reduce(action: Pagination.Action<T>): State<T> {
        return Pagination.reduce(this, action)
    }
}