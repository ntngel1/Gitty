/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories

import android.os.Bundle
import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.RepositoryEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.pagination.PaginatorState
import com.github.ntngel1.gitty.presentation.common.recyclerview.DividerItemDecoration
import com.github.ntngel1.gitty.presentation.common.recyclerview.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.withItems
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories.recyclerview.RepositoryItem
import kotlinx.android.synthetic.main.fragment_profile_repositories.*
import moxy.ktx.moxyPresenter

class ProfileRepositoriesFragment : BaseFragment(), ProfileRepositoriesView {

    override val layoutId: Int
        get() = R.layout.fragment_profile_repositories

    private val dividerItemDecoration = DividerItemDecoration()

    private val presenter by moxyPresenter {
        scope.getInstance(ProfileRepositoriesPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview_profile_repositories) {
            adapter = ItemAdapter()
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun setState(state: PaginatorState) {
        val repositories = when (state) {
            is PaginatorState.Data<*> -> {
                state.items as List<RepositoryEntity>
            }
            is PaginatorState.FullData<*> -> {
                state.items as List<RepositoryEntity>
            }
            else -> null
        }

        recyclerview_profile_repositories.withItems {
            +repositories?.map { repository ->
                RepositoryItem(
                    id = "repository(${repository.id})",
                    name = repository.name,
                    forkedFromRepositoryName = repository.forkedFromRepositoryName,
                    forkedFromRepositoryOwner = repository.forkedFromRepositoryOwner,
                    description = repository.description,
                    languageColor = repository.languageColor,
                    languageName = repository.languageName,
                    licenseName = repository.licenseName,
                    forksCount = repository.forksCount,
                    starsCount = 101, // TODO
                    updatedAt = repository.updatedAt
                )
            }
        }
    }
}