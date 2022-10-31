package com.outlet.device.view_assets;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.outlet.device.models.Upload;

public class MyListAdapter extends ListAdapter<Upload, DevicesViewHolder> {

    public MyListAdapter(@NonNull DiffUtil.ItemCallback<Upload> diffCallback) {
        super(diffCallback);
    }
    @Override
    public DevicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DevicesViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(DevicesViewHolder holder, int position) {
        Upload current = getItem(position);
        holder.bind(current);
    }

    static class WordDiff extends DiffUtil.ItemCallback<Upload> {

        @Override
        public boolean areItemsTheSame(@NonNull Upload oldItem, @NonNull Upload newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Upload oldItem, @NonNull Upload newItem) {
            return oldItem.getDatetime().equals(newItem.getDatetime());
        }
    }

}
