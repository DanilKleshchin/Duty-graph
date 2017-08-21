package com.kleshchin.danil.dutygraph;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> _fragments = new ArrayList<>();
    private ArrayList<String> _tabTitles = new ArrayList<>();

    void addFragments(Fragment fragments, String tabTitles) {
        _fragments.add(fragments);
        _tabTitles.add(tabTitles);
    }

    ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return _fragments.get(position);
    }

    @Override
    public int getCount() {
        return _fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _tabTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
