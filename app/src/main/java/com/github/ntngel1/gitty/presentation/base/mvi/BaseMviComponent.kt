package com.github.ntngel1.gitty.presentation.base.mvi

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom

abstract class BaseMviComponent<A : MviAction, S : MviState> : MviComponent<A, S> {

    private val actions = PublishRelay.create<A>()
    private val state by lazy {
        BehaviorRelay.createDefault(initialState)
    }

    abstract val initialState: S
    abstract val reducer: MviReducer<S, A>
    abstract val middlewares: List<MviMiddleware<A, S>>
    abstract val uiScheduler: Scheduler

    override fun bind(view: MviView<S, A>): Disposable {
        val disposables = CompositeDisposable()

        disposables += state.observeOn(uiScheduler).subscribe(view::render)
        disposables += view.actions().subscribe(actions::accept)

        return disposables
    }

    fun wire(): Disposable {
        val disposables = CompositeDisposable()

        disposables += actions
            .withLatestFrom(state) { action, state ->
                reducer.reduce(state, action)
            }
            .distinctUntilChanged()
            .subscribe(state::accept)

        disposables += Observable
            .merge<A>(
                middlewares.map { middleware ->
                    middleware.bind(actions, state)
                }
            )
            .subscribe(actions::accept)

        return disposables
    }
}