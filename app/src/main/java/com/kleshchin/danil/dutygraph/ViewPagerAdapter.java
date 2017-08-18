package com.kleshchin.danil.dutygraph;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> _fragments = new ArrayList<>();
    private ArrayList<String> _tabTitles = new ArrayList<>();
    private Map<Integer, String> tags;
    private FragmentManager fragmentManager;

    public void addFragments(Fragment fragments, String tabTitles) {
        _fragments.add(fragments);
        _tabTitles.add(tabTitles);
    }

    public ViewPagerAdapter(FragmentManager fragmentManager) {
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
        Object object = super.instantiateItem(container, position);
        tags = new HashMap<>();
        if(object instanceof Fragment) {
            Fragment f = (Fragment) object;


            String tag = f.getTag();
            tags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        String tag = tags.get(position);
        if(tag == null) {
            return null;
        }
        return fragmentManager.findFragmentByTag(tag);
    }
}
