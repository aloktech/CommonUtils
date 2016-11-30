/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.common.utils;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author Pintu
 */
@Builder
@Getter
public class Scheduler {

    /**
     * Specifies one or more seconds with in a second.
     */
    private final String milliSecond;

    /**
     * Specifies one or more seconds with in a minute.
     */
    private final String second;

    /**
     * Specifies one or more minutes with an hour.
     */
    private final String minute;

    /**
     * Specifies one or more hours within a day.
     */
    private final String hour;

    /**
     * Specifies one or more days within a month.
     */
    private final String dayOfMonth;

    /**
     * Specifies one or more months within a year.
     */
    private final String month;

    /**
     * Specifies one or more days within a week.
     */
    private final String dayOfWeek;

    /**
     * Specifies one or more years.
     */
    private final String year;

    /**
     * Specifies the time zone within which the schedule is evaluated. Time
     * zones are specified as an ID string. The set of required time zone IDs is
     * defined by the Zone Name(TZ) column of the public domain zoneinfo
     * database.
     * <p>
     * If a timezone is not specified, the schedule is evaluated in the context
     * of the default timezone associated with the contianer in which the
     * application is executing.
     */
    private final String timezone;

    /**
     * Specifies an information string that is associated with the timer
     */
    private final String info;

    /**
     * Specifies whether the timer that is created is persistent.
     */
    private final boolean persistent;

    private Scheduler(String milliSecond, String second, String minute, String hour, String dayOfMonth,
            String month, String dayOfWeek, String year, String timezone, String info, boolean persistent) {
        this.milliSecond = milliSecond == null ? "0" : milliSecond;
        this.second = second == null ? "0" : second;
        this.minute = minute == null ? "0" : minute;
        this.hour = hour == null ? "0" : hour;
        this.dayOfMonth = dayOfMonth == null ? "0" : dayOfMonth;
        this.month = month == null ? "0" : month;
        this.dayOfWeek = dayOfWeek == null ? "0" : dayOfWeek;
        this.year = year == null ? "0" : year;
        this.timezone = timezone == null ? "" : timezone;
        this.info = info == null ? "" : info;
        this.persistent = persistent;
    }

    /**
     *
     * @return
     */
    public long getDelayed() {
        long time = 0, interval = 1000L;
        if (!getMilliSecond().equals("0")) {
            time += Long.parseLong(getMilliSecond());
        }
        if (!getSecond().equals("0")) {
            time += interval * Long.parseLong(getSecond());
        }
        if (!getMinute().equals("0")) {
            time += interval * 60 * Long.parseLong(getMinute());
        }
        if (!getHour().equals("0")) {
            time += interval * 60 * 60 * Long.parseLong(getHour());
        }
        return time;
    }

    public static class SchedulerBuilder {

        public SchedulerBuilder() {
        }
    }

}
