package com.github.ntngel1.gitty.presentation.base.recyclerview.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

    @NonNull
    private ArrayList<Item> items = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = items.get(position);

        item.bind(holder.itemView);
        holder.setItem(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Item newItem = items.get(position);
            Item previousItem = (Item) payloads.get(payloads.size() - 1);

            newItem.bind(previousItem, holder.itemView);
            holder.setItem(newItem);
        }
    }

    @Override
    public void onViewRecycled(@NonNull ItemHolder holder) {
        holder.getItem().recycle(holder.itemView);
        holder.setItem(null);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public void setItems(@NonNull List<Item> newItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ItemDiffUtilCallback(items, newItems));

        diffResult.dispatchUpdatesTo(this);

        items.clear();
        items.addAll(newItems);
    }

    public List<Item> getItems() {
        return items;
    }
}
