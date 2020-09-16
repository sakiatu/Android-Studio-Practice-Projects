package com.pietheta.alarmclock.Alarm;

import android.text.format.DateFormat;

import java.util.Calendar;

public class TimeFormatter {
    public static String formatTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return DateFormat.format("hh:mm a", calendar).toString();
    }

    public static String formatDate(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return DateFormat.format("dd.MM.yyyy", calendar).toString();
    }

    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return formatTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return formatDate(calendar.get(Calendar.DATE), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
    }
}
