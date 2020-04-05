package com.github.ntngel1.gitty.presentation.ui.screens.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.user.UserProfileEntity
import com.github.ntngel1.gitty.presentation.base.BaseFragment
import com.github.ntngel1.gitty.presentation.di.modules.ProfileModule
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

    override fun setProfileHeader(userProfile: UserProfileEntity) {
        shimmer_profile_header.hideShimmer()

        // Removing background that are used for shimmer layout
        textview_profile_name.background = null
        textview_profile_status.background = null

        textview_profile_name.text = userProfile.name
        textview_profile_status.text = "${userProfile.status.emoji} ${userProfile.status.message}"

        Glide.with(imageview_profile_avatar)
            .load(userProfile.avatarUrl)
            .fitCenter()
            .into(imageview_profile_avatar)

        ProfileViewPagerAdapter.setupTabLayout(tablayout_profile, viewpager_profile, userProfile)
    }

    private fun setupViewPager() {
        viewpager_profile.adapter = ProfileViewPagerAdapter(this)
        ProfileViewPagerAdapter.setupTabLayout(tablayout_profile, viewpager_profile)
    }

    private fun setupToolbar() {
        toolbar_profile.setup(title = userLogin, onBackClicked = router::exit)
        toolbar_profile.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.menu_profile_details -> TODO()
                R.id.menu_profile_copy_link -> TODO()
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