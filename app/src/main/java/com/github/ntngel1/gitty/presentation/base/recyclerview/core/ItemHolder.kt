/*
 * Copyright (c) 5.4.2020
 * This file created by Kirill Shepelev (aka ntngel1)
 * ntngel1@gmail.com
 */

package com.github.ntngel1.gitty.presentation.base.recyclerview.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: Item<*>? = null
}