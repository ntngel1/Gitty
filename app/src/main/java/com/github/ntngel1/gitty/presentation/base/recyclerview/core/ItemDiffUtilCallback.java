package com.github.ntngel1.gitty.presentation.base.recyclerview.core;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.github.ntngel1.gitty.presentation.base.recyclerview.core.Item;

import java.util.List;

public class ItemDiffUtilCallback extends DiffUtil.Callback {

    private final List<Item> oldItems;
    private final List<Item> newItems;

    ItemDiffUtilCallback(List<Item> oldItems, List<Item> newItems) {
        this.oldItems = oldItems;
        this.newItems = newItems;
    }

    @Override
    public int getOldListSize() {
        return oldItems.size();
    }

    @Override
    public int getNewListSize() {
        return newItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Item oldItem = oldItems.get(oldItemPosition);
        Item newItem = newItems.get(newItemPosition);

        return oldItem.getViewType() == newItem.getViewType() &&
                oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Item oldItem = oldItems.get(oldItemPosition);
        Item newItem = newItems.get(newItemPosition);

        return newItem.areContentsTheSame(oldItem);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return oldItems.get(oldItemPosition);
    }
}