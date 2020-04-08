/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.stars

import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.domain.interactors.user.get_user_starred_repositories.GetUserStarredRepositoriesInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.pagination.Pagination
import com.github.ntngel1.gitty.presentation.di.UserLogin
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ProfileStarsPresenter @Inject constructor(
    @UserLogin private val userLogin: String,
    private val getUserStarredRepositories: GetUserStarredRepositoriesInteractor
) : BasePresenter<ProfileStarsView>() {

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
        getUserStarredRepositories(userLogin, PAGE_LIMIT, cursor = null)
            .doOnSubscribe {
                currentState = Pagination.reduce(currentState, Pagination.Action.Refresh)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribe({ repositoriesPage ->
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
            }, { throwable ->
                currentState = Pagination.reduce(
                    currentState,
                    Pagination.Action.RefreshError(throwable)
                )
            })
            .disposeOnDestroy()
    }

    fun onLoadNextPage() {
        val state = currentState

        if (state.isLoadingNextPage || state.nextPageCursor == null) {
            return
        }

        getUserStarredRepositories(userLogin, PAGE_LIMIT, state.nextPageCursor)
            .doOnSubscribe {
                currentState = Pagination.reduce(
                    currentState,
                    Pagination.Action.LoadNextPage
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .logErrors()
            .subscribe({ repositoriesPage ->
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
            }, { throwable ->
                currentState = Pagination.reduce(
                    currentState,
                    Pagination.Action.PageError(throwable)
                )
            })
            .disposeOnDestroy()
    }

    companion object {
        private const val PAGE_LIMIT = 15
    }
}