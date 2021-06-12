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
import com.gladkikh.tinkoffsiriusapp.fragments.RecyclerFragment;
import com.gladkikh.tinkoffsiriusapp.fragments.RandomFragment;


public class MyPagerAdapter extends FragmentPagerAdapter {

    private RandomFragment randomFragment;
    private RecyclerFragment recyclerFragment1, recyclerFragment2;
    private Context context;

    public MyPagerAdapter(Context context, FragmentManager manager) {
        super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }



    public ButtonSupportedFragment getCurrentFragment() {
        if (randomFragment.isOnScreen())
            return randomFragment;

        if (recyclerFragment1.isOnScreen())
            return recyclerFragment1;

        if (recyclerFragment2.isOnScreen())
            return recyclerFragment2;

        return null;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.d("PagerAdapterPos", "POS: " + position);

        if (randomFragment.isOnScreen() && randomFragment != object) {
            randomFragment.setOnScreen(false);
        }

        if (recyclerFragment1.isOnScreen() && recyclerFragment1 != object) {
            recyclerFragment1.setOnScreen(false);
        }

        if (recyclerFragment2.isOnScreen() && recyclerFragment2 != object) {
            recyclerFragment2.setOnScreen(false);
        }

        switch (position) {
            case 0:
                randomFragment.setOnScreen(true);
                break;
            case 1:
                recyclerFragment1.setOnScreen(true);
                break;
            case 2:
                recyclerFragment2.setOnScreen(true);
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
                recyclerFragment1 = RecyclerFragment.newInstance("latest");
                return recyclerFragment1;
            case 2:
                recyclerFragment2 = RecyclerFragment.newInstance("top");
                return recyclerFragment2;

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