/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.repository

data class RepositoryOverviewEntity(
    val forksCount: Int,
    val starsCount: Int,
    val watchersCount: Int,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val forkedFromRepositoryName: String?,
    val forkedFromRepositoryOwnerName: String?,
    val forkedFromRepositoryId: String?,
    val description: String?,
    val isCurrentUserStarredRepo: Boolean,
    val currentUserSubscriptionStatus: RepositorySubscription,
    val isCurrentUserAdmin: Boolean,
    val defaultBranchName: String?,
    val readmeMarkdownText: String? = null
)