package com.example.tinkoffsiriusapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.tinkoffsiriusapp.models.Gif;


class GifDiffCallback extends DiffUtil.ItemCallback<Gif> {
    @Override
    public boolean areItemsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
        return oldItem.equals(newItem);
    }
}
