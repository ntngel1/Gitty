/*
 * Copyright (c) 17.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.interactors.repository.get_repository_readme_md

import io.reactivex.Maybe

interface GetRepositoryReadmeMdInteractor {
    operator fun invoke(repositoryId: String, branchName: String): Maybe<String>
}