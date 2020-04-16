/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.domain.entities.user

import com.github.ntngel1.gitty.domain.entities.gist.GistEntity

sealed class PinnableItem {
    data class Repository(val repository: PinnedRepositoryEntity) : PinnableItem()
    data class Gist(val gist: GistEntity) : PinnableItem()
}