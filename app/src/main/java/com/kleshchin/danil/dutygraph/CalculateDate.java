package com.kleshchin.danil.dutygraph;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 804307 on 15.08.2016.
 */
class CalculateDate {
    CalculateDate(int year, int monthOfYear, int dayOfMonth, int currentYear, int currentMonthOfYear, int currentDayOfMonth, int duty) {
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        this.currentYear = currentYear;
        this.currentMonthOfYear = currentMonthOfYear;
        this.currentDayOfMonth = currentDayOfMonth;
        this.duty = duty;
    }

    int Calculate()// Must return value between 1 and 4. It's duty.
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentYear, currentMonthOfYear, currentDayOfMonth);
        while (true) {
            if (currentYear == year && currentMonthOfYear == monthOfYear && currentDayOfMonth == dayOfMonth) {
                return duty;
            }
            while (true) {
                if (currentMonthOfYear == 1) {
                    if (calendar.get(Calendar.DAY_OF_YEAR) > 365) {
                        countDuty();
                        if (currentDayOfMonth >= 29) {
                            currentMonthOfYear++;
                            currentDayOfMonth = 0;
                        }
                        currentDayOfMonth++;
                    } else {
                        countDuty();
                        if (currentDayOfMonth >= 28) {
                            currentMonthOfYear++;
                            currentDayOfMonth = 0;
                        }
                        currentDayOfMonth++;
                    }
                } else if (thirty.contains(currentMonthOfYear)) {
                    countDuty();
                    if (currentDayOfMonth >= 30) {
                        currentMonthOfYear++;
                        currentDayOfMonth = 0;
                    }
                    currentDayOfMonth++;
                } else if (thirtyOne.contains(currentMonthOfYear)) {
                    countDuty();
                    if (currentDayOfMonth >= 31) {
                        if (currentMonthOfYear == 11 && currentDayOfMonth == 31) {
                            currentYear++;
                            currentMonthOfYear = 0;
                            currentDayOfMonth = 1;
                            calendar.set(currentYear, currentMonthOfYear, currentDayOfMonth);
                            currentDayOfMonth = 0;

                        } else {
                            currentMonthOfYear++;
                            currentDayOfMonth = 0;
                        }
                    }
                    currentDayOfMonth++;
                }
                if (currentDayOfMonth == dayOfMonth) {
                    if (currentMonthOfYear == monthOfYear) {
                        break;
                    }
                }
            }

        }
    }

    private void countDuty() {
        this.duty++;
        if (this.duty > 4)
            this.duty = 1;
    }

    private int duty;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int currentYear;
    private int currentMonthOfYear;
    private int currentDayOfMonth;
    private List<Integer> thirtyOne = Arrays.asList(0, 2, 4, 6, 7, 9, 11);
    private List<Integer> thirty = Arrays.asList(3, 5, 8, 10);
}
