/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMObApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fragment_ObApplication extends Fragment {

    private VMObApplication mViewModel;

    private TextView lblTransNox;
    private RadioGroup rgObType;
    private LinearLayout lnWithLog;
    private TextInputEditText txtDateFrom,
                                txtDateTo,
                                txtTimeInAM,
                                txtTimeOutAM,
                                txtTimeInPM,
                                txtTimeOutPM,
                                txtOverTimeAM,
                                txtOverTimePM;

    public static Fragment_ObApplication newInstance() {
        return new Fragment_ObApplication();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ob_application, container, false);

        lblTransNox = view.findViewById(R.id.lbl_transnox);
        rgObType = view.findViewById(R.id.rg_ObType);
        lnWithLog = view.findViewById(R.id.linear_ObLog);
        txtDateFrom = view.findViewById(R.id.txt_dateFrom);
        txtDateTo = view.findViewById(R.id.txt_dateTo);
        txtTimeInAM = view.findViewById(R.id.txt_timeInAM);
        txtTimeOutAM = view.findViewById(R.id.txt_timeOutAM);
        txtTimeInPM = view.findViewById(R.id.txt_timeInPM);
        txtTimeOutPM = view.findViewById(R.id.txt_timeOutPM);
        txtOverTimeAM = view.findViewById(R.id.txt_overtimeAM);
        txtOverTimePM = view.findViewById(R.id.txt_overtimePM);

        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMObApplication.class);

        rgObType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_withLog) {
                lnWithLog.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rb_withoutLog) {
                lnWithLog.setVisibility(View.GONE);
            }
        });

        txtDateFrom.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    txtDateFrom.setText(dateFormatter.format(newDate.getTime()));
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dateFrom.show();
        });

        txtDateTo.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    txtDateTo.setText(dateFormatter.format(newDate.getTime()));
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dateFrom.show();
        });
    }

}