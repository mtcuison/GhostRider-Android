/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 10/6/21, 4:59 PM
 * project file last modified : 10/6/21, 4:59 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.BullsEye;

import android.util.Log;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchPerformance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class PerformancePeriod {
    private static final String TAG = PerformancePeriod.class.getSimpleName();

    public static ArrayList<String> getList() {
        ArrayList<String> loPeriod = new ArrayList<>();
        Calendar loCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
        SimpleDateFormat loFormatx = new SimpleDateFormat("yyyyMM");

        int lnMontCom = loCalendr.getInstance().get(Calendar.MONTH) + 1;
        int lnCurYear = loCalendr.getInstance().get(Calendar.YEAR);
        String lsPastMos = String.valueOf(lnCurYear) + lnMontCom;

        try {
            loCalendr.setTime(loFormatx.parse(lsPastMos));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 12; i++) {
            String lsMonthYr = loFormatx.format(loCalendr.getTime());
            loPeriod.add(lsMonthYr);
            loCalendr.add(Calendar.MONTH, -1);
        }

        return loPeriod;
    }

    public static String getLatestCompletePeriod() {
        try {
            Calendar loCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
            final int MONTH_MIN = 1;
            int lnMontNow = loCalendr.getInstance().get(Calendar.MONTH) + 1;
            int lnMonthMax;
            int lnRefYear;

            if(lnMontNow == MONTH_MIN) {
                lnRefYear = loCalendr.getInstance().get(Calendar.YEAR) - 1;
                lnMonthMax = 12; // Up to December
            } else {
                lnRefYear = loCalendr.getInstance().get(Calendar.YEAR);
                lnMonthMax = lnMontNow - 1; // Set previous Month
            }

            String lsMonth = lnMonthMax < 10 ? "0" + lnMonthMax : String.valueOf(lnMonthMax);
            String lsPeriod = lnRefYear + lsMonth;
            Log.e(TAG + " Period", lsPeriod);
            return lsPeriod;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getPeriodText(String fsPeriodx) {
        String lsYearNox = fsPeriodx.substring(0,4);
        String lsMonthNo = "";
        String lsMonthNm = "";


        if (fsPeriodx.length() > 2)
        {
            lsMonthNo = fsPeriodx.substring(fsPeriodx.length() - 2);
        }
        else
        {
            lsMonthNo = fsPeriodx;
        }

        switch(lsMonthNo) {
            case "01":
                lsMonthNm = "January";
                break;
            case "02":
                lsMonthNm = "February";
                break;
            case "03":
                lsMonthNm = "March";
                break;
            case "04":
                lsMonthNm = "April";
                break;
            case "05":
                lsMonthNm = "May";
                break;
            case "06":
                lsMonthNm = "June";
                break;
            case "07":
                lsMonthNm = "July";
                break;
            case "08":
                lsMonthNm = "August";
                break;
            case "09":
                lsMonthNm = "September";
                break;
            case "10":
                lsMonthNm = "October";
                break;
            case "11":
                lsMonthNm = "November";
                break;
            case "12":
                lsMonthNm = "December";
                break;
            default:
                lsMonthNm = "";
                break;
        }

        return lsMonthNm + " " + lsYearNox;
    }

    public static ArrayList<String> getSortedPeriodList(ArrayList<String> foList) {
        if(foList.size() > 0) {

            ArrayList<String> loList = new ArrayList<>();
            for(int x = foList.size() - 1; x >= 0 ; x--) {
                loList.add(foList.get(x));
            }

            return loList;

        } else {
            return null;
        }
    }

    public static String[] getAreaTableLabel(List<EAreaPerformance> foList) {
        List<String> loList = new ArrayList<>();

        for(int x = 0; x < foList.size(); x++) {
            loList.add(parseDateLabel(foList.get(x).getPeriodxx()));
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;

    };

    public static String[] parseAreaPeriodicPerformance(List<DAreaPerformance.PeriodicPerformance> foList) {
        List<String> loList = new ArrayList<>();

        for(int x = 0; x < foList.size(); x++) {
            loList.add(parseDateLabel(foList.get(x).sPeriodxx));
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;
    };


    public static String[] parseBranchPeriodicPerformance(List<DBranchPerformance.PeriodicalPerformance> foList) {
        List<String> loList = new ArrayList<>();

        for(int x = 0; x < foList.size(); x++) {
            loList.add(parseDateLabel(foList.get(x).Period));
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;
    };

    public static String[] getBranchTableLabel(List<EBranchPerformance> foList) {
        List<String> loList = new ArrayList<>();

        for(int x = 0; x < foList.size(); x++) {
            loList.add(parseDateLabel(foList.get(x).getPeriodxx()));
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;

    };

    public static String parseDateLabel(String fsDate) {
        if(!"".equalsIgnoreCase(fsDate)) {
            String lsMonth = fsDate.substring(4, 6);
            String lsYear = fsDate.substring(0, 4);

            switch (lsMonth) {
                case "01":
                    return "Jan " + lsYear;
                case "02":
                    return "Feb " + lsYear;
                case "03":
                    return "Mar " + lsYear;
                case "04":
                    return "Apr " + lsYear;
                case "05":
                    return "May " + lsYear;
                case "06":
                    return "Jun " + lsYear;
                case "07":
                    return "Jul " + lsYear;
                case "08":
                    return "Aug " + lsYear;
                case "09":
                    return "Sep " + lsYear;
                case "10":
                    return "Oct " + lsYear;
                case "11":
                    return "Nov " + lsYear;
                case "12":
                    return "Dec " + lsYear;
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}
