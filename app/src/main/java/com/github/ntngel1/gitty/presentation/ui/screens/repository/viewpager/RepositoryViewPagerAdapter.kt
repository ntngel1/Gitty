/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.repository.RepositoryHeaderEntity
import com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.overview.RepositoryOverviewFragment
import com.github.ntngel1.gitty.presentation.utils.string
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RepositoryViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var isSettingsScreenVisible: Boolean = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun createFragment(
        position: Int
    ): Fragment = when (position) {
        POSITION_OVERVIEW -> RepositoryOverviewFragment()
        else -> throw IllegalStateException("No such fragment for position $position")
    }

    override fun getItemCount(): Int {
        return if (isSettingsScreenVisible) 10 else 9
    }

    companion object {
        private const val POSITION_OVERVIEW = 0
        private const val POSITION_CODE = 1
        private const val POSITION_ISSUES = 2
        private const val POSITION_PULL_REQUESTS = 3
        private const val POSITION_ACTIONS = 4
        private const val POSITION_PROJECTS = 5
        private const val POSITION_WIKI = 6
        private const val POSITION_SECURITY = 7
        private const val POSITION_INSIGHTS = 8
        private const val POSITION_SETTINGS = 9

        /**
         * Setups TabLayout for Repository screen
         */
        fun setupTabLayout(
            tabLayout: TabLayout,
            viewPager: ViewPager2,
            repositoryHeader: RepositoryHeaderEntity?
        ) = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                POSITION_OVERVIEW -> {
                    tabLayout.string(R.string.overview)
                }
                POSITION_CODE -> {
                    tabLayout.string(R.string.repository_code)
                }
                POSITION_ISSUES -> {
                    repositoryHeader?.issuesCount?.let { issuesCount ->
                        tabLayout.string(
                            R.string.repository_issues_with_count,
                            issuesCount
                        )
                    } ?: tabLayout.string(R.string.repository_issues)
                }
                POSITION_PULL_REQUESTS -> {
                    repositoryHeader?.pullRequestsCount?.let { pullRequestsCount ->
                        tabLayout.string(
                            R.string.repository_pull_requests_with_count,
                            pullRequestsCount
                        )
                    } ?: tabLayout.string(R.string.repository_pull_requests)
                }
                POSITION_ACTIONS -> {
                    tabLayout.string(R.string.repository_actions)
                }
                POSITION_PROJECTS -> {
                    repositoryHeader?.projectsCount?.let { projectsCount ->
                        tabLayout.string(
                            R.string.repository_projects_with_count,
                            projectsCount
                        )
                    } ?: tabLayout.string(R.string.repository_projects)
                }
                POSITION_WIKI -> {
                    tabLayout.string(R.string.repository_wiki)
                }
                POSITION_SECURITY -> {
                    tabLayout.string(R.string.repository_security)
                }
                POSITION_INSIGHTS -> {
                    tabLayout.string(R.string.repository_insights)
                }
                POSITION_SETTINGS -> {
                    tabLayout.string(R.string.settings)
                }
                else -> throw IllegalStateException("No such title for position: $position")
            }
        }.attach()
    }
}