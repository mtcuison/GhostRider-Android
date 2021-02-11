package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int initialYear = -1;
    private int initialMonth = -1;
    private int initialDay = -1;
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
    public void setListener(DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        this.listener = listener;
        this.initialYear = year;
        this.initialMonth = month;
        this.initialDay = day;
    }
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener, year, month, day);
        return fragment;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Initial selected value
        if (initialYear == -1)
            initialYear = year - 18;

        if (initialMonth == -1)
            initialMonth = c.get(Calendar.MONTH);

        if (initialDay == -1)
            initialDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), listener, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
//        return new DatePickerDialog(getActivity(), listener, year, month, day);

    }

}