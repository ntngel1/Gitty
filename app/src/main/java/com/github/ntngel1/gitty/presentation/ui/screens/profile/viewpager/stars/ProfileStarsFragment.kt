/*
 * Copyright (c) 9.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.stars

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.pagination.Pagination
import com.github.ntngel1.gitty.presentation.common.pagination.PaginationScrollListener
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.SpacingItemDecoration
import com.github.ntngel1.gitty.presentation.ui.recyclerview.LoadingErrorItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile.recyclerview.RepositoryItem
import com.github.ntngel1.gitty.presentation.utils.dp
import kotlinx.android.synthetic.main.fragment_profile_stars.*
import moxy.ktx.moxyPresenter

class ProfileStarsFragment : BaseFragment(), ProfileStarsView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_stars

    private val spacingItemDecoration = SpacingItemDecoration()
    private val scrollListener by lazy {
        PaginationScrollListener(presenter::onLoadNextPage)
    }

    private val paginationStateToUiAdapter by lazy {
        Pagination.StateToUiAdapter<RepositoryEntity>(
            shimmer_profile_stars,
            swipe_refresh_layout_profile_stars,
            error_stub_profile_stars,
            empty_content_stub_profile_stars,
            scrollListener
        )
    }

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileStarsPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRefreshListeners()
    }

    override fun setState(state: Pagination.State<RepositoryEntity>) {
        paginationStateToUiAdapter.render(state, ::showStarredRepositories)
    }

    private fun setupRefreshListeners() {
        swipe_refresh_layout_profile_stars.setOnRefreshListener(presenter::onRefresh)
        empty_content_stub_profile_stars.setOnRefreshClickListener(presenter::onRefresh)
        error_stub_profile_stars.setOnRefreshClickListener(presenter::onRefresh)
    }

    private fun setupRecyclerView() {
        with(recycler_profile_stars) {
            adapter = ItemAdapter()
            addItemDecoration(spacingItemDecoration)
            addOnScrollListener(scrollListener)
        }
    }

    private fun showStarredRepositories(
        repositories: List<RepositoryEntity>,
        isLoadingNextPage: Boolean,
        isPageLoadingError: Boolean
    ) = recycler_profile_stars.render(spacingItemDecoration = spacingItemDecoration) {
        repositories
            .map { repository ->
                RepositoryItem(
                    id = "starredRepository(${repository.id})",
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
            .render(spacingPx = 8.dp)

        if (isLoadingNextPage) {
            spacing(8.dp)
            ProgressBarItem(id = "progress_bar").render()
        }

        if (isPageLoadingError) {
            spacing(8.dp)
            LoadingErrorItem(
                id = "loading_error",
                onTryAgainClicked = callback {
                    presenter.onLoadNextPage()
                }
            ).render()
        }
    }
}
