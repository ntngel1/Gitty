/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.di.modules.RepositoryModule
import com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.RepositoryViewPagerAdapter
import com.github.ntngel1.gitty.presentation.utils.argument
import com.github.ntngel1.gitty.presentation.utils.setup
import kotlinx.android.synthetic.main.fragment_repository.*
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import toothpick.Scope
import javax.inject.Inject

class RepositoryFragment : BaseFragment(), RepositoryView {

    override val layoutId: Int
        get() = R.layout.fragment_repository

    @Inject
    lateinit var router: Router

    private val repositoryName by argument<String>(REPOSITORY_NAME_KEY)
    private val repositoryId by argument<String>(REPOSITORY_ID_KEY)

    private val presenter by moxyPresenter {
        scope.getInstance(RepositoryPresenter::class.java)
    }

    override fun initScope(scope: Scope) {
        super.initScope(scope)
        scope.installModules(RepositoryModule(repositoryId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewPager()
    }

    override fun setRepositoryHeader(repositoryHeader: RepositoryHeaderEntity) {
        RepositoryViewPagerAdapter.setupTabLayout(
            tablayout_repository,
            viewpager_repository,
            repositoryHeader
        )
    }

    private fun setupViewPager() {
        viewpager_repository.adapter = RepositoryViewPagerAdapter(this)
        RepositoryViewPagerAdapter.setupTabLayout(
            tablayout_repository,
            viewpager_repository,
            repositoryHeader = null
        )
    }

    private fun setupToolbar() {
        toolbar_repository.setup(title = repositoryName, onBackClicked = router::exit)
    }

    companion object {
        private const val REPOSITORY_NAME_KEY = "repository_name"
        private const val REPOSITORY_ID_KEY = "repository_id"

        fun newInstance(
            repositoryName: String,
            repositoryId: String
        ) = RepositoryFragment().apply {
            arguments = bundleOf(
                REPOSITORY_NAME_KEY to repositoryName,
                REPOSITORY_ID_KEY to repositoryId
            )
        }
    }
}