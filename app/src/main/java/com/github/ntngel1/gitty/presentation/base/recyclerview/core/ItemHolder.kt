package com.github.ntngel1.gitty.presentation.base.recyclerview.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.ntngel1.gitty.presentation.base.recyclerview.core.Item

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: Item<*>? = null
}