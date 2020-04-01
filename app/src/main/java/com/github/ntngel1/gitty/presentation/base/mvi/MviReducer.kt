package com.github.ntngel1.gitty.presentation.base.mvi

interface MviReducer<S : MviState, A : MviAction> {
    fun reduce(state: S, action: A): S
}