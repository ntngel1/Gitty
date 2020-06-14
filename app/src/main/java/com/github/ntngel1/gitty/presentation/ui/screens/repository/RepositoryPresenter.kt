/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository

import com.github.ntngel1.gitty.domain.interactors.repository.get_repository_header.GetRepositoryHeaderInteractor
import com.github.ntngel1.gitty.presentation.common.BasePresenter
import com.github.ntngel1.gitty.presentation.di.RepositoryId
import com.github.ntngel1.gitty.presentation.utils.logErrors
import io.reactivex.android.schedulers.AndroidSchedulers
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class RepositoryPresenter @Inject constructor(
    @RepositoryId private val repositoryId: String,
    private val getRepositoryHeader: GetRepositoryHeaderInteractor
) : BasePresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadRepositoryHeader()
    }

    private fun loadRepositoryHeader() {
        getRepositoryHeader(repositoryId)
            .logErrors()
            .retry()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ repositoryHeader ->
                viewState.setRepositoryHeader(repositoryHeader)
            }, { throwable ->
                // TODO Handle error
            })
            .disposeOnDestroy()
    }
}