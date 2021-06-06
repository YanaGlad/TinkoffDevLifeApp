package com.gladkikh.tinkoffsiriusapp.adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.viewmodel.GifViewModel;


public class GifViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    AppCompatImageView image;
    AppCompatTextView description, author;
    private GifViewModel viewModel;
    private ProgressBar progressBar;
    private LinearLayoutCompat toolbar;
    private AssetManager assetManager;
    private static ErrorHandler errorHandler = new ErrorHandler();

    private RequestListener<GifDrawable> requestListener = new RequestListener<GifDrawable>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
            errorHandler = new ErrorHandler();
            errorHandler.setImageError();
            viewModel.setError(errorHandler);
            return false;
        }

        @Override
        public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
            if (viewModel != null) {
                viewModel.setIsGifLoaded(true);
                errorHandler.setSuccess();
                viewModel.setError(errorHandler);
            } else {
                errorHandler.setImageError();
                viewModel.setError(errorHandler);
            }
            return false;
        }
    };

    public GifViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.assetManager = context.getAssets();

        image = itemView.findViewById(R.id.rnd_image);
        description = itemView.findViewById(R.id.rnd_description);
        author = itemView.findViewById(R.id.rnd_author);
        progressBar = itemView.findViewById(R.id.rnd_progressbar);
        toolbar = itemView.findViewById(R.id.rnd_toolbar);
    }


    private void prepareForLoading() {
        if (viewModel != null)
            viewModel.setIsGifLoaded(false);
        image.setImageResource(R.color.disabled_btn);
    }


    public void setupRecycleErrorParams(AssetManager assetManager) {
        prepareForLoading();
//            Glide.with(context)
//                    .load(Support.loadBitmapImage(assetManager, "devnull.png"))
//                    .into(image);
        progressBar.setVisibility(View.GONE);
        description.setText(R.string.no_internet);
        author.setText(":(");

    }

    public void loadImage(String url) {
        prepareForLoading();

        Glide.with(context)
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(requestListener)
                .placeholder(R.drawable.waiting_background)
                .into(image);
    }

    public void setViewModel(GifViewModel viewModel) {
        this.viewModel = viewModel;


        viewModel.getIsCurrentGifLoaded().observe((LifecycleOwner) context, isLoaded -> {
            if (isLoaded)
                progressBar.setVisibility(View.GONE);
            else
                progressBar.setVisibility(View.VISIBLE);
        });

        viewModel.getError().observe((LifecycleOwner) context, error -> {
            if (error.getCurrentError().equals(ErrorHandler.success())) {
                //  toolbar.setVisibility(View.GONE);
                if (error.getCurrentError().equals(ErrorHandler.imageError())) {
                    setupRecycleErrorParams(assetManager);
                }
            } else {
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }


}