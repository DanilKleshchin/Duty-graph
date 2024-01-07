package com.kleshchin.danil.dutygraph;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
public class ParticularDateFragment extends Fragment {

    @Nullable
    private Context context_;
    private Calendar calendar_ = Calendar.getInstance();
    int myYear = calendar_.get(Calendar.YEAR);
    int myMonth = calendar_.get(Calendar.MONTH);
    int myDay = calendar_.get(Calendar.DAY_OF_MONTH);
    int currentYear = calendar_.get(Calendar.YEAR);
    int currentMonthOfYear = calendar_.get(Calendar.MONTH);
    int currentDayOfMonth = calendar_.get(Calendar.DAY_OF_MONTH);
    private String strDuty_ = null;
    private static int popupCheckedItem_ = -1;
    private static int dutyNumber_;
    private TextView dutyTextView_;
    private TextView popupDutyPicker_;
    private CardView dutyLayout_;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_particular_date, container, false);
        if (container != null) {
            context_ = container.getContext();
        }
        dutyLayout_ = (CardView) view.findViewById(R.id.duty_layout);
        popupDutyPicker_ = (TextView) view.findViewById(R.id.pop_duty_picker_particular);
        popupDutyPicker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context_ != null) {
                    showPopupMenu(v, context_);
                }
            }
        });
        dutyTextView_ = (TextView) view.findViewById(R.id.duty_type_text);
        dutyTextView_.setOnClickListener(new OnTextViewClickListener());
        return view;
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (!isCorrectDate(year, monthOfYear, dayOfMonth)) {
                return;
            }
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, myYear);
            cal.set(Calendar.MONTH, myMonth);
            cal.set(Calendar.DAY_OF_MONTH, myDay);
            if (context_ != null) {
                showCalendar(context_, cal.getTime());
            }
            calendar_.set(year, monthOfYear, dayOfMonth);
            obtainStrDuty();
            dutyTextView_.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + "\n" + strDuty_);
        }
    };

    private void showCalendar(@NonNull final Context context, @Nullable Date date) {
        final CaldroidMonthFragment caldroidFragment = new CaldroidMonthFragment();
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                caldroidFragment.setBackgroundDrawableForDate(ContextCompat.getDrawable(context, R.drawable.cell_border_black), date);
                caldroidFragment.refreshView();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                myYear = calendar.get(Calendar.YEAR);
                myMonth = calendar.get(Calendar.MONTH);
                myDay = calendar.get(Calendar.DAY_OF_MONTH);
                if (!isCorrectDate(myYear, myMonth, myDay)) {
                    return;
                }
                obtainStrDuty();
                dutyTextView_.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + "\n" + strDuty_);
            }
        });
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            caldroidFragment.setBackgroundDrawableForDate(ContextCompat.getDrawable(context, R.drawable.cell_border_black), date);
            Bundle args = new Bundle();
            cal.setTime(date);
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
            caldroidFragment.refreshView();
        } else {
            Bundle args = new Bundle();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
        }
        caldroidFragment.refreshView();
        FragmentTransaction t = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        t.replace(R.id.placeholder_particular, caldroidFragment);
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
                switch (item.getItemId()) {
                    case R.id.duty_morning_pop:
                        setPopupItemChecked(0, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.morning));
                        popupCheckedItem_ = 0;
                        dutyNumber_ = 1;
                        break;
                    case R.id.duty_evening_pop:
                        setPopupItemChecked(1, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.evening));
                        popupCheckedItem_ = 1;
                        dutyNumber_ = 2;
                        break;
                    case R.id.rest_pop:
                        setPopupItemChecked(2, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.rest));
                        popupCheckedItem_ = 2;
                        dutyNumber_ = 3;
                        break;
                    case R.id.holiday_pop:
                        setPopupItemChecked(3, 4, popupMenu);
                        popupDutyPicker_.setText(context.getString(R.string.holiday));
                        popupCheckedItem_ = 3;
                        dutyNumber_ = 4;
                        break;
                    default:
                        return false;
                }
                showCalendar(context, null);
                dutyLayout_.setVisibility(View.VISIBLE);
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

    private void obtainStrDuty() {
        CalculateDate calculateDate = new CalculateDate(myYear, myMonth, myDay, currentYear, currentMonthOfYear, currentDayOfMonth, dutyNumber_);
        switch (calculateDate.Calculate()) {
            case 1:
                strDuty_ = getResources().getString(R.string.morning);
                break;
            case 2:
                strDuty_ = getResources().getString(R.string.evening);
                break;
            case 3:
                strDuty_ = getResources().getString(R.string.rest);
                break;
            case 4:
                strDuty_ = getResources().getString(R.string.holiday);
                break;
        }
    }

    private boolean isCorrectDate(int year, int monthOfYear, int dayOfMonth) {
        if (year < currentYear ||
                year == currentYear && monthOfYear < currentMonthOfYear ||
                year == currentYear && monthOfYear == currentMonthOfYear && dayOfMonth < currentDayOfMonth) {
            Toast.makeText(context_, R.string.past_time, Toast.LENGTH_LONG).show();
            return false;
        }
        if (year - myYear >= 2) {
            Toast.makeText(context_, R.string.far_far_future, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private class OnTextViewClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (context_ != null) {
                DatePickerDialog datePicker = new DatePickerDialog(context_, myCallBack, myYear, myMonth, myDay);
                datePicker.setCancelable(true);
                datePicker.setTitle(getString(R.string.select_date));
                datePicker.show();
            }
        }
    }
}
