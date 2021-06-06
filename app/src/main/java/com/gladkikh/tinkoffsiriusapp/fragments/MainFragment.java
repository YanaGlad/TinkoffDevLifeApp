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
import com.gladkikh.tinkoffsiriusapp.values.ErrorHandler;
import com.gladkikh.tinkoffsiriusapp.values.PageOperation;
import com.gladkikh.tinkoffsiriusapp.adapters.GifsRecyclerAdapter;
import com.gladkikh.tinkoffsiriusapp.viewmodel.RecyclerFragmentViewModel;


public class MainFragment extends ButtonSupportedFragment implements Clickable {

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

    public static MainFragment newInstance(String type) {
        MainFragment fragment = new MainFragment();
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
        }else {
            Log.e("TAG_MAIN_FRAG", "No arguments in bundle");
        }
        Log.d("TYPE", type);
        recyclerFragmentViewModel.loadGifs(PageOperation.STAND, type);

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
        btnNex = getActivity().findViewById(R.id.btn_next);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        onNextClickListener = v -> recyclerFragmentViewModel.loadGifs(PageOperation.NEXT, type);
        onPrevClickListener = v -> recyclerFragmentViewModel.loadGifs(PageOperation.PREVIOUS, type);

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
                            recyclerFragmentViewModel.loadGifs(PageOperation.STAND, type);
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