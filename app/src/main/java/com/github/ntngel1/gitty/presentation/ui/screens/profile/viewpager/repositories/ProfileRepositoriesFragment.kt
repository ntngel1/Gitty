/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorState
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorStateAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.PaginationScrollListener
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.withItems
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.SpacingItemDecoration
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories.recyclerview.RepositoryItem
import com.github.ntngel1.gitty.presentation.utils.dp
import kotlinx.android.synthetic.main.fragment_profile_repositories.*
import moxy.ktx.moxyPresenter

class ProfileRepositoriesFragment : BaseFragment(), ProfileRepositoriesView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_repositories

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileRepositoriesPresenter::class.java)
    }

    private val paginatorStateAdapter by lazy {
        PaginatorStateAdapter(
            swipe_refresh_layout_profile_repositories,
            shimmer_profile_repositories,
            empty_content_stub_profile_repositories,
            error_stub_profile_repositories,
            ::showRepositories
        )
    }

    private val spacingItemDecoration =
        SpacingItemDecoration()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recycler_profile_repositories) {
            adapter = ItemAdapter()
            addItemDecoration(spacingItemDecoration)
            addOnScrollListener(
                PaginationScrollListener(
                    presenter::onLoadNextPage
                )
            )
        }

        swipe_refresh_layout_profile_repositories.setOnRefreshListener(presenter::onRefresh)
        empty_content_stub_profile_repositories.setOnRefreshClickListener(presenter::onRefresh)
        error_stub_profile_repositories.setOnRefreshClickListener(presenter::onRefresh)
    }

    override fun setState(state: PaginatorState<RepositoryEntity>) {
        paginatorStateAdapter.render(state)
    }

    private fun showRepositories(
        repositories: List<RepositoryEntity>,
        isLoadingNextPage: Boolean
    ) = recycler_profile_repositories.withItems(spacingItemDecoration = spacingItemDecoration) {
        val repositoryItems = repositories.map { repository ->
            RepositoryItem(
                id = "repository(${repository.id})",
                name = repository.name,
                forkedFromRepositoryName = repository.forkedFromRepositoryName,
                forkedFromRepositoryOwner = repository.forkedFromRepositoryOwner,
                description = repository.description,
                languageColor = repository.languageColor,
                languageName = repository.languageName,
                licenseName = repository.licenseName,
                forksCount = repository.forksCount,
                starsCount = repository.starsCount,
                updatedAt = repository.updatedAt
            )
        }

        repositoryItems.forEach { repositoryItem ->
            spacing(8.dp)
            addItem(repositoryItem)
        }

        if (isLoadingNextPage) {
            +ProgressBarItem(id = "progress_bar")
        }
    }

}