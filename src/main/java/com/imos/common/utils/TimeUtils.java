/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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

    public String getTimeWithDate(String timeDateFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public String getTimeWithDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public String getCurrentDate(String dateFormat) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-mm-yyyy"));
    }

    public String getCurrentTime(String timeFormat) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(timeFormat));
    }

    public String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
    }

    public String getCurrentTimeWithDate(String timeDateFormat) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(timeDateFormat));        
    }
    public String getCurrentTimeWithDate() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public String getCurrentTimeOnPreviousDate(String timeDateFormat) {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public String getCurrentTimeOnPreviousDate() {
        return LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
    }

    public String getCurrentTimeOnNextDate(String timeDateFormat) {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern(timeDateFormat));
    }

    public String getCurrentTimeOnNextDate() {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("hh:mm:ss a dd-mm-yyyy"));
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
