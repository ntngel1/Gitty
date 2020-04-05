package com.github.ntngel1.gitty.domain.entities.user

import com.github.ntngel1.gitty.domain.entities.gist.GistEntity
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryEntity

sealed class PinnableItem {
    data class Repository(val repository: RepositoryEntity) : PinnableItem()
    data class Gist(val gist: GistEntity) : PinnableItem()
}