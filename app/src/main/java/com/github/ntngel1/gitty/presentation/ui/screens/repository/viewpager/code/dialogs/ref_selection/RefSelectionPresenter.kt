/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection

import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_branches.GetRepositoryRefsInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.di.RefName
import com.github.ntngel1.gitty.presentation.di.RepositoryId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RefSelectionPresenter @Inject constructor(
    @RepositoryId private val repositoryId: String,
    @RefName private val selectedBranch: String,
    private val getRepositoryRefs: GetRepositoryRefsInteractor
) : BasePresenter<RefSelectionView>() {

    private var currentState = RefSelectionState(selectedBranch = selectedBranch)
        set(value) {
            field = value
            viewState.setState(value)
        }

    private var refsLoadingDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadBranches()
    }

    fun onLoadNextPage() {
        loadBranches()
    }

    fun onFilterChanged(filter: Int) {
        refsLoadingDisposable?.dispose()
        refsLoadingDisposable = null

        require(filter == RefSelectionState.FILTER_BRANCHES || filter == RefSelectionState.FILTER_TAGS)

        currentState = currentState.copy(
            isInitialLoading = true,
            nextPageCursor = null,
            isLoading = false,
            isLoadingError = false,
            branches = emptyList(),
            filter = filter
        )

        loadBranches()
    }

    private fun loadBranches() {
        if (currentState.isLoading) {
            return
        }

        if (!currentState.isInitialLoading && currentState.nextPageCursor == null) {
            return
        }

        val filter = when (currentState.filter) {
            RefSelectionState.FILTER_BRANCHES -> GetRepositoryRefsInteractor.FILTER_BRANCHES
            RefSelectionState.FILTER_TAGS -> GetRepositoryRefsInteractor.FILTER_TAGS
            else -> throw IllegalStateException("No such domain filter for filter: ${currentState.filter}")
        }

        getRepositoryRefs(repositoryId, PAGE_LIMIT, currentState.nextPageCursor, filter)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = currentState.copy(
                    isLoading = true,
                    isLoadingError = false
                )
            }
            .subscribe({ branchesPage ->
                currentState = currentState.copy(
                    isLoading = false,
                    branches = currentState.branches + branchesPage.items,
                    isInitialLoading = false,
                    nextPageCursor = if (branchesPage.hasNextPage) {
                        branchesPage.cursor
                    } else {
                        null
                    }
                )
            }, {
                currentState = currentState.copy(
                    isLoading = false,
                    isLoadingError = true
                )
            })
            .also { disposable ->
                refsLoadingDisposable = disposable
            }
            .disposeOnDestroy()
    }

    companion object {
        private const val PAGE_LIMIT = 15
    }
}