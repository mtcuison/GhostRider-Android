package org.rmj.g3appdriver.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class DayCheck {
    private static final String TAG = DayCheck.class.getSimpleName();
    private static final Calendar poCalendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));

    public static boolean isSunday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            return false;
        }
        return true;
    }

    public static boolean isMonday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            return false;
        }
        return true;
    }

    public static boolean isTuesday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
            return false;
        }
        return true;
    }

    public static boolean isWednesday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            return false;
        }
        return true;
    }

    public static boolean isThursday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
            return false;
        }
        return true;
    }

    public static boolean isFriday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
            return false;
        }
        return true;
    }

    public static boolean isSaturday() {
        if(poCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            return false;
        }
        return true;
    }

}
