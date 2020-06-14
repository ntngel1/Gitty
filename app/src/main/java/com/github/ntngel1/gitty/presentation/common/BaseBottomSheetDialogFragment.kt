/*
 * Copyright (c) 14.6.2020
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
import moxy.MvpBottomSheetDialogFragment
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

abstract class BaseBottomSheetDialogFragment : MvpBottomSheetDialogFragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    protected lateinit var scope: Scope
        private set

    private var isInstanceStateSaved = false
    private lateinit var dialogScopeName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        dialogScopeName = savedInstanceState?.getString(DIALOG_SCOPE_NAME_KEY)
            ?: objectScopeName()

        scope = if (Toothpick.isScopeOpen(dialogScopeName)) {
            Timber.d("Using existing UI scope: $dialogScopeName")
            Toothpick.openScope(dialogScopeName)
        } else {
            Timber.d("Creating UI scope: $dialogScopeName -> ${parentFragmentScopeName()}")
            Toothpick.openScopes(parentFragmentScopeName(), dialogScopeName)
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
        outState.putString(DIALOG_SCOPE_NAME_KEY, dialogScopeName)
        isInstanceStateSaved = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needToCloseScope()) {
            Timber.d("Destroying UI scope: $dialogScopeName")
            Toothpick.closeScope(dialogScopeName)
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
        private const val DIALOG_SCOPE_NAME_KEY = "dialog_scope_name"
    }
}