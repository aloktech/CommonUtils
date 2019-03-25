/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.inject.Inject;

/**
 *
 * @author Alok
 */
public class TimeUtils {

    private final Calendar INSTANCE = GregorianCalendar.getInstance();

    public static final String UNDER_SCORE = "_";
    public static final String SLASH = "/";
    public static final String COLON = ":";

    @Inject
    public TimeUtils() {
    }

    public String getTimeWithDate() {

        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        getCurrentTime(builder);
        builder.append(COLON);
        builder.append(INSTANCE.get(Calendar.DAY_OF_MONTH));
        builder.append(SLASH);
        builder.append(INSTANCE.get(Calendar.MONTH));
        builder.append(SLASH);
        builder.append(INSTANCE.get(Calendar.YEAR));

        return builder.toString();
    }

    public String getCurrentDate() {
        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        getCurrentTime(builder);
        builder.append(COLON);
        builder.append(INSTANCE.get(Calendar.DAY_OF_MONTH));
        builder.append(SLASH);
        builder.append(INSTANCE.get(Calendar.MONTH));
        builder.append(SLASH);
        builder.append(INSTANCE.get(Calendar.YEAR));

        return builder.toString();
    }

    public String getCurrentTime() {
        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        getCurrentTime(builder);

        return builder.toString();
    }

    public String getCurrentTimeWithDate() {
        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        builder.append(INSTANCE.get(Calendar.DAY_OF_MONTH));
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.MONTH) + 1);
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.YEAR));
        builder.append(UNDER_SCORE);
        getCurrentTime(builder);

        return builder.toString();
    }

    public String getCurrentTimeWithPreviousDate() {
        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        builder.append(INSTANCE.get(Calendar.DAY_OF_MONTH) - 1);
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.MONTH) + 1);
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.YEAR));
        builder.append(UNDER_SCORE);
        getCurrentTime(builder);

        return builder.toString();
    }

    public String getCurrentTimeWithAfterDate() {
        StringBuilder builder = new StringBuilder();
        INSTANCE.setTimeInMillis(System.currentTimeMillis());
        builder.append(INSTANCE.get(Calendar.DAY_OF_MONTH) + 1);
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.MONTH) + 1);
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.YEAR));
        builder.append(UNDER_SCORE);
        getCurrentTime(builder);

        return builder.toString();
    }

    private void getCurrentTime(StringBuilder builder) {
        builder.append(INSTANCE.get(Calendar.HOUR_OF_DAY));
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.MINUTE));
        builder.append(UNDER_SCORE);
        builder.append(INSTANCE.get(Calendar.SECOND));
    }

    public long getDelayed(Scheduler scheduler) {
        long time = 0, interval = 1000L;
        if (!scheduler.getMilliSecond().equals("0")) {
            time += Long.parseLong(scheduler.getMilliSecond());
        }
        if (!scheduler.getSecond().equals("0")) {
            time += interval * Long.parseLong(scheduler.getSecond());
        }
        if (!scheduler.getMinute().equals("0")) {
            time += interval * 60 * Long.parseLong(scheduler.getMinute());
        }
        if (!scheduler.getHour().equals("0")) {
            time += interval * 60 * 60 * Long.parseLong(scheduler.getHour());
        }
        return time;
    }
}
