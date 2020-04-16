/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.user

import org.threeten.bp.Instant

data class UserRepositoryEntity(
    val id: String,
    val name: String,
    val description: String?,
    val forksCount: Int,
    val starsCount: Int,
    val updatedAt: Instant,
    val forkedFromRepositoryName: String?,
    val forkedFromRepositoryOwner: String?,
    val licenseName: String?,
    val languageName: String?,
    val languageColor: String?
)