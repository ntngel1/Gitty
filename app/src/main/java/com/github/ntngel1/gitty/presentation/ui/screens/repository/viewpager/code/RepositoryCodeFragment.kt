/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.screens.repository.viewpager.code

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.BaseFragment
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.ItemAdapter
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.render
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ButtonItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.LoadingErrorItem
import com.github.ntngel1.gitty.presentation.ui.recyclerview.ProgressBarItem
import com.github.ntngel1.gitty.presentation.utils.visibleOrGone
import kotlinx.android.synthetic.main.fragment_repository_code.*
import moxy.ktx.moxyPresenter

class RepositoryCodeFragment : BaseFragment(), RepositoryCodeView {

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
        shimmer_repository_code.visibleOrGone(
            state.isLoading && state.fileTree.isNullOrEmpty()
        )

        error_stub_repository_code.visibleOrGone(
            state.error != null && state.fileTree.isNullOrEmpty()
        )

        recycler_repository_code.render {
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
                        onClicked = callback(hashable = true) {
                            presenter.onFileTreeEntryClicked(fileTreeEntry)
                        }
                    )
                }
                ?.render()

            if (state.isLoading && !state.fileTree.isNullOrEmpty()) {
                ProgressBarItem(
                    id = "progress_bar"
                ).render()
            }

            if (state.error != null && !state.fileTree.isNullOrEmpty()) {
                LoadingErrorItem(
                    id = "loading_error",
                    onTryAgainClicked = callback {
                        presenter.onTryAgainClicked()
                    }
                ).render()
            }
        }
    }

    private fun setupRecyclerView() {
        with(recycler_repository_code) {
            adapter = ItemAdapter()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}