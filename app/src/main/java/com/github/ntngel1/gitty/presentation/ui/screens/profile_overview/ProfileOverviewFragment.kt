package com.github.ntngel1.gitty.presentation.ui.screens.profile_overview

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.PinnableItem
import com.github.ntngel1.gitty.domain.entities.user.ProfileOverviewEntity
import com.github.ntngel1.gitty.presentation.base.BaseFragment
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.base.recyclerview.withItems
import com.github.ntngel1.gitty.presentation.ui.screens.profile_overview.recyclerview.PinnedGistItem
import com.github.ntngel1.gitty.presentation.ui.screens.profile_overview.recyclerview.PinnedRepositoryItem
import com.github.ntngel1.gitty.presentation.utils.gone
import com.github.ntngel1.gitty.presentation.utils.visible
import kotlinx.android.synthetic.main.fragment_profile_overview.*
import moxy.ktx.moxyPresenter

class ProfileOverviewFragment : BaseFragment(), ProfileOverviewView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_overview

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileOverviewPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_profile_overview.adapter = ItemAdapter()
    }

    override fun setOverview(overview: ProfileOverviewEntity) {
        shimmer_profile_overview.gone()
        swipe_refresh_layout_profile_overview.visible()

        recyclerview_profile_overview.withItems {
            +overview.pinnedItems.map { pinnableItem ->
                when(pinnableItem) {
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
        }
    }
}