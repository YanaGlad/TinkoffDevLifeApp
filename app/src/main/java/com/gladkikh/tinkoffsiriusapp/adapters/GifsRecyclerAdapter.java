package com.gladkikh.tinkoffsiriusapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.models.GifModel;
import com.gladkikh.tinkoffsiriusapp.viewmodel.GifViewModel;

import java.util.ArrayList;
import java.util.List;


public class GifsRecyclerAdapter extends RecyclerView.Adapter<GifViewHolder> {

    Context context;
    List<GifModel> gifModels;
    private String type;

    public GifsRecyclerAdapter(Context context, ArrayList<GifModel> gifModels, String type) {
        this.context = context;
        this.gifModels = gifModels;
        this.type = type;
    }

    @NonNull
    @Override
    public GifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.load_item, parent, false);
        return new GifViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull GifViewHolder holder, int position) {
        GifModel gifModel = gifModels.get(position);

        GifViewModel gifViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(type , GifViewModel.class);

        holder.setViewModel(gifViewModel);
        holder.loadImage(gifModel.getGifURL());
        holder.author.setText(context.getString(R.string.by) + " " + gifModel.getAuthor());
        holder.description.setText(gifModel.getDescription());

    }

    @Override
    public int getItemCount() {
        return gifModels.size();
    }


}
