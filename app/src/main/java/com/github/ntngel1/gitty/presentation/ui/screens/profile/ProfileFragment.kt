package com.github.ntngel1.gitty.presentation.ui.screens.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.base.BaseFragment

class ProfileFragment : BaseFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private const val USER_LOGIN_KEY = "user_id"

        fun newInstance(userLogin: String) = ProfileFragment().apply {
            arguments = bundleOf(USER_LOGIN_KEY to userLogin)
        }
    }
}