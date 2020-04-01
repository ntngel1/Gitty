package com.github.ntngel1.gitty.presentation.base.mvi

import io.reactivex.Observable

interface MviMiddleware<A : MviAction, S : MviState> {
    fun bind(actions: Observable<A>, state: Observable<S>): Observable<A>
}