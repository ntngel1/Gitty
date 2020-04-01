package com.github.ntngel1.gitty.presentation.base.mvi

import io.reactivex.Observable

interface MviView<S : MviState, A: MviAction> {
    fun render(state: S)
    fun actions(): Observable<A>
}