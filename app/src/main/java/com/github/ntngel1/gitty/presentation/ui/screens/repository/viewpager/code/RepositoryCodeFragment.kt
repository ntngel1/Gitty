/*
 * Copyright (c) 14.6.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.domain.entities.repository.FileTreeEntry
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ButtonItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.LoadingErrorItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code.dialogs.ref_selection.RefSelectionDialog
import com.github.ntngel1.gitty.presentation.utils.drawable
import com.github.ntngel1.gitty.presentation.utils.safeShow
import kotlinx.android.synthetic.main.fragment_repository_code.*
import moxy.ktx.moxyPresenter

class RepositoryCodeFragment : BaseFragment(), RepositoryCodeView, RefSelectionDialog.Listener {

    override val layoutId: Int
        get() = R.layout.fragment_repository_code

    private val presenter by moxyPresenter {
        scope.getInstance(RepositoryCodePresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun setState(state: RepositoryCodeState) {
        recycler_repository_code.render {
            if (state.branch != null) {
                ButtonItem(
                    id = "branch",
                    text = state.branch,
                    onClicked = callback(hashable = true) {
                        showBranchSelectionDialog(state.branch)
                    },
                    iconDrawable = drawable(R.drawable.ic_branch_black)
                ).render()
            }

            if (state.shouldDisplayBackButton) {
                ButtonItem(
                    id = "back_button",
                    text = "...",
                    onClicked = callback {
                        presenter.onBackClicked()
                    }
                ).render()
            }

            state.visibleFileTree
                ?.map { fileTreeEntry ->
                    ButtonItem(
                        id = fileTreeEntry.id,
                        text = fileTreeEntry.name,
                        iconDrawable = when (fileTreeEntry) {
                            is FileTreeEntry.Folder -> drawable(R.drawable.ic_file_directory_black)
                            is FileTreeEntry.Blob -> iconForBlob(fileTreeEntry)
                        },
                        onClicked = callback(hashable = true) {
                            presenter.onFileTreeEntryClicked(fileTreeEntry)
                        }
                    )
                }
                ?.render()

            if (state.isLoading) {
                ProgressBarItem(
                    id = "progress_bar"
                ).render()
            }

            if (state.error != null) {
                LoadingErrorItem(
                    id = "loading_error",
                    onTryAgainClicked = callback {
                        presenter.onTryAgainClicked()
                    }
                ).render()
            }
        }
    }

    override fun onBranchSelected(branch: String) {

    }

    private fun iconForBlob(blob: FileTreeEntry.Blob): Drawable? {
        return null
    }

    private fun setupRecyclerView() {
        with(recycler_repository_code) {
            adapter = ItemAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun showBranchSelectionDialog(selectedBranch: String) {
        RefSelectionDialog.newInstance(selectedBranch)
            .safeShow(childFragmentManager)
    }
}