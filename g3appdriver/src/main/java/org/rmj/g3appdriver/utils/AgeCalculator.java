/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator {

    public static int getAge(String BirthDate){
        int years = 0;
        int month = 0;
        int day = 0;
        try{
            @SuppressLint("SimpleDateFormat") Date parseDate = new SimpleDateFormat("mm/dd/yyyy").parse(BirthDate);
            @SuppressLint("SimpleDateFormat")String birthdate = new SimpleDateFormat("dd/mm/yyyy").format(parseDate);
            @SuppressLint("SimpleDateFormat")SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
            Date birthDate = sdf.parse(birthdate);

            Calendar birthday = Calendar.getInstance();
            birthday.setTimeInMillis(birthDate.getTime());

            long currentTime = System.currentTimeMillis();
            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(currentTime);

            years = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
            int currentMonth = now.get(Calendar.MONTH) + 1;
            int birthMonth = birthday.get(Calendar.MONTH) + 1;

            month = currentMonth - birthMonth;

            if(month < 0){
                years--;
                month = 12 - birthMonth + currentMonth;
                if(now.get(Calendar.DATE) < birthday.get(Calendar.DATE))
                    month--;
            } else if(month == 0 && now.get(Calendar.DATE) < birthday.get(Calendar.DATE)){
                years--;
                month = 11;
            }

            //Calculate the days
            if (now.get(Calendar.DATE) > birthday.get(Calendar.DATE))
                day = now.get(Calendar.DATE) - birthday.get(Calendar.DATE);
            else if (now.get(Calendar.DATE) < birthday.get(Calendar.DATE))
            {
                int today = now.get(Calendar.DAY_OF_MONTH);
                now.add(Calendar.MONTH, -1);
                day = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH) + today;
            }
            else {
                month = 0;
                if (month == 12) {
                    years++;
                    month = 0;
                }
            }

            return years;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
