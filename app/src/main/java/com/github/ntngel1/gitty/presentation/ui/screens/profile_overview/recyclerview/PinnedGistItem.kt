package com.github.ntngel1.gitty.presentation.ui.screens.profile_overview.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.Item
import kotlinx.android.synthetic.main.item_pinned_gist.view.*

data class PinnedGistItem(
    override val id: String,
    val name: String
) : Item<PinnedGistItem>() {

    override val layoutId: Int
        get() = R.layout.item_pinned_gist

    override fun bind(view: View) = with(view) {
        textview_pinned_gist_name.text = name
    }
}