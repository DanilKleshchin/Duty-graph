package com.kleshchin.danil.dutygraph;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    //Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
    }

    private void initToolbar() {
        //toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        //setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new MonthFragment(), getString(R.string.whole_month));
        viewPagerAdapter.addFragments(new ParticularDateFragment(), getString(R.string.particular_date));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment page = getSupportFragmentManager()
                        .findFragmentByTag("android:switcher:" + R.id.viewpager + ":" +
                                viewPager.getCurrentItem());
                if (position == 1 && page != null) {
                    page.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
