/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview.GetRepositoryOverviewInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.common.single_load.SingleLoad
import com.github.ntngel1.gitty.presentation.di.RepositoryId
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RepositoryOverviewPresenter @Inject constructor(
    @RepositoryId private val repositoryId: String,
    private val getRepositoryOverview: GetRepositoryOverviewInteractor
) : BasePresenter<RepositoryOverviewView>() {

    private var currentState = SingleLoad.State<RepositoryOverviewEntity>()
        set(value) {
            field = value
            viewState.setState(value)
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
        TODO("Not yet implemented")
    }

    fun onOwnerClicked() {
        TODO("Not yet implemented")
    }

    fun onStarClicked() {
        TODO("Not yet implemented")
    }

    fun onWatchClicked() {
        TODO("Not yet implemented")
    }
}