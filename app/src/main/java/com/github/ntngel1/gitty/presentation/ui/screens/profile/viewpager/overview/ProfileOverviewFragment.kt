/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.OverviewEntity
import com.github.ntngel1.gitty.domain.entities.user.PinnableItem
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.withItems
import com.github.ntngel1.gitty.presentation.common.recyclerview.item_decorations.SpacingItemDecoration
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview.PinnedGistItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview.PinnedHeaderItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.recyclerview.PinnedRepositoryItem
import com.github.ntngel1.gitty.presentation.utils.dp
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.fragment_profile_overview.*
import moxy.ktx.moxyPresenter

class ProfileOverviewFragment : BaseFragment(),
    ProfileOverviewView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_overview

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileOverviewPresenter::class.java)
    }

    private val spacingItemDecoration =
        SpacingItemDecoration()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        with(recyclerview_profile_overview) {
            adapter = ItemAdapter()
            addItemDecoration(spacingItemDecoration)
        }
    }

    override fun setOverview(overview: OverviewEntity) {
        shimmer_profile_overview.gone()
        swipe_refresh_layout_profile_overview.visible()

        val pinnedItems = overview.pinnedItems.map { pinnableItem ->
            when (pinnableItem) {
                is PinnableItem.Repository -> {
                    PinnedRepositoryItem(
                        id = "pinnedRepository(${pinnableItem.repository.id})",
                        name = pinnableItem.repository.name,
                        languageColor = pinnableItem.repository.languageColor,
                        description = pinnableItem.repository.description,
                        languageName = pinnableItem.repository.languageName,
                        forksCount = pinnableItem.repository.forksCount
                    )
                }
                is PinnableItem.Gist -> {
                    PinnedGistItem(
                        id = pinnableItem.gist.id,
                        name = pinnableItem.gist.name
                    )
                }
            }
        }

        recyclerview_profile_overview.withItems(
            spacingItemDecoration = spacingItemDecoration
        ) {
            spacing(16.dp)
            +PinnedHeaderItem()

            pinnedItems.forEach { pinnedItem ->
                spacing(8.dp)
                addItem(pinnedItem)
            }
        }
    }
}