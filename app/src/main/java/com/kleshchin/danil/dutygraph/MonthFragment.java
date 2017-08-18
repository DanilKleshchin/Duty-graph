package com.kleshchin.danil.dutygraph;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
public class MonthFragment extends Fragment {
    TextView monthName_;
    MonthAdapter adapter_;
    RecyclerView recyclerView_;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        recyclerView_ = (RecyclerView) view.findViewById(R.id.month_fragment_recycler_view);
        monthName_ = (TextView) view.findViewById(R.id.month_name);
        final int monthNumber = Calendar.getInstance().get(Calendar.MONTH);
        final String[] months = getResources().getStringArray(R.array.months);
        monthName_.setText(months[monthNumber]);
        monthName_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (container != null) {
                    showDialog(container.getContext(), months, monthNumber);
                }
            }
        });
        if (container != null) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(container.getContext(), 7);
            recyclerView_.setLayoutManager(layoutManager);
        }
        int days = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        adapter_ = new MonthAdapter(days);
        recyclerView_.setAdapter(adapter_);
        return view;
    }

    private void showDialog(@NonNull Context context, @NonNull final String[] months, int monthNumber) {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle(R.string.month_picker);
        dialog.setContentView(R.layout.dialog_pick_month);
        Button b1 = (Button) dialog.findViewById(R.id.set_button);
        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.month_picker);
        np.setMaxValue(months.length - 1);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setValue(monthNumber);
        np.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return months[value];
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthName_.setText(months[np.getValue()]);
                int days = Calendar.getInstance().getCr(Calendar.FEBRUARY);
                adapter_.setDayInMonthCount(days);
                adapter_.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
