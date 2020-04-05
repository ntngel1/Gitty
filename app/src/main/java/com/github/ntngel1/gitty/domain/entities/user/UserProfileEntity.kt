/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.user

data class UserProfileEntity(
    val login: String,
    val name: String?,
    val avatarUrl: String,
    val status: UserStatusEntity,
    val repositoriesCount: Int,
    val projectsCount: Int,
    val starredRepositoriesCount: Int,
    val followersCount: Int,
    val followingCount: Int
)