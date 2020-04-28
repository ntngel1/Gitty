/*
 * Copyright (c) 28.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.recyclerview

import android.view.View
import com.github.ntngel1.gitty.R
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.Callback
import com.github.ntngel1.gitty.presentation.common.recyclerview.delegate_adapter.core.Item
import kotlinx.android.synthetic.main.item_button.view.*

data class ButtonItem(
    override val id: String,
    val text: String,
    val onClicked: Callback<Unit>
) : Item<ButtonItem>() {

    override val layoutId: Int
        get() = R.layout.item_button

    override fun bind(view: View) = with(view) {
        text_button.text = text

        this.setOnClickListener {
            onClicked.listener.invoke()
        }
    }

    override fun recycle(view: View) = with(view) {
        super.recycle(view)
        this.setOnClickListener(null)
    }

}