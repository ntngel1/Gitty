/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import io.reactivex.Observable

fun <T, R> Observable<T>.mapOrEmpty(mapper: (T) -> R?): Observable<R> =
    this.flatMap { value ->
        mapper.invoke(value)
            ?.let { mappedValue ->
                Observable.just(mappedValue)
            } ?: Observable.empty()
    }