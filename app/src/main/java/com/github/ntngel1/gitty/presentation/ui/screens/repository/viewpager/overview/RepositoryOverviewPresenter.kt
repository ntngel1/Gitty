/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview.GetRepositoryOverviewInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.star_repository.StarRepositoryInteractor
import com.github.ntngel1.gitty.domain.interactors.repository.unstar_repository.UnstarRepositoryInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.single_load.SingleLoad
import com.github.ntngel1.gitty.presentation.di.RepositoryId
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RepositoryOverviewPresenter @Inject constructor(
    @RepositoryId private val repositoryId: String,
    private val getRepositoryOverview: GetRepositoryOverviewInteractor,
    private val starRepository: StarRepositoryInteractor,
    private val unstarRepository: UnstarRepositoryInteractor
) : BasePresenter<RepositoryOverviewView>() {

    private var currentState = SingleLoad.State<RepositoryOverviewEntity>()
        set(value) {
            field = value
            viewState.setState(value)
        }

    private var starRepositoryDisposable: Disposable? = null
        set(value) {
            field = value
            value?.disposeOnDestroy()
        }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onRefresh()
    }

    fun onRefresh() {
        getRepositoryOverview(repositoryId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = SingleLoad.reduce(currentState, SingleLoad.Action.Refresh)
            }
            .logErrors()
            .subscribe({ repositoryOverview ->
                currentState = SingleLoad.reduce(
                    currentState,
                    SingleLoad.Action.Refreshed(repositoryOverview)
                )
            }, { throwable ->
                currentState = SingleLoad.reduce(
                    currentState,
                    SingleLoad.Action.RefreshError(throwable)
                )
            })
            .disposeOnDestroy()
    }

    fun onForkClicked() {
        TODO("Not yet implemented")
    }

    fun onForkedFromClicked() {
        val forkedFromRepositoryName = currentState.data?.forkedFromRepositoryName
            ?: return

        val forkedFromRepositoryId = currentState.data?.forkedFromRepositoryId
            ?: return

        viewState.showRepositoryScreen(forkedFromRepositoryName, forkedFromRepositoryId)
    }

    fun onOwnerClicked() {
        val ownerName = currentState.data?.ownerName
            ?: return

        viewState.showProfileScreen(ownerName)
    }

    fun onStarClicked() {
        if (starRepositoryDisposable != null) {
            return
        }

        val isStarred = currentState.data?.isCurrentUserStarredRepo
            ?: return

        starRepositoryDisposable = if (isStarred) {
            unstarRepository()
        } else {
            starRepository()
        }
    }

    private fun unstarRepository(): Disposable {
        return unstarRepository(repositoryId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = currentState.copy(
                    data = currentState.data?.copy(
                        starsCount = currentState.data!!.starsCount - 1,
                        isCurrentUserStarredRepo = false
                    )
                )
            }
            .doFinally {
                starRepositoryDisposable = null
            }
            .logErrors()
            .subscribe({ result ->
                currentState = currentState.copy(
                    data = currentState.data?.copy(
                        isCurrentUserStarredRepo = result.isRepositoryStarred,
                        starsCount = result.starsCount
                    )
                )
            }, {
                // TODO Handle error
            })
    }

    private fun starRepository(): Disposable {
        return starRepository(repositoryId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                currentState = currentState.copy(
                    data = currentState.data?.copy(
                        starsCount = currentState.data!!.starsCount + 1,
                        isCurrentUserStarredRepo = true
                    )
                )
            }
            .doFinally {
                starRepositoryDisposable = null
            }
            .logErrors()
            .subscribe({ result ->
                currentState = currentState.copy(
                    data = currentState.data?.copy(
                        isCurrentUserStarredRepo = result.isRepositoryStarred,
                        starsCount = result.starsCount
                    )
                )
            }, {
                // TODO Handle error
            })
    }

    fun onWatchClicked() {
        TODO("Not yet implemented")
    }
}