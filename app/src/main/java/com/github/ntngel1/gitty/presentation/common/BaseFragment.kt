/*
 * Copyright (c) 6.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.github.ntngel1.gitty.presentation.di.Scopes
import com.github.ntngel1.gitty.presentation.utils.objectScopeName
import moxy.MvpAppCompatFragment
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseFragment : MvpAppCompatFragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var scope: Scope
        private set

    private var isInstanceStateSaved = false
    private lateinit var fragmentScopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(FRAGMENT_SCOPE_NAME_KEY)
            ?: objectScopeName()

        scope = if (Toothpick.isScopeOpen(fragmentScopeName)) {
            Timber.d("Using existing UI scope: $fragmentScopeName")
            Toothpick.openScope(fragmentScopeName)
        } else {
            Timber.d("Creating UI scope: $fragmentScopeName -> ${parentFragmentScopeName()}")
            Toothpick.openScopes(parentFragmentScopeName(), fragmentScopeName)
                .also(::initScope)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onResume() {
        super.onResume()
        isInstanceStateSaved = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(FRAGMENT_SCOPE_NAME_KEY, fragmentScopeName)
        isInstanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needToCloseScope()) {
            Timber.d("Destroying UI scope: $fragmentScopeName")
            Toothpick.closeScope(fragmentScopeName)
        }
    }

    protected open fun initScope(scope: Scope) {}

    private fun isFullyDestroying(): Boolean =
        (isRemoving && !isInstanceStateSaved) || // Because isRemoving == true for fragment in backstack on screen rotation
                ((parentFragment as? BaseFragment)?.isFullyDestroying() ?: false)

    private fun needToCloseScope() =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isFullyDestroying()
        }

    private fun parentFragmentScopeName() =
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: Scopes.INTERACTOR_SCOPE

    companion object {
        private const val FRAGMENT_SCOPE_NAME_KEY = "fragment_scope_name"
    }
}