/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.domain.interactors.user.get_user_repositories.GetUserRepositoriesInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.pagination.Pagination
import com.github.ntngel1.gitty.presentation.di.UserLogin
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfileRepositoriesPresenter @Inject constructor(
    @UserLogin private val userLogin: String,
    private val getUserRepositories: GetUserRepositoriesInteractor
) : BasePresenter<ProfileRepositoriesView>() {

    private var currentState = Pagination.State<RepositoryEntity>()
        set(value) {
            field = value
            viewState.setState(value)
        }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onRefresh()
    }

    fun onRefresh() {
        getUserRepositories(userLogin, PAGE_LIMIT, cursor = null)
            .doOnSubscribe {
                currentState = Pagination.reduce(
                    currentState,
                    Pagination.Action.StartedRefreshing
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribeBy(
                onSuccess = { repositoriesPage ->
                    currentState = Pagination.reduce(
                        currentState,
                        Pagination.Action.Refreshed(
                            items = repositoriesPage.repositories,
                            nextPageCursor = if (repositoriesPage.hasNextPage) {
                                repositoriesPage.cursor
                            } else {
                                null
                            }
                        )
                    )
                },
                onError = { throwable ->
                    currentState = Pagination.reduce(
                        currentState,
                        Pagination.Action.RefreshError(throwable)
                    )
                }
            )
            .disposeOnDestroy()
    }

    fun onLoadNextPage() {
        currentState.let { state ->
            if (currentState.isLoadingNextPage || currentState.nextPageCursor == null) {
                return
            }

            getUserRepositories(userLogin, PAGE_LIMIT, state.nextPageCursor)
                .doOnSubscribe {
                    currentState = Pagination.reduce(
                        currentState,
                        Pagination.Action.StartedLoadingNextPage
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .logErrors()
                .subscribeBy(
                    onSuccess = { repositoriesPage ->
                        currentState = Pagination.reduce(
                            currentState,
                            Pagination.Action.PageLoaded(
                                items = repositoriesPage.repositories,
                                nextPageCursor = if (repositoriesPage.hasNextPage) {
                                    repositoriesPage.cursor
                                } else {
                                    null
                                }
                            )
                        )
                    },
                    onError = { throwable ->
                        currentState = Pagination.reduce(
                            currentState,
                            Pagination.Action.PageError(throwable)
                        )
                    }
                )
        }
    }

    companion object {
        private const val PAGE_LIMIT = 15
    }
}