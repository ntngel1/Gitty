/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_branches

import com.github.ntngel1.gitty.domain.entities.common.Page
import io.reactivex.Single

interface GetRepositoryRefsInteractor {
    operator fun invoke(
        repositoryId: String,
        limit: Int,
        cursor: String?,
        filter: Int
    ): Single<Page<String>>

    companion object {
        const val FILTER_BRANCHES = 1
        const val FILTER_TAGS = 2
    }
}