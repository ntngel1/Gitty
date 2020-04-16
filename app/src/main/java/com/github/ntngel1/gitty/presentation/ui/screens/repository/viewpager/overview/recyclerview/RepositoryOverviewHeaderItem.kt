/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview.recyclerview

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.repository.RepositorySubscription
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.Callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import com.github.ntngel1.gitty.presentation.utils.*
import kotlinx.android.synthetic.main.item_repository_overview_header.view.*

data class RepositoryOverviewHeaderItem(
    override val id: String,
    val description: String?,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val forkedFromOwnerName: String? = null,
    val forkedFromRepositoryName: String? = null,
    val starsCount: Int,
    val forksCount: Int,
    val watchersCount: Int,
    val isForkButtonEnabled: Boolean,
    val isStarred: Boolean,
    val onStarClicked: Callback<Unit>,
    val onForkClicked: Callback<Unit>,
    val onWatchClicked: Callback<Unit>,
    val onOwnerClicked: Callback<Unit>,
    val onForkedFromClicked: Callback<Unit>,
    val subscriptionStatus: RepositorySubscription
) : Item<RepositoryOverviewHeaderItem>() {

    override val layoutId: Int
        get() = R.layout.item_repository_overview_header

    override fun bind(view: View) = with(view) {
        bindOwner()
        bindForkedFrom()
        bindDescription()
        bindStarButton()
        bindForkButton()
        bindWatchButton()
    }

    override fun recycle(view: View) = with(view) {
        super.recycle(view)
        text_repository_overview_header_owner.setOnClickListener(null)
        text_repository_overview_header_forked_from.setOnClickListener(null)
        button_repository_overview_header_star.setOnClickListener(null)
        button_repository_overview_header_fork.setOnClickListener(null)
        button_repository_overview_header_watch.setOnClickListener(null)
    }

    private fun View.bindWatchButton() {
        if (subscriptionStatus == RepositorySubscription.UNKNOWN) {
            return
        }

        button_repository_overview_header_watch.text = when (subscriptionStatus) {
            RepositorySubscription.IGNORED -> string(R.string.stop_ignoring)
            RepositorySubscription.SUBSCRIBED -> string(R.string.unwatch)
            RepositorySubscription.UNSUBSCRIBED -> string(R.string.watch)
            else -> throw IllegalStateException("No such button text for value: $subscriptionStatus")
        }

        button_repository_overview_header_watch.icon =
            if (subscriptionStatus == RepositorySubscription.IGNORED) {
                drawable(R.drawable.ic_mute_black)
            } else {
                drawable(R.drawable.ic_eye_black)
            }

        button_repository_overview_header_watch.setOnClickListener {
            onWatchClicked.listener.invoke()
        }
    }

    private fun View.bindForkButton() {
        button_repository_overview_header_fork.isEnabled = isForkButtonEnabled
        button_repository_overview_header_fork.text = if (forksCount > 0) {
            string(R.string.fork_with_count, forksCount)
        } else {
            string(R.string.fork)
        }

        button_repository_overview_header_fork.setOnClickListener {
            onForkClicked.listener.invoke()
        }
    }

    private fun View.bindStarButton() {
        button_repository_overview_header_star.setOnClickListener {
            onStarClicked.listener.invoke()
        }

        button_repository_overview_header_star.text = when {
            starsCount == 0 -> string(R.string.star)
            isStarred -> string(R.string.unstar, starsCount)
            !isStarred -> string(R.string.star_with_count, starsCount)
            else -> throw IllegalStateException("What the hell?")
        }

        button_repository_overview_header_star.icon = if (isStarred) {
            drawable(R.drawable.ic_star_white)
        } else {
            drawable(R.drawable.ic_star_border_white)
        }
    }

    private fun View.bindForkedFrom() {
        if (forkedFromRepositoryName == null || forkedFromOwnerName == null) {
            text_repository_overview_header_forked_from.gone()
            return
        }

        text_repository_overview_header_forked_from.visible()
        text_repository_overview_header_forked_from.setOnClickListener {
            onForkedFromClicked.listener.invoke()
        }

        text_repository_overview_header_forked_from.text = string(
            R.string.forked_from,
            forkedFromOwnerName,
            forkedFromRepositoryName
        )
    }

    private fun View.bindOwner() {
        text_repository_overview_header_owner.text = ownerName
        text_repository_overview_header_owner.setOnClickListener {
            onOwnerClicked.listener.invoke()
        }

        Glide.with(text_repository_overview_header_owner)
            .load(ownerAvatarUrl)
            .override(24.dp, 24.dp)
            .apply(RequestOptions().transform(CenterCrop()).transform(RoundedCorners(16)))
            .into(text_repository_overview_header_owner.glideDrawableLeft)
    }

    private fun View.bindDescription() {
        if (description == null) {
            text_repository_overview_header_description.gone()
            return
        }

        text_repository_overview_header_description.visible()
        text_repository_overview_header_description.text = description
    }
}