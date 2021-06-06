package com.gladkikh.tinkoffsiriusapp.fragments;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public abstract class ButtonSupportedFragment extends Fragment implements Clickable {
    protected ExtendedFloatingActionButton btnNex, btnPrev;
    protected View.OnClickListener onNextClickListener, onPrevClickListener;

    public View.OnClickListener getOnNextClickListener() {
        return onNextClickListener;
    }
    public View.OnClickListener getOnPrevClickListener() {
        return onPrevClickListener;
    }

}
