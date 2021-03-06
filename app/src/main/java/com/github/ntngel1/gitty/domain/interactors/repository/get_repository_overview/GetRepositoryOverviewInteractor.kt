/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_overview

import com.github.ntngel1.gitty.domain.entities.repository.RepositoryOverviewEntity
import io.reactivex.Single

interface GetRepositoryOverviewInteractor {
    operator fun invoke(id: String): Single<RepositoryOverviewEntity>
}