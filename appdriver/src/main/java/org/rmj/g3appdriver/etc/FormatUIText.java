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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class FormatUIText {

    @SuppressLint("SimpleDateFormat")
    public static String getDateUIFormat(String date){
        try {
            Date parseDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return new SimpleDateFormat("MMMM dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toSqlValue(String date){
        try {
            Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(date);
            return new SimpleDateFormat("yyyy-MM-dd").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String logTimeToSimpleDate(String date){
        try {
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("yyyy-MM-dd").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date pass date string in format MM/dd/yyyy
     * @return returns tring value of MMMM dd, yyyy
     *
     *
     */
    @SuppressLint("SimpleDateFormat")
    public static String getBirthDateUIFormat(String date){
        try {
            Date parseDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
            return new SimpleDateFormat("MMMM dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date
     * @return
     *
     * this method parses date
     * from yyyy-MM-dd HH:mm:ss Format to MMMM dd, yyyy
     */
    public static String getParseDate(String date){
        try {
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("MMMM dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date
     * @return
     *
     * this method parses datetime from local database
     * a user friendly intervention...
     */
    public static String getParseDateTime(String date){
        try {
            if (date != null) {
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
                return new SimpleDateFormat("MMMM dd, yyyy hh:mm aa").format(parseDate);
            } else {
                return "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date pass the date to be parse
     * This is use to parse birthdate for user interface.
     * Date format from yyyy-MM-dd to MMMM dd, yyyy
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatGOCasBirthdate(String date){
        try {
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("MMMM dd, yyyy").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date pass the date to be parse
     * This is use to parse birthdate for user interface.
     * Date format from MMMM dd, yyyy to yyyy-MM-dd
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTextToData(String date){
        try {
            Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(date);
            return new SimpleDateFormat("yyyy-MM-dd").format(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * @param date pass the date with MMMM dd, yyyy HH:mm aa format
     *             to be parse into yyyyMMdd. This parsing is use in
     *             naming file.
     * @return
     */
    public static String getParseUIDateTime(String date){
        try{
            Date parseDate = new SimpleDateFormat("MMMM dd, yyyy HH:mm aa").parse(date);
            return new SimpleDateFormat("yyyyMMdd").format(parseDate);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static String getAbbreviationOfMonthAndDayFormat(String date){
        try{
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("MMM dd").format(parseDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    public static String FormatSenderMessageDateTime(String date){
        try{
            Date parseDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
            return new SimpleDateFormat("MMM dd").format(parseDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String formatTime_HHMMSS_to_HHMMAA(String date){
        try{
            Date parseDate = new SimpleDateFormat("HH:mm:ss").parse(date);
            return new SimpleDateFormat("HH:mm aa").format(parseDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String HHMMSS_TO_HHMMA_12(String date){
        try{
            Date parseDate = new SimpleDateFormat("HH:mm:ss").parse(date);
            return new SimpleDateFormat("hh:mm a").format(parseDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrencyUIFormat(String price){
        String lsResult = "₱ 0.00";
        try {
            DecimalFormat currency_total = new DecimalFormat("₱ ###,###,###.##");
            BigDecimal loBigDecimal = new BigDecimal(price);
            lsResult = currency_total.format(loBigDecimal);
            return lsResult;
        } catch (Exception e){
            e.printStackTrace();
            return lsResult;
        }
    }

    public static double getParseDouble(String value) {
        try {
            String downPrice = value.replaceAll(",", "");
            return Double.parseDouble(downPrice);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static long getParseLong(String value){
        try{
            double ldValue = Double.parseDouble(value.replace(",", ""));
            return (Double.valueOf(ldValue)).longValue();
        } catch (Exception e){
            e.printStackTrace();
            return Long.valueOf("0");
        }
    }

    public static int getParseInt(String value){
        try {
            return Integer.parseInt(value.replace(",", ""));
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    /**
     *
     * @param value
     * @param position
     * @return value requires the input value from
     * the edittext, while position requires the
     * selected value from the spinner
     */
    public static double getParseBusinessLength(String value, int position){
        try{
            if(position == 0) {
                double ldValue = Double.parseDouble(value);
                return ldValue / 12;
            } else {
                return Double.parseDouble(value);
            }
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static String dbPeriodToUI(String val){
        try{
            Date parseDate = new SimpleDateFormat("yyyyMM").parse(val);
            return new SimpleDateFormat("MMMM yyyy").format(parseDate);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    public static class CurrencyFormat implements TextWatcher{

        private TextInputEditText textInputEditText;

        public CurrencyFormat(TextInputEditText textInputEditText){
            this.textInputEditText = textInputEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            FormatCurrency(textInputEditText);
        }

        private void FormatCurrency(TextInputEditText txt){
            try
            {
                txt.removeTextChangedListener(this);
                String value = txt.getText().toString();

                if (!value.equals(""))
                {

                    if(value.startsWith(".")){
                        txt.setText("0.");
                    }
                    if(value.startsWith("0") && !value.startsWith("0.")){
                        txt.setText("");

                    }


                    String str = txt.getText().toString().replaceAll(",", "");
                    txt.setText(getDecimalFormattedString(str));
                    txt.setSelection(txt.getText().toString().length());
                }
                txt.addTextChangedListener(this);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                txt.addTextChangedListener(this);
            }

        }
    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = new StringBuilder(".");
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString();
            }
            if (i == 3)
            {
                str3.insert(0, ",");
                i = 0;
            }
            str3.insert(0, str1.charAt(k));
            i++;
        }
    }
}
