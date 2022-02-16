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

package org.rmj.g3appdriver.GRider.Etc;

import android.util.Log;

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BranchPerformancePeriod {
    private static final String TAG = BranchPerformancePeriod.class.getSimpleName();

    public static ArrayList<String> getList() {
        ArrayList<String> loPeriod = new ArrayList<>();
        Calendar loCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
        SimpleDateFormat loFormatx = new SimpleDateFormat("yyyyMM");

        int lnMontCom = loCalendr.getInstance().get(Calendar.MONTH);
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
            loList.add(foList.get(x).getPeriodxx());
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;

    };

    public static String[] getBranchTableLabel(List<EBranchPerformance> foList) {
        List<String> loList = new ArrayList<>();

        for(int x = 0; x < foList.size(); x++) {
            loList.add(foList.get(x).getPeriodxx());
        }

        String[] loArray = loList.toArray(new String[0]);

        return loArray;

    };

}
