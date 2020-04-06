/*
 * Copyright (c) 7.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.ntngel1.gitty.R
import kotlinx.android.synthetic.main.view_error_stub.view.*

class ErrorStubView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_error_stub, this)
    }

    fun setOnRefreshClickListener(listener: () -> Unit) {
        button_error_stub_refresh.setOnClickListener {
            listener.invoke()
        }
    }
}