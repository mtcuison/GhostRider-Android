package org.rmj.guanzongroup.pacitareward.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.guanzongroup.pacitareward.R;

import java.util.Calendar;

public class Fragment_HistoryEval extends Fragment implements DatePickerDialog.OnDateSetListener {
    private TextInputEditText searchview_fromdate;
    private TextInputEditText searchview_todate;
    private TextInputLayout srchview_fromdtt;
    private TextInputLayout srchview_todtt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_layouthisteval, container, false);

        searchview_fromdate = view.findViewById(R.id.searchview_fromdate);
        searchview_todate = view.findViewById(R.id.searchview_todate);
        srchview_fromdtt = view.findViewById(R.id.srchview_fromdtt);
        srchview_todtt = view.findViewById(R.id.srchview_todtt);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), this, year, month, day);
        datePicker.setCancelable(true);

        searchview_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_fromdate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        searchview_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_todate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        srchview_fromdtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_fromdate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        srchview_todtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_todate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        return view;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
