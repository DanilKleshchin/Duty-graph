package com.kleshchin.danil.dutygraph;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Calendar;
import java.util.Map;

import hirondelle.date4j.DateTime;

/**
 * Created by Danil Kleshchin on 18.08.2017.
 */
class CaldroidMonthAdapter extends CaldroidGridAdapter {

    CaldroidMonthAdapter(Context context, int month, int year, Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cellView = convertView;
        if (convertView == null) {
            cellView = inflater.inflate(R.layout.caldroid_cell, null);
        }

        DateTime dateTime = this.datetimeList.get(position);
        Calendar calendar = Calendar.getInstance();

        TextView dateTextView = (TextView) cellView.findViewById(R.id.caldroid_date);
        TextView dutyTextView = (TextView) cellView.findViewById(R.id.caldroid_duty);
        Integer dutyNumber = (Integer) extraData.get("DUTY");
        if (dateTime.getMonth() != month) {
            dateTextView.setTextColor(resources
                    .getColor(com.caldroid.R.color.caldroid_darker_gray));
        }
        if (dateTime.equals(getToday())) {
            cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
        } else {
            cellView.setBackgroundResource(com.caldroid.R.drawable.cell_bg);
        }
        if (dutyNumber != null && dutyNumber != -1) {

            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonthOfYear = calendar.get(Calendar.MONTH);
            int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int year = dateTime.getYear();
            int monthOfYear = dateTime.getMonth() - 1; //0...11;
            int dayOfMonth = dateTime.getDay();

            if (!checkPastDate(year, monthOfYear, dayOfMonth, currentYear, currentMonthOfYear, currentDayOfMonth)) {
                CalculateDate calculateDate = new CalculateDate(year, monthOfYear, dayOfMonth,
                        currentYear, currentMonthOfYear, currentDayOfMonth, dutyNumber);
                String strDuty = "";
                int colorRes = ContextCompat.getColor(context, android.R.color.white);
                switch (calculateDate.Calculate()) {
                    case 1:
                        strDuty = context.getResources().getString(R.string.calendar_morning);
                        colorRes = ContextCompat.getColor(context, android.R.color.holo_green_dark);
                        break;
                    case 2:
                        strDuty = context.getResources().getString(R.string.calendar_evening);
                        colorRes = ContextCompat.getColor(context, android.R.color.holo_blue_dark);
                        break;
                    case 3:
                        strDuty = context.getResources().getString(R.string.calendar_rest);
                        colorRes = ContextCompat.getColor(context, android.R.color.holo_orange_light);
                        break;
                    case 4:
                        strDuty = context.getResources().getString(R.string.calendar_holiday);
                        colorRes = ContextCompat.getColor(context, android.R.color.holo_red_light);
                        break;
                }
                if ((boolean) extraData.get("COLOR")) {
                    cellView.setBackgroundColor(colorRes);
                } else {
                    dutyTextView.setText(strDuty);
                }
            }
        }
        dateTextView.setText(String.valueOf(dateTime.getDay()));
        setCustomResources(dateTime, cellView, dateTextView);

        return cellView;
    }

    private boolean checkPastDate(int year, int monthOfYear, int dayOfMonth, int currentYear, int currentMonthOfYear, int currentDayOfMonth) {
        boolean b = year < currentYear || year == currentYear && monthOfYear < currentMonthOfYear || year == currentYear && monthOfYear == currentMonthOfYear && dayOfMonth < currentDayOfMonth || year - currentYear >= 2;
        return b;
    }
}
