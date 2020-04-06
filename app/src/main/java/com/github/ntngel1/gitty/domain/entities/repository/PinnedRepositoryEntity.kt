/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.repository

data class PinnedRepositoryEntity(
    val id: String,
    val name: String,
    val description: String?,
    val languageName: String?,
    val languageColor: String?,
    val forksCount: Int
)