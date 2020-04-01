package com.github.ntngel1.gitty.presentation.base.mvi

import io.reactivex.disposables.Disposable

interface MviComponent<A : MviAction, S : MviState> {
    fun bind(view: MviView<S, A>): Disposable
}