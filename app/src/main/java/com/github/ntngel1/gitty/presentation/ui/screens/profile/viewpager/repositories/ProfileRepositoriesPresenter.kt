/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import com.github.ntngel1.gitty.domain.entities.user.RepositoriesPageEntity
import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.pagination.*
import com.github.ntngel1.gitty.presentation.di.UserLogin
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class ProfileRepositoriesPresenter @Inject constructor(
    @UserLogin private val userLogin: String,
    private val userGateway: UserGateway
) : BasePresenter<ProfileRepositoriesView>(), PaginatorDelegate<RepositoryEntity> {

    private val paginator by lazy {
        Paginator(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        paginator.subscribe(object : Observer<PaginatorState> {
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}

            override fun onNext(t: PaginatorState) {
                viewState.setState(t)
            }

            override fun onError(e: Throwable) {
                Timber.d(e.toString())
            }
        })
        paginator.disposeOnDestroy()
        paginator.accept(PaginatorAction.Initial)
    }

    override fun loadFirstPage(): Single<PaginatorPage<RepositoryEntity>> {
        return userGateway.getRepositories(login = userLogin, limit = 15, cursor = null)
            .map { repositoriesPage ->
                repositoriesPage.toPaginatorPage()
            }
    }

    override fun loadNextPage(cursor: String): Single<PaginatorPage<RepositoryEntity>> {
        return userGateway.getRepositories(login = userLogin, limit = 15, cursor = cursor)
            .map { repositoriesPage ->
                repositoriesPage.toPaginatorPage()
            }
    }

    private fun RepositoriesPageEntity.toPaginatorPage() =
        PaginatorPage(
            hasNextPage = hasNextPage,
            cursor = cursor,
            isInitialPage = true,
            items = repositories
        )
}