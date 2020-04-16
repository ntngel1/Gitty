/*
 * Copyright (c) 16.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.ProfileHeaderEntity
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.di.modules.ProfileModule
import com.github.ntngel1.gitty.presentation.ui.screens.profile.viewpager.ProfileViewPagerAdapter
import com.github.ntngel1.gitty.presentation.utils.argument
import com.github.ntngel1.gitty.presentation.utils.setup
import kotlinx.android.synthetic.main.fragment_profile.*
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.Router
import toothpick.Scope
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileView {

    override val layoutId: Int
        get() = R.layout.fragment_profile

    @Inject
    lateinit var router: Router

    private val userLogin by argument<String>(USER_LOGIN_KEY)

    private val presenter by moxyPresenter {
        scope.getInstance(ProfilePresenter::class.java)
    }

    override fun initScope(scope: Scope) {
        super.initScope(scope)
        scope.installModules(ProfileModule(userLogin))
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

    override fun setProfileHeader(profileHeader: ProfileHeaderEntity) {
        shimmer_profile_header.hideShimmer()

        // Removing background that are used for shimmer layout
        textview_profile_name.background = null
        textview_profile_status.background = null

        textview_profile_name.text = profileHeader.name
        textview_profile_status.text =
            "${profileHeader.status.emoji} ${profileHeader.status.message}" // FIXME

        Glide.with(imageview_profile_avatar)
            .load(profileHeader.avatarUrl)
            .fitCenter()
            .into(imageview_profile_avatar)

        ProfileViewPagerAdapter.setupTabLayout(tablayout_profile, viewpager_profile, profileHeader)
    }

    private fun setupViewPager() {
        viewpager_profile.adapter = ProfileViewPagerAdapter(this)
        ProfileViewPagerAdapter.setupTabLayout(tablayout_profile, viewpager_profile)
    }

    private fun setupToolbar() {
        toolbar_profile.setup(title = userLogin, onBackClicked = router::exit)
        toolbar_profile.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_profile_details -> {
                }
                R.id.menu_profile_copy_link -> {
                }
                else -> return@setOnMenuItemClickListener false
            }

            true
        }

    }

    companion object {
        private const val USER_LOGIN_KEY = "user_login"

        fun newInstance(userLogin: String) = ProfileFragment().apply {
            arguments = bundleOf(USER_LOGIN_KEY to userLogin)
        }
    }
}