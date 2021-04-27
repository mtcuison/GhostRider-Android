/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Etc;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.StringTokenizer;

public class TextFormatter {

    public static class OnTextChangedCurrencyFormatter implements TextWatcher{

        private TextInputEditText txtBox;

        public OnTextChangedCurrencyFormatter(TextInputEditText txtBox) {
            this.txtBox = txtBox;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try
            {
                txtBox.removeTextChangedListener(this);
                String value = Objects.requireNonNull(txtBox.getText()).toString();


                if (!value.equals(""))
                {
                    if(value.startsWith(".")){
                        txtBox.setText("0.");
                    }
                    if(value.startsWith("0") && !value.startsWith("0.")){
                        txtBox.setText("");

                    }

                    String str = txtBox.getText().toString().replaceAll(",", "");
                    txtBox.setText(getDecimalFormattedString(str));
                    txtBox.setSelection(txtBox.getText().toString().length());
                }
                txtBox.addTextChangedListener(this);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                txtBox.addTextChangedListener(this);
            }
        }
    }

    public static String getDecimalFormattedString(String value)
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
