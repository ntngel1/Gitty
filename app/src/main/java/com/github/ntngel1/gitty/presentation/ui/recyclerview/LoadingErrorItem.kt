/*
 * Copyright (c) 8.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.Callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import kotlinx.android.synthetic.main.item_loading_error.view.*

data class LoadingErrorItem(
    override val id: String,
    val onTryAgainClicked: Callback<Unit>
) : Item<LoadingErrorItem>() {

    override val layoutId: Int
        get() = R.layout.item_loading_error

    override fun bind(view: View) = with(view) {
        button_loading_error_try_again.setOnClickListener {
            onTryAgainClicked.listener.invoke()
        }
    }

    override fun recycle(view: View) = with(view) {
        super.recycle(view)
        button_loading_error_try_again.setOnClickListener(null)
    }
}