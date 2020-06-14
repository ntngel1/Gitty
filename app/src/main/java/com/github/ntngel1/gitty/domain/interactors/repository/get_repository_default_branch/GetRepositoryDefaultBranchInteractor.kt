/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_default_branch

import io.reactivex.Maybe

interface GetRepositoryDefaultBranchInteractor {
    operator fun invoke(repositoryId: String): Maybe<String>
}