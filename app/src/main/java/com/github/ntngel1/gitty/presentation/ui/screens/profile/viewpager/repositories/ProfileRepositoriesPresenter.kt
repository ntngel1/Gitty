/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import com.github.ntngel1.gitty.domain.entities.user.RepositoriesPageEntity
import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.domain.gateways.UserGateway
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.pagination.Paginator
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorAction
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorDelegate
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorPage
import com.github.ntngel1.gitty.presentation.di.UserLogin
import io.reactivex.Single
import moxy.InjectViewState
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
        loadRepositories()
    }

    override fun loadFirstPage(): Single<PaginatorPage<RepositoryEntity>> {
        return userGateway.getRepositories(login = userLogin, limit = PAGE_LIMIT, cursor = null)
            .map { repositoriesPage ->
                repositoriesPage.toPaginatorPage()
            }
    }

    override fun loadNextPage(cursor: String): Single<PaginatorPage<RepositoryEntity>> {
        return userGateway.getRepositories(login = userLogin, limit = PAGE_LIMIT, cursor = cursor)
            .map { repositoriesPage ->
                repositoriesPage.toPaginatorPage()
            }
    }

    fun onRefresh() {
        paginator.accept(PaginatorAction.Refresh)
    }

    fun onLoadNextPage() {
        paginator.accept(PaginatorAction.LoadNextPage)
    }

    private fun loadRepositories() {
        paginator.subscribeState(viewState::setState)
            .disposeOnDestroy()

        paginator.accept(PaginatorAction.Initial)
    }

    private fun RepositoriesPageEntity.toPaginatorPage() =
        PaginatorPage(
            hasNextPage = hasNextPage,
            cursor = cursor,
            isInitialPage = true,
            items = repositories
        )

    companion object {
        private const val PAGE_LIMIT = 10
    }
}