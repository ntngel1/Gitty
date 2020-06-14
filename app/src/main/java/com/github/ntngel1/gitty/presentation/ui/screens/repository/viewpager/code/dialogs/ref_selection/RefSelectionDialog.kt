/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.BaseBottomSheetDialogFragment
import com.github.ntngel1.gitty.presentation.common.pagination.PaginationScrollListener
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.di.modules.RefSelectionModule
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ButtonItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.LoadingErrorItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection.recyclerview.RefSelectionHeaderItem
import com.github.ntngel1.gitty.presentation.utils.argument
import com.github.ntngel1.gitty.presentation.utils.drawable
import kotlinx.android.synthetic.main.dialog_ref_selection.*
import moxy.ktx.moxyPresenter
import toothpick.Scope

class RefSelectionDialog : BaseBottomSheetDialogFragment(), RefSelectionView {

    interface Listener {
        fun onBranchSelected(branch: String)
    }

    override val layoutId: Int
        get() = R.layout.dialog_ref_selection

    private val selectedBranch: String by argument(SELECTED_BRANCH_KEY)

    private val listener by lazy {
        parentFragment as Listener
    }

    private val presenter by moxyPresenter {
        scope.getInstance(RefSelectionPresenter::class.java)
    }

    override fun initScope(scope: Scope) {
        super.initScope(scope)
        scope.installModules(RefSelectionModule(selectedBranch))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        super.onCreateDialog(savedInstanceState).apply {
            makeFullscreen()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun setState(state: RefSelectionState) {
        recycler_branch_selection.render {
            RefSelectionHeaderItem(
                id = "header",
                onBranchesClicked = callback {
                    presenter.onFilterChanged(RefSelectionState.FILTER_BRANCHES)
                },
                onTagsClicked = callback {
                    presenter.onFilterChanged(RefSelectionState.FILTER_TAGS)
                },
                selectedButton = when (state.filter) {
                    RefSelectionState.FILTER_BRANCHES -> RefSelectionHeaderItem.SELECTED_BUTTON_BRANCHES
                    RefSelectionState.FILTER_TAGS -> RefSelectionHeaderItem.SELECTED_BUTTON_TAGS
                    else -> throw IllegalStateException("No such button for filter: ${state.filter}")
                }
            ).render()

            state.branches
                .map { branch ->
                    ButtonItem(
                        id = "branch($branch)",
                        text = branch,
                        iconDrawable = if (branch == state.selectedBranch) {
                            drawable(R.drawable.ic_check_black)
                        } else {
                            null
                        },
                        onClicked = callback {
                            listener.onBranchSelected(branch)
                        }
                    )
                }
                .render()

            if (state.isLoading) {
                ProgressBarItem(id = "progress_bar")
                    .render()
            }

            if (state.isLoadingError) {
                LoadingErrorItem(
                    id = "loading_error",
                    onTryAgainClicked = callback {
                        presenter.onLoadNextPage()
                    }
                )
            }
        }
    }

    private fun Dialog.makeFullscreen() {
        setOnShowListener { dialog ->
            // TODO
        }
    }

    private fun setupRecyclerView() {
        with(recycler_branch_selection) {
            adapter = ItemAdapter()

            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )

            addItemDecoration(dividerItemDecoration)

            val scrollListener = PaginationScrollListener(presenter::onLoadNextPage)
            addOnScrollListener(scrollListener)
        }
    }

    companion object {
        private const val SELECTED_BRANCH_KEY = "selected_branch"

        fun newInstance(
            selectedBranch: String
        ) = RefSelectionDialog()
            .apply {
                arguments = bundleOf(
                    SELECTED_BRANCH_KEY to selectedBranch
                )
            }
    }
}