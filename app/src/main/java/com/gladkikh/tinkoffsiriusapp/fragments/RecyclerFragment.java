package com.gladkikh.tinkoffsiriusapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.api.Api;
import com.gladkikh.tinkoffsiriusapp.api.Instance;
import com.gladkikh.tinkoffsiriusapp.models.Gif;
import com.gladkikh.tinkoffsiriusapp.models.GifModel;
import com.gladkikh.tinkoffsiriusapp.models.Gifs;
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.values.PageOperation;
import com.gladkikh.tinkoffsiriusapp.adapters.GifsRecyclerAdapter;
import com.gladkikh.tinkoffsiriusapp.viewmodel.RecyclerFragmentViewModel;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerFragment extends ButtonSupportedFragment {

    private boolean isOnScreen = false;
    private String type;
    private static ErrorHandler errorHandler = new ErrorHandler();

    public boolean isOnScreen() {
        return isOnScreen;
    }

    public void setOnScreen(boolean onScreen) {
        isOnScreen = onScreen;
    }

    private RecyclerFragmentViewModel recyclerFragmentViewModel;
    private GifsRecyclerAdapter gifsRecyclerAdapter;

    public static RecyclerFragment newInstance(String type) {
        RecyclerFragment fragment = new RecyclerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TAB_TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerFragmentViewModel = new ViewModelProvider(this).get(RecyclerFragmentViewModel.class);
        if (getArguments() != null) {
            type = getArguments().getString("TAB_TYPE");
            recyclerFragmentViewModel.setType(type);
        } else {
            Log.e("TAG_MAIN_FRAG", "No arguments in bundle");
        }
        Log.d("TYPE", type);
        loadGifs(PageOperation.STAND, type);

    }

    private Callback<Gifs> gifsCallback = new Callback<Gifs>() {
        @Override
        public void onResponse(@NonNull Call<Gifs> call, Response<Gifs> response) {
            if (response.isSuccessful()) {
                recyclerFragmentViewModel.setCanLoadNext(true);

                if (response.body() != null) 
                    recyclerFragmentViewModel.createListOfGifModels(response.body().getGifs());

            } else {
                ErrorHandler errorHandler = new ErrorHandler();
                errorHandler.setLoadError();
                recyclerFragmentViewModel.setError(errorHandler);
            }
        }

        @Override
        public void onFailure(@NonNull Call<Gifs> call, @NonNull Throwable t) {
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.setLoadError();
            recyclerFragmentViewModel.setError(errorHandler);
        }
    };


    public void loadGifs(PageOperation pageOperation, String type) {
        recyclerFragmentViewModel.setCanLoadNext(recyclerFragmentViewModel.getError().getValue().getCurrentError().equals(ErrorHandler.success()));
        recyclerFragmentViewModel.setCurrentPage(recyclerFragmentViewModel.getCurrentPage().getValue(), pageOperation);
        Api service = Instance.getInstance().create(Api.class);

        switch (type) {
            case "latest":
                service.getLatestGifs(recyclerFragmentViewModel.getCurrentPage().getValue(), 10, "gif").enqueue(gifsCallback);
                break;
            case "top":
                service.getTopGifs(recyclerFragmentViewModel.getCurrentPage().getValue(), 10, "gif").enqueue(gifsCallback);
                break;
        }
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recycle, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutCompat errorLayout = view.findViewById(R.id.recycl_error_layout);
        AppCompatTextView errorTitle = view.findViewById(R.id.recycl_error_title);
        ProgressBar errorProgressBar = view.findViewById(R.id.recycle_error_progressbar);
        Button errorButton = view.findViewById(R.id.recycl_error_btn);

        btnPrev = requireActivity().findViewById(R.id.btn_previous);
        btnNex = requireActivity().findViewById(R.id.btn_next);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        onNextClickListener = v -> loadGifs(PageOperation.NEXT, type);
        onPrevClickListener = v -> loadGifs(PageOperation.PREVIOUS, type);

        recyclerFragmentViewModel.getGifModels().observe(getViewLifecycleOwner(), gifs -> {
            if (gifs != null) {
                errorHandler.setSuccess();
                recyclerFragmentViewModel.setError(errorHandler);
                gifsRecyclerAdapter = new GifsRecyclerAdapter(getContext(), gifs, recyclerFragmentViewModel.getType().getValue());
                recyclerView.setAdapter(gifsRecyclerAdapter);
            }
        });

        recyclerFragmentViewModel.getError().observe(getViewLifecycleOwner(), e -> {
            recyclerFragmentViewModel.updateCanLoadPrevious();
            recyclerFragmentViewModel.setCanLoadNext(true);

            errorProgressBar.setVisibility(View.INVISIBLE);

            if (!e.getCurrentError().equals(ErrorHandler.success())) {
                recyclerView.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);

                if (e.getCurrentError().equals(ErrorHandler.loadError()))
                    errorTitle.setText(view.getContext().getString(R.string.errorr) + "\n" + view.getContext().getString(R.string.no_internet));


                errorButton.setOnClickListener(v -> {
                    if (errorProgressBar.getVisibility() == View.INVISIBLE) {
                        errorProgressBar.setVisibility(View.VISIBLE);
                        if (e.getCurrentError().equals(ErrorHandler.loadError())) {
                            loadGifs(PageOperation.STAND, type);
                        }
                    }
                });
            } else {
                errorLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        recyclerFragmentViewModel.getCanLoadNext().observe(getViewLifecycleOwner(), enabled -> {
            if (isOnScreen())
                btnNex.setEnabled(enabled);
        });

        recyclerFragmentViewModel.getCanLoadPrevious().observe(getViewLifecycleOwner(), enabled -> {
            if (isOnScreen())
                btnPrev.setEnabled(enabled);
        });

        return view;
    }

    @Override
    public boolean nextEnabled() {
        return recyclerFragmentViewModel.getCanLoadNext().getValue();
    }

    @Override
    public boolean previousEnabled() {
        return recyclerFragmentViewModel.getCanLoadPrevious().getValue();
    }

}