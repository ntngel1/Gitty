/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.unstar_repository

import io.reactivex.Single

interface UnstarRepositoryInteractor {

    data class UnstarResult(
        val isRepositoryStarred: Boolean,
        val starsCount: Int
    )

    operator fun invoke(repositoryId: String): Single<UnstarResult>
}