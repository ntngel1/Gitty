/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.utils

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import timber.log.Timber

fun <T : Any> Observable<T>.logErrors(): Observable<T> =
    this.doOnError { throwable ->
        Timber.e(throwable)
    }

fun Completable.logErrors(): Completable =
    this.doOnError { throwable ->
        Timber.e(throwable)
    }

fun <T : Any> Maybe<T>.logErrors(): Maybe<T> =
    this.doOnError { throwable ->
        Timber.e(throwable)
    }

fun <T : Any> Single<T>.logErrors(): Single<T> =
    this.doOnError { throwable ->
        Timber.e(throwable)
    }