package com.gladkikh.tinkoffsiriusapp.fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.api.Api;
import com.gladkikh.tinkoffsiriusapp.api.Instance;
import com.gladkikh.tinkoffsiriusapp.models.Gif;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.viewmodel.RandomFragmentViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RandomFragment extends ButtonSupportedFragment implements Clickable {

    private boolean isOnScreen = false;

    public boolean isOnScreen() {
        return isOnScreen;
    }

    public void setOnScreen(boolean onScreen) {
        isOnScreen = onScreen;
    }

    private RandomFragmentViewModel randomFragmentViewModel;
    private AppCompatImageView image;
    private LinearLayoutCompat toolbar;
    private AppCompatTextView title;
    private AppCompatTextView subtitle;
    private String savedUrl;
    private static ErrorHandler errorHandler = new ErrorHandler();


    private Callback<Gif> gifCallback = new Callback<Gif>() {
        @Override
        public void onResponse(@NonNull Call<Gif> call, Response<Gif> response) {
            if (response.isSuccessful()) {
                if (Objects.requireNonNull(randomFragmentViewModel.getError().getValue()).getCurrentError().equals(ErrorHandler.loadError()))
                    errorHandler.setSuccess();
                    randomFragmentViewModel.setAppError(errorHandler);

                if (response.body() != null) {
                    randomFragmentViewModel.addGifModel(response.body().createGifModel());
                    loadGifWithGlide(response.body().createGifModel().getGifURL());
                }

            } else {
                errorHandler.setLoadError();
                randomFragmentViewModel.setAppError(errorHandler);
                Log.e("Callback error", "Can't load post");
            }
        }

        @Override
        public void onFailure(@NonNull Call<Gif> call, @NonNull Throwable t) {
            errorHandler.setLoadError();
            randomFragmentViewModel.setAppError(errorHandler);
            Log.e("Callback error", "onFailure: " + t.toString());
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomFragmentViewModel = new ViewModelProvider(this).get(RandomFragmentViewModel.class);
        Api api = Instance.getInstance().create(Api.class);
        api.getRandomGif().enqueue(gifCallback);

        onPrevClickListener = v -> {
            if (!randomFragmentViewModel.goBack())
                Log.e("Cache is empty", "No cached gifs");
            else
                loadGifWithGlide(Objects.requireNonNull(Objects.requireNonNull(randomFragmentViewModel.getCurrentGif().getValue()).getGifURL()));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        onNextClickListener = v -> {
            loadGif();
        };

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);

        image = view.findViewById(R.id.load_image);
        toolbar = view.findViewById(R.id.load_linear_layout);
        title = view.findViewById(R.id.load_description);
        subtitle = view.findViewById(R.id.load_author);
        ProgressBar loadProgress = view.findViewById(R.id.load_progressbar);

        btnPrev = requireActivity().findViewById(R.id.btn_previous);
        btnNex = getActivity().findViewById(R.id.btn_next);

        btnPrev.setOnClickListener(onPrevClickListener);
        btnNex.setOnClickListener(onNextClickListener);

        Button errorButton = view.findViewById(R.id.recycl_error_btn);
        ProgressBar errorProgressBar = view.findViewById(R.id.recycle_error_progressbar);

        title.setText("");
        subtitle.setText("");

        randomFragmentViewModel.getCurrentGif().observe(getViewLifecycleOwner(), gif -> {
            if (gif != null) {
                title.setText(gif.getDescription());
                subtitle.setText(view.getContext().getString(R.string.by) + gif.getAuthor());
            }
        });

        randomFragmentViewModel.getIsCurrentGifLoaded().observe(getViewLifecycleOwner(),
                isLoaded -> {
                    if (isLoaded)
                        loadProgress.setVisibility(View.INVISIBLE);
                    else
                        loadProgress.setVisibility(View.VISIBLE);
                });

         randomFragmentViewModel.getCanLoadNext().observe(getViewLifecycleOwner(), (Boolean enabled) -> {
            if (isOnScreen)
                btnNex.setEnabled(enabled);
        });

        randomFragmentViewModel.getCanLoadPrevious().observe(getViewLifecycleOwner(), enabled -> {
            if (isOnScreen)
                btnPrev.setEnabled(enabled);
        });


        randomFragmentViewModel.getError().observe(getViewLifecycleOwner(), e -> {
            randomFragmentViewModel.updateCanLoadPrevious();

            errorProgressBar.setVisibility(View.INVISIBLE);

            if (!e.getCurrentError().equals(ErrorHandler.success())) {
                toolbar.setVisibility(View.GONE);

                switch (e.getCurrentError()) {
                    case "LOAD_ERROR":
                        errorButton.setVisibility(View.VISIBLE);
                        break;

                    case "IMAGE_ERROR":
                        errorButton.setVisibility(View.VISIBLE);
                        setupErrorParams(getContext(), getContext().getAssets());
                        randomFragmentViewModel.setCanLoadNext(false);
                        break;
                }

                errorButton.setOnClickListener(v -> {
                    if (errorProgressBar.getVisibility() == View.INVISIBLE) {
                        errorProgressBar.setVisibility(View.VISIBLE);
                        if (e.getCurrentError().equals(ErrorHandler.imageError())) {
                            loadGifWithGlide(savedUrl);
                        }
                    }
                });
            } else {
                errorButton.setVisibility(View.GONE);
                errorProgressBar.setVisibility(View.GONE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }


    private void loadGifWithGlide(String url) {
        randomFragmentViewModel.setIsCurrentGifLoaded(false);
        randomFragmentViewModel.setCanLoadNext(false);

        Glide.with(requireActivity())
                .asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.waiting_background)

                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        errorHandler.setImageError();
                        savedUrl = (String) model;
                        randomFragmentViewModel.setAppError(errorHandler);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (Objects.requireNonNull(randomFragmentViewModel.getError().getValue()).getCurrentError().equals(ErrorHandler.imageError())){
                            errorHandler.setSuccess();
                            randomFragmentViewModel.setAppError(errorHandler);
                        }

                        randomFragmentViewModel.setIsCurrentGifLoaded(true);
                        randomFragmentViewModel.setCanLoadNext(true);
                        return false;
                    }
                })
                .into(image);
    }



    public void setupErrorParams(Context context, AssetManager assetManager) {
        image.setImageResource(R.color.disabled_btn);

//        Glide.with(context)
//                .load(Support.loadBitmapImage(assetManager, "devnull.png"))
//                .into(image);

        title.setText(R.string.no_internet);
        subtitle.setText(":(");

    }

    private void loadGif() {
        if (!randomFragmentViewModel.goNext()) {
            randomFragmentViewModel.setCanLoadNext(false);
            Api api = Instance.getInstance().create(Api.class);
            api.getRandomGif().enqueue(gifCallback);
        } else
            loadGifWithGlide(Objects.requireNonNull(randomFragmentViewModel.getCurrentGif().getValue()).getGifURL());

    }

    @Override
    public boolean nextEnabled() {
        return randomFragmentViewModel.getCanLoadNext().getValue();
    }

    @Override
    public boolean previousEnabled() {
        return randomFragmentViewModel.getCanLoadPrevious().getValue();
    }

}