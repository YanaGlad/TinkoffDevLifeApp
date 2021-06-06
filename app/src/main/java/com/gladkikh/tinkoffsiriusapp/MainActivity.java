package com.gladkikh.tinkoffsiriusapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gladkikh.tinkoffsiriusapp.adapters.MyPagerAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private MyPagerAdapter myPagerAdapter;
    private ViewPager viewPager;
    private ExtendedFloatingActionButton btnNext, btnPrevious;
    private TabLayout tabLayout;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            btnNext.setOnClickListener(myPagerAdapter.getCurrentFragment().getOnNextClickListener());
            btnPrevious.setOnClickListener(myPagerAdapter.getCurrentFragment().getOnPrevClickListener());
            btnNext.setEnabled(myPagerAdapter.getCurrentFragment().nextEnabled());
            btnPrevious.setEnabled(myPagerAdapter.getCurrentFragment().previousEnabled());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myPagerAdapter = new MyPagerAdapter(getApplicationContext(), getSupportFragmentManager());
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        tabLayout.setupWithViewPager(viewPager);

        btnNext = findViewById(R.id.btn_next);
        btnPrevious = findViewById(R.id.btn_previous);
    }


}