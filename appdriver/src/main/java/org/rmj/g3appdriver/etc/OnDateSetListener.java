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

package org.rmj.g3appdriver.etc;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class OnDateSetListener implements TextWatcher {
    private final TextInputEditText inputEditText;

    public OnDateSetListener(TextInputEditText editText){
        this.inputEditText = editText;
    }

    Calendar cal = Calendar.getInstance();
    String current = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals(current)) {
            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

            int cl = clean.length();
            int sel = cl;
            for (int i = 2; i <= cl && i < 6; i += 2) {
                sel++;
            }
            //Fix for pressing delete next to a forward slash
            if (clean.equals(cleanC)) sel--;

            if (clean.length() < 8){
                String mmddyyyy = "MMDDYYYY";
                clean = clean + mmddyyyy.substring(clean.length());
            }else{
                //This part makes sure that when we finish entering numbers
                //the date is correct, fixing it otherwise
                int day  = Integer.parseInt(clean.substring(2,4));
                int mon  = Integer.parseInt(clean.substring(0,2));
                int year = Integer.parseInt(clean.substring(4,8));

                mon = mon < 1 ? 1 : Math.min(mon, 12);
                cal.set(Calendar.MONTH, mon-1);
                year = (year<1900)?1900: Math.min(year, Calendar.getInstance().get(Calendar.YEAR));
                cal.set(Calendar.YEAR, year);
                // ^ first set year for the line below to work correctly
                //with leap years - otherwise, date e.g. 29/02/2012
                //would be automatically corrected to 28/02/2012

                day = Math.min(day, cal.getActualMaximum(Calendar.DATE));
                clean = String.format("%02d%02d%02d", mon, day, year);
            }

            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                    clean.substring(2, 4),
                    clean.substring(4, 8));

            sel = Math.max(sel, 0);
            current = clean;
            inputEditText.setText(current);
            inputEditText.setSelection(Math.min(sel, current.length()));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
