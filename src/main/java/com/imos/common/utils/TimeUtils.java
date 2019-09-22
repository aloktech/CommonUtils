/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Alok
 */
public class TimeUtils {

    private final Calendar INSTANCE = GregorianCalendar.getInstance();

    public static final String UNDER_SCORE = "_";
    public static final String SLASH = "/";
    public static final String COLON = ":";

    public static LocalDateTime convertLongTimeToLocalDateTime(long time) {
        return LocalDateTime.ofInstant(new Date(time).toInstant(), ZoneId.systemDefault());
    }

    public static long convertLocalDateTimeToLongTime(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    public static String getTimeWithDate(String timeDateFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public static String getTimeWithDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public static String getCurrentDate(String dateFormat) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy"));
    }

    public static String getCurrentTime(String timeFormat) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }

    public static String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    public static String getCurrentTimeWithDate(String timeDateFormat) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public static String getCurrentTimeWithDate() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public static String getCurrentTimeOnPreviousDate(String timeDateFormat) {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public static String getCurrentTimeOnPreviousDate() {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public static String getCurrentTimeOnNextDate(String timeDateFormat) {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public static String getCurrentTimeOnNextDate() {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public static long getDelayed(Scheduler scheduler) {
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
