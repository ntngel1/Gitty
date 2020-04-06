/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common.pagination

import io.reactivex.Single

interface PaginatorDelegate<T> {
    fun loadFirstPage(): Single<PaginatorPage<T>>
    fun loadNextPage(cursor: String): Single<PaginatorPage<T>>
}