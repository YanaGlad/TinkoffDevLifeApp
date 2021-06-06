package com.gladkikh.tinkoffsiriusapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gladkikh.tinkoffsiriusapp.R;
import com.gladkikh.tinkoffsiriusapp.fragments.ButtonSupportedFragment;
import com.gladkikh.tinkoffsiriusapp.fragments.MainFragment;
import com.gladkikh.tinkoffsiriusapp.fragments.RandomFragment;


public class MyPagerAdapter extends FragmentPagerAdapter {

    private RandomFragment randomFragment;
    private MainFragment mainFragment1, mainFragment2;
    private Context context;

    public MyPagerAdapter(Context context, FragmentManager manager) {
        super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }



    public ButtonSupportedFragment getCurrentFragment() {
        if (randomFragment.isOnScreen())
            return randomFragment;

        if (mainFragment1.isOnScreen())
            return mainFragment1;

        if (mainFragment2.isOnScreen())
            return mainFragment2;

        return null;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d("PagerAdapterPos", "POS: " + position);

        if (randomFragment.isOnScreen() && randomFragment != object) {
            randomFragment.setOnScreen(false);
        }

        if (mainFragment1.isOnScreen() && mainFragment1 != object) {
            mainFragment1.setOnScreen(false);
        }

        if (mainFragment2.isOnScreen() && mainFragment2 != object) {
            mainFragment2.setOnScreen(false);
        }

        switch (position) {
            case 0:
                randomFragment.setOnScreen(true);
                break;
            case 1:
                mainFragment1.setOnScreen(true);
                break;
            case 2:
                mainFragment2.setOnScreen(true);
                break;
        }

        super.setPrimaryItem(container, position, object);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                randomFragment = new RandomFragment();
                return randomFragment;
            case 1:
                mainFragment1 = MainFragment.newInstance("latest");
                return mainFragment1;
            case 2:
                mainFragment2 = MainFragment.newInstance("top");
                return mainFragment2;

        }

        Log.e("PagerAdapter getItem", "Error at position" + position);

        return new RandomFragment();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.random);
            case 1:
                return context.getString(R.string.latest);
            case 2:
                return context.getString(R.string.top);
        }
        Log.e("PagerAdapter getItem", "Error at position" + position);

        return context.getString(R.string.random);

    }

    @Override
    public int getCount() {
        return 3;
    }
}