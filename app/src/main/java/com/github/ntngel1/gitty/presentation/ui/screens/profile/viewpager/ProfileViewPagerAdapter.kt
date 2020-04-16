/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.ProfileHeaderEntity
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.followers.ProfileFollowersFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.following.ProfileFollowingFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.overview.ProfileOverviewFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.projects.ProfileProjectsFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.repositories.ProfileRepositoriesFragment
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.stars.ProfileStarsFragment
import com.github.ntngel1.gitty.presentation.utils.string
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun createFragment(
        position: Int
    ): Fragment = when (position) {
        POSITION_OVERVIEW -> ProfileOverviewFragment()
        POSITION_REPOSITORIES -> ProfileRepositoriesFragment()
        POSITION_PROJECTS -> ProfileProjectsFragment()
        POSITION_STARRED_REPOSITORIES -> ProfileStarsFragment()
        POSITION_FOLLOWERS -> ProfileFollowersFragment()
        POSITION_FOLLOWING -> ProfileFollowingFragment()
        else -> throw IllegalStateException("No such fragment for position $position")
    }

    override fun getItemCount(): Int = 6

    companion object {
        private const val POSITION_OVERVIEW = 0
        private const val POSITION_REPOSITORIES = 1
        private const val POSITION_PROJECTS = 2
        private const val POSITION_STARRED_REPOSITORIES = 3
        private const val POSITION_FOLLOWERS = 4
        private const val POSITION_FOLLOWING = 5

        /**
         * Setups TabLayout for Profile screen.
         *
         * @param profileHeader using it to display counts (repositories count, projects count, etc)
         *                    inside tab title. If null, counts will not be displayed.
         */
        fun setupTabLayout(
            tabLayout: TabLayout,
            viewPager: ViewPager2,
            profileHeader: ProfileHeaderEntity? = null
        ) = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                POSITION_OVERVIEW -> {
                    tabLayout.string(R.string.overview)
                }
                POSITION_REPOSITORIES -> {
                    profileHeader?.repositoriesCount?.let { repositoriesCount ->
                        tabLayout.string(
                            R.string.profile_repositories_with_count,
                            repositoriesCount
                        )
                    } ?: tabLayout.string(R.string.profile_repositories)
                }
                POSITION_PROJECTS -> {
                    profileHeader?.projectsCount?.let { projectsCount ->
                        tabLayout.string(R.string.profile_projects_with_count, projectsCount)
                    } ?: tabLayout.string(R.string.profile_projects)
                }
                POSITION_STARRED_REPOSITORIES -> {
                    profileHeader?.starredRepositoriesCount?.let { starredRepositoriesCount ->
                        tabLayout.string(
                            R.string.profile_starred_repositories_with_count,
                            starredRepositoriesCount
                        )
                    } ?: tabLayout.string(R.string.profile_starred_repositories)
                }
                POSITION_FOLLOWERS -> {
                    profileHeader?.followersCount?.let { followersCount ->
                        tabLayout.string(R.string.profile_followers_with_count, followersCount)
                    } ?: tabLayout.string(R.string.profile_followers)
                }
                POSITION_FOLLOWING -> {
                    profileHeader?.followingCount?.let { followingCount ->
                        tabLayout.string(R.string.profile_following_with_count, followingCount)
                    } ?: tabLayout.string(R.string.profile_following)
                }
                else -> throw IllegalStateException("No such title for position: $position")
            }
        }.attach()
    }
}