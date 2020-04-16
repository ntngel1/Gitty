/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.user

data class RepositoriesPageEntity(
    val hasNextPage: Boolean,
    val cursor: String?,
    val repositories: List<UserRepositoryEntity>
)