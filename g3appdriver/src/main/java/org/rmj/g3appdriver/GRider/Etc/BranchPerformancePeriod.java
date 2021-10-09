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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class BranchPerformancePeriod {
    private static final String TAG = BranchPerformancePeriod.class.getSimpleName();

    public static ArrayList<String> getList() {
        ArrayList<String> loPeriod = new ArrayList<>();
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

            for (int x = 1; x <= lnMonthMax; x++) {
                String lsMonth = x < 10 ? "0" + x : String.valueOf(x);
                String lsPeriod = lnRefYear + lsMonth;
                Log.e(TAG + " Period", lsPeriod);
                loPeriod.add(lsPeriod);
            }

            return loPeriod;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
