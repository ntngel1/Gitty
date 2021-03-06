/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.stars

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.UserRepositoryEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.pagination.Pagination
import com.github.ntngel1.gitty.presentation.common.pagination.PaginationScrollListener
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback1
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.SpacingItemDecoration
import com.github.ntngel1.gitty.presentation.ui.Screens
import com.github.ntngel1.gitty.presentation.ui.recyclerview.LoadingErrorItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile.recyclerview.RepositoryItem
import com.github.ntngel1.gitty.presentation.utils.dp
import kotlinx.android.synthetic.main.fragment_profile_stars.*
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ProfileStarsFragment : BaseFragment(), ProfileStarsView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_stars

    @Inject
    lateinit var router: Router

    private lateinit var paginationStateToUiAdapter: Pagination.StateToUiAdapter<UserRepositoryEntity>

    private val spacingItemDecoration = SpacingItemDecoration()
    private val scrollListener by lazy {
        PaginationScrollListener(presenter::onLoadNextPage)
    }

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileStarsPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStateToUiAdapter()
        setupRecyclerView()
        setupRefreshListeners()
    }

    override fun setState(state: Pagination.State<UserRepositoryEntity>) {
        paginationStateToUiAdapter.render(state, ::showStarredRepositories)
    }

    override fun showRepositoryScreen(repositoryName: String, repositoryId: String) {
        router.navigateTo(
            Screens.Repository(
                repositoryName = repositoryName,
                repositoryId = repositoryId
            )
        )
    }

    private fun setupStateToUiAdapter() {
        paginationStateToUiAdapter = Pagination.StateToUiAdapter(
            shimmer_profile_stars,
            swipe_refresh_layout_profile_stars,
            error_stub_profile_stars,
            empty_content_stub_profile_stars,
            scrollListener
        )
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
        repositories: List<UserRepositoryEntity>,
        isLoadingNextPage: Boolean,
        isPageLoadingError: Boolean
    ) = recycler_profile_stars.render(spacingItemDecoration = spacingItemDecoration) {
        repositories
            .map { repository ->
                RepositoryItem(
                    id = "starredRepository(${repository.id})",
                    repositoryId = repository.id,
                    name = repository.name,
                    forkedFromRepositoryName = repository.forkedFromRepositoryName,
                    forkedFromRepositoryOwner = repository.forkedFromRepositoryOwner,
                    description = repository.description,
                    languageColor = repository.languageColor,
                    languageName = repository.languageName,
                    licenseName = repository.licenseName,
                    forksCount = repository.forksCount,
                    starsCount = repository.starsCount,
                    updatedAt = repository.updatedAt,
                    onClicked = callback1 { repositoryId ->
                        presenter.onRepositoryClicked(repositoryId)
                    }
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
