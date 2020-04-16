/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.repository

data class RepositoryHeaderEntity(
    val issuesCount: Int,
    val projectsCount: Int,
    val pullRequestsCount: Int,
    val isCurrentUserAdmin: Boolean
)