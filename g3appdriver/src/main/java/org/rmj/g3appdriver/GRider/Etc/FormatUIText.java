package org.rmj.g3appdriver.GRider.Etc;

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
    public String getDateUIFormat(String date){
        try {
            Date parseDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
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
    public String getParseDate(String date){
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
    public String getParseDateTime(String date){
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
    public String formatGOCasBirthdate(String date){
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
     * @param date pass the date with MMMM dd, yyyy HH:mm aa format
     *             to be parse into yyyyMMdd. This parsing is use in
     *             naming file.
     * @return
     */
    public String getParseUIDateTime(String date){
        try{
            Date parseDate = new SimpleDateFormat("MMMM dd, yyyy HH:mm aa").parse(date);
            return new SimpleDateFormat("yyyyMMdd").format(parseDate);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public String getCurrencyUIFormat(String price){
        try {
            DecimalFormat currency_total = new DecimalFormat("₱ ###,###,###.###");
            BigDecimal loBigDecimal = new BigDecimal(price);
            return currency_total.format(loBigDecimal);
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public double getParseDouble(String value) {
        try {
            String downPrice = value.replaceAll(",", "");
            return Double.parseDouble(downPrice);
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public long getParseLong(String value){
        try{
            double ldValue = Double.parseDouble(value.replace(",", ""));
            return (Double.valueOf(ldValue)).longValue();
        } catch (Exception e){
            e.printStackTrace();
            return Long.valueOf("0");
        }
    }

    public int getParseInt(String value){
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
    public double getParseBusinessLength(String value, int position){
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
