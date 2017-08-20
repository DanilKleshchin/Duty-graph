
package com.kleshchin.danil.dutygraph;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
public class MonthFragment extends Fragment {
    private static int popupCheckedItem_ = -1;
    private static int pickedMonth_;
    private static int pickedYear_;
    private TextView popupDutyPicker_;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        if (container != null) {
            final Context context = container.getContext();
            popupDutyPicker_ = (TextView) view.findViewById(R.id.pop_duty_picker_month);
            popupDutyPicker_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, context);
                }
            });
            Calendar cal = Calendar.getInstance();
            pickedYear_ = cal.get(Calendar.YEAR);
            pickedMonth_ = cal.get(Calendar.MONTH) + 1;
            showCalendar(context, -1, pickedYear_, pickedMonth_);
        }
        return view;
    }

    private void showCalendar(@NonNull Context context, int dutyNumber_, int year, int month) {
        CaldroidMonthFragment caldroidFragment = new CaldroidMonthFragment();
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

            }

            @Override
            public void onChangeMonth(int month, int year) {
                pickedMonth_ = month;
                pickedYear_ = year;
            }
        });
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.MONTH, month);
        args.putInt(CaldroidFragment.YEAR, year);
        caldroidFragment.setArguments(args);
        Map<String, Object> extraData = caldroidFragment.getExtraData();
        extraData.put("DUTY", dutyNumber_);
        caldroidFragment.refreshView();
        FragmentTransaction t = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        t.replace(R.id.month_calendar, caldroidFragment);
        t.commit();
    }

    private void showPopupMenu(@NonNull View v, @NonNull final Context context) {
        final PopupMenu popupMenu = new PopupMenu(context, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
        setPopupItemChecked(popupCheckedItem_, 4, popupMenu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int dutyNumber;
                switch (item.getItemId()) {
                    case R.id.duty_morning_pop:
                        setPopupItemChecked(0, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.morning));
                        popupCheckedItem_ = 0;
                        dutyNumber = 1;
                        break;
                    case R.id.duty_evening_pop:
                        setPopupItemChecked(1, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.evening));
                        popupCheckedItem_ = 1;
                        dutyNumber = 2;
                        break;
                    case R.id.rest_pop:
                        setPopupItemChecked(2, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.rest));
                        popupCheckedItem_ = 2;
                        dutyNumber = 3;
                        break;
                    case R.id.holiday_pop:
                        setPopupItemChecked(3, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.holiday));
                        popupCheckedItem_ = 3;
                        dutyNumber = 4;
                        break;
                    default:
                        return false;
                }
                showCalendar(context, dutyNumber, pickedYear_, pickedMonth_);
                popupMenu.dismiss();
                return true;
            }
        });
        popupMenu.show();
    }

    private void setPopupItemChecked(int position, int itemsCount, @NonNull PopupMenu popupMenu) {
        if (position > -1 && position <= itemsCount) {
            for (int i = 0; i < itemsCount; ++i) {
                popupMenu.getMenu().getItem(i).setChecked(false);
            }
            popupMenu.getMenu().getItem(position).setChecked(true);
        }
    }
}
