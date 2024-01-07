package com.kleshchin.danil.dutygraph;

/**
 * Created by Danil Kleshchin on 18.08.2017.
 */
public class CaldroidMonthFragment extends CaldroidFragment {
    @Override
    public CaldroidMonthAdapter getNewDatesGridAdapter(int month, int year) {
        return new CaldroidMonthAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }
}
