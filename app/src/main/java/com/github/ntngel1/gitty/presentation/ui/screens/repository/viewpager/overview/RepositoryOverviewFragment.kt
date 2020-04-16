/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.common.single_load.SingleLoad
import com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview.recyclerview.RepositoryOverviewHeaderItem
import kotlinx.android.synthetic.main.fragment_repository_overview.*
import moxy.ktx.moxyPresenter

class RepositoryOverviewFragment : BaseFragment(), RepositoryOverviewView {

    override val layoutId: Int
        get() = R.layout.fragment_repository_overview

    private lateinit var singleLoadStateToUiAdapter: SingleLoad.StateToUiAdapter<RepositoryOverviewEntity>

    private val presenter by moxyPresenter {
        scope.getInstance(RepositoryOverviewPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStateToUiAdapter()
        setupRecyclerView()
        setupRefreshListeners()
    }

    override fun setState(state: SingleLoad.State<RepositoryOverviewEntity>) {
        singleLoadStateToUiAdapter.render(state, ::showRepositoryOverview)
    }

    private fun setupStateToUiAdapter() {
        singleLoadStateToUiAdapter = SingleLoad.StateToUiAdapter(
            shimmer_repository_overview,
            swipe_refresh_layout_repository_overview,
            error_stub_repository_overview
        )
    }

    private fun showRepositoryOverview(
        repositoryOverview: RepositoryOverviewEntity
    ) = recycler_repository_overview.render {
        RepositoryOverviewHeaderItem(
            id = "repository_overview_header",
            description = repositoryOverview.description,
            ownerAvatarUrl = repositoryOverview.ownerAvatarUrl,
            ownerName = repositoryOverview.ownerName,
            forkedFromOwnerName = repositoryOverview.forkedFromOwnerName,
            forkedFromRepositoryName = repositoryOverview.forkedFromRepositoryName,
            forksCount = repositoryOverview.forksCount,
            isStarred = repositoryOverview.isCurrentUserStarredRepo,
            watchersCount = repositoryOverview.watchersCount,
            starsCount = repositoryOverview.starsCount,
            subscriptionStatus = repositoryOverview.currentUserSubscriptionStatus,
            isForkButtonEnabled = !repositoryOverview.isCurrentUserAdmin,
            onForkClicked = callback {
                presenter.onForkClicked()
            },
            onForkedFromClicked = callback {
                presenter.onForkedFromClicked()
            },
            onOwnerClicked = callback {
                presenter.onOwnerClicked()
            },
            onStarClicked = callback {
                presenter.onStarClicked()
            },
            onWatchClicked = callback {
                presenter.onWatchClicked()
            }
        ).render()
    }

    private fun setupRefreshListeners() {
        swipe_refresh_layout_repository_overview.setOnRefreshListener(presenter::onRefresh)
        error_stub_repository_overview.setOnRefreshClickListener(presenter::onRefresh)
    }

    private fun setupRecyclerView() {
        with(recycler_repository_overview) {
            adapter = ItemAdapter()
        }
    }
}