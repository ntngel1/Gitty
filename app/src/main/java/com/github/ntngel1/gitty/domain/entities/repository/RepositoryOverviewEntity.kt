/*
 * Copyright (c) 16.4.2020
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
    val forkedFromOwnerName: String?,
    val description: String?,
    val isCurrentUserStarredRepo: Boolean,
    val currentUserSubscriptionStatus: RepositorySubscription,
    val isCurrentUserAdmin: Boolean
)