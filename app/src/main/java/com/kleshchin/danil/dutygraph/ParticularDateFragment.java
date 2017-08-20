package com.kleshchin.danil.dutygraph;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Calendar;

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
    /*private List<Integer> thirtyOne = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    private List<Integer> thirty = Arrays.asList(4, 6, 9, 11);*/


    TextView tvDate;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;
    RadioButton radioButton4;
    CalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_particular_date, container, false);
        if (container != null) {
            context_ = container.getContext();
        }
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(new OnTextViewClickListener());
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) view.findViewById(R.id.rBmorning);
        radioButton2 = (RadioButton) view.findViewById(R.id.rBday);
        radioButton3 = (RadioButton) view.findViewById(R.id.rBfHoliday);
        radioButton4 = (RadioButton) view.findViewById(R.id.rBsHoliday);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rBmorning:
                        tvDate.setVisibility(View.VISIBLE);
                        calendarView.setVisibility(VideoView.VISIBLE);
                        duty = 1;
                        break;
                    case R.id.rBday:
                        tvDate.setVisibility(View.VISIBLE);
                        calendarView.setVisibility(VideoView.VISIBLE);
                        duty = 2;
                        break;
                    case R.id.rBfHoliday:
                        tvDate.setVisibility(View.VISIBLE);
                        calendarView.setVisibility(VideoView.VISIBLE);
                        duty = 3;
                        break;
                    case R.id.rBsHoliday:
                        tvDate.setVisibility(View.VISIBLE);
                        calendarView.setVisibility(VideoView.VISIBLE);
                        duty = 4;
                        break;
                }
            }
        });
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
                tvDate.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + dayOfWeek + "\n" + strDuty);
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
            tvDate.setText(myDay + "/" + (myMonth + 1) + "/" + myYear + " это " + dayOfWeek + "\n" + strDuty);
            calendarView.refreshDrawableState();

        }
    };

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
