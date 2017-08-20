package com.kleshchin.danil.dutygraph;

import android.app.DatePickerDialog;
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by Danil Kleshchin on 14.08.2017.
 */
public class ParticularDateFragment extends Fragment {
    @Nullable
    private Context context_;
    Calendar calendar = Calendar.getInstance();
    int myYear = calendar.get(Calendar.YEAR);
    int myMonth = calendar.get(Calendar.MONTH);
    int myDay = calendar.get(Calendar.DAY_OF_MONTH);
    int duty;
    String strDuty = null;
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonthOfYear = calendar.get(Calendar.MONTH);
    int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    private static int popupCheckedItem_ = -1;
    private TextView dutyTextView_;
    private TextView popupDutyPicker_;
    CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_particular_date, container, false);
        if (container != null) {
            context_ = container.getContext();
        }
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

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

            /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rBmorning:
                            dutyTextView_.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(VideoView.VISIBLE);
                            duty = 1;
                            break;
                        case R.id.rBday:
                            dutyTextView_.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(VideoView.VISIBLE);
                            duty = 2;
                            break;
                        case R.id.rBfHoliday:
                            dutyTextView_.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(VideoView.VISIBLE);
                            duty = 3;
                            break;
                        case R.id.rBsHoliday:
                            dutyTextView_.setVisibility(View.VISIBLE);
                            calendarView.setVisibility(VideoView.VISIBLE);
                            duty = 4;
                            break;
                    }
                }
            });*/
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                if (year < currentYear || year == currentYear && month < currentMonthOfYear || year == currentYear && month == currentMonthOfYear && dayOfMonth < currentDayOfMonth) {
                    Toast.makeText(context_, "Это уже прошлое", Toast.LENGTH_LONG).show();
                    return;
                }
                if (year - myYear >= 2) {
                    Toast.makeText(context_, "Слишком далёкое будущее", Toast.LENGTH_LONG).show();
                    return;
                }
                calendarView.refreshDrawableState();
                myYear = year;
                myMonth = month;
                myDay = dayOfMonth;
                String dayOfWeek = null;
                calendar.set(year, month, dayOfMonth);
                calendarView.setDate(calendar.getTimeInMillis(), true, true);
                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.SUNDAY:
                        dayOfWeek = "Воскресенье";
                        break;
                    case Calendar.MONDAY:
                        dayOfWeek = "Понедельник";
                        break;
                    case Calendar.TUESDAY:
                        dayOfWeek = "Вторник";
                        break;
                    case Calendar.WEDNESDAY:
                        dayOfWeek = "Среда";
                        break;
                    case Calendar.THURSDAY:
                        dayOfWeek = "Четверг";
                        break;
                    case Calendar.FRIDAY:
                        dayOfWeek = "Пятница";
                        break;
                    case Calendar.SATURDAY:
                        dayOfWeek = "Суббота";
                }
                CalculateDate calculateDate = new CalculateDate(myYear, myMonth, myDay, currentYear, currentMonthOfYear, currentDayOfMonth, duty);
                switch (calculateDate.Calculate()) {
                    case 1:
                        strDuty = getResources().getString(R.string.morning);
                        break;
                    case 2:
                        strDuty = getResources().getString(R.string.evening);
                        break;
                    case 3:
                        strDuty = getResources().getString(R.string.rest);
                        break;
                    case 4:
                        strDuty = getResources().getString(R.string.holiday);
                        break;
                }
                dutyTextView_.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + dayOfWeek + "\n" + strDuty);
                calendarView.refreshDrawableState();
            }
        });

        return view;
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (year < currentYear ||
                    year == currentYear && monthOfYear < currentMonthOfYear ||
                    year == currentYear && monthOfYear == currentMonthOfYear && dayOfMonth < currentDayOfMonth) {
                Toast.makeText(context_, "Это уже прошлое", Toast.LENGTH_LONG).show();
                return;
            }
            if (year - myYear >= 2) {
                Toast.makeText(context_, "Слишком далёкое будущее", Toast.LENGTH_LONG).show();
                return;
            }
            calendarView.refreshDrawableState();
            myYear = year;
            myMonth = monthOfYear;
            myDay = dayOfMonth;
            String dayOfWeek = null;
            calendar.set(year, monthOfYear, dayOfMonth);
            calendarView.setDate(calendar.getTimeInMillis(), true, true);
            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    dayOfWeek = "Воскресенье";
                    break;
                case Calendar.MONDAY:
                    dayOfWeek = "Понедельник";
                    break;
                case Calendar.TUESDAY:
                    dayOfWeek = "Вторник";
                    break;
                case Calendar.WEDNESDAY:
                    dayOfWeek = "Среда";
                    break;
                case Calendar.THURSDAY:
                    dayOfWeek = "Четверг";
                    break;
                case Calendar.FRIDAY:
                    dayOfWeek = "Пятница";
                    break;
                case Calendar.SATURDAY:
                    dayOfWeek = "Суббота";
            }
            CalculateDate calculateDate = new CalculateDate(myYear, myMonth, myDay, currentYear, currentMonthOfYear, currentDayOfMonth, duty);
            switch (calculateDate.Calculate()) {
                case 1:
                    strDuty = getResources().getString(R.string.morning);
                    break;
                case 2:
                    strDuty = getResources().getString(R.string.evening);
                    break;
                case 3:
                    strDuty = getResources().getString(R.string.rest);
                    break;
                case 4:
                    strDuty = getResources().getString(R.string.holiday);
                    break;
            }
            dutyTextView_.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + dayOfWeek + "\n" + strDuty);
            calendarView.refreshDrawableState();

        }
    };

    private void showCalendar(@NonNull Context context, int dutyNumber_, int year, int month) {
        CaldroidMonthFragment caldroidFragment = new CaldroidMonthFragment();
        caldroidFragment.setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

            }

            @Override
            public void onChangeMonth(int month, int year) {
                /*pickedMonth_ = month;
                pickedYear_ = year;*/
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
                //showCalendar(context, dutyNumber, pickedYear_, pickedMonth_);
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
