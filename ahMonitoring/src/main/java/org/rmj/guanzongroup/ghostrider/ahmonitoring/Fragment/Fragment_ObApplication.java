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
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.OBApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMObApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Fragment_ObApplication extends Fragment {

    public static final String TAG = Fragment_ObApplication.class.getSimpleName();
    private VMObApplication mViewModel;

    private TextView lblTransNox, lblUsername, lblPosition, lblBranch;
    private LinearLayout lnWithoutLog;
    private Button btnSubmit;
    private AutoCompleteTextView txtBranchDestination;
    private TextInputEditText txtDateFrom,
                                txtDateTo,
                                txtNoDays,
                                txtRemarks;
    private OBApplication infoModel;
    private LoadDialog poProgress;
    private MessageBox loMessage;

    public static Fragment_ObApplication newInstance() {
        return new Fragment_ObApplication();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ob_application, container, false);
        infoModel = new OBApplication();
        lblUsername = view.findViewById(R.id.lbl_username);
        lblPosition = view.findViewById(R.id.lbl_userPosition);
        lblBranch = view.findViewById(R.id.lbl_userBranch);

        lblTransNox = view.findViewById(R.id.lbl_transnox);
        lnWithoutLog = view.findViewById(R.id.linear_ObWithoutLog);
        txtDateFrom = view.findViewById(R.id.txt_dateFrom);
        txtDateTo = view.findViewById(R.id.txt_dateTo);
        txtNoDays = view.findViewById(R.id.txt_noOfDays);
        btnSubmit = view.findViewById(R.id.btn_submitApplication);
        txtBranchDestination = view.findViewById(R.id.txt_brnDestination);
        txtRemarks = view.findViewById(R.id.txt_remarks);

        poProgress = new LoadDialog(getActivity());
        loMessage = new MessageBox(getActivity());
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMObApplication.class);

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try{
                lblUsername.setText(eEmployeeInfo.getUserName());
                lblPosition.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
                infoModel.setEmployID(eEmployeeInfo.getEmployID());
                infoModel.setTransact(AppConstants.CURRENT_DATE);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try{
                lblBranch.setText(eBranchInfo.getBranchNm());

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDateFrom.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    if(!txtDateTo.getText().toString().isEmpty()){
                        Date dateTo = dateFormatter.parse(Objects.requireNonNull(txtDateTo.getText()).toString());
                        String lsFrom = dateFormatter.format(newDate.getTime());
                        Date dateFrom1 = dateFormatter.parse(lsFrom);
                        int result = dateFrom1.compareTo(dateTo);
                        if(result >= 0) {
                            txtDateFrom.setText(dateFormatter.format(newDate.getTime()));
                            long diff = dateTo.getTime() - dateFrom1.getTime();
                            long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
                            txtNoDays.setText(String.valueOf(noOfDays));
                        } else {
                            GToast.CreateMessage(getActivity(), "Invalid date selected.", GToast.ERROR).show();
                        }
                    } else {
                        txtDateFrom.setText(dateFormatter.format(newDate.getTime()));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            dateFrom.show();
        });

        txtDateTo.setOnClickListener(v -> {
            if(!Objects.requireNonNull(txtDateFrom.getText()).toString().isEmpty()) {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                @SuppressLint("SetTextI18n") final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (view, year, month, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    try {
                        Date dateFrom1 = dateFormatter.parse(Objects.requireNonNull(txtDateFrom.getText()).toString());
                        Date dateTo = newDate.getTime();
                        assert dateFrom1 != null;
                        if (dateFrom1.before(dateTo)) {
                            txtDateTo.setText(dateFormatter.format(newDate.getTime()));
                            long diff = dateTo.getTime() - dateFrom1.getTime();
                            long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
                            txtNoDays.setText(""+ Integer.parseInt(String.valueOf(noOfDays)));
                        } else {
                            GToast.CreateMessage(getActivity(), "Invalid date selected.", GToast.ERROR).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                dateFrom.show();
            } else {
                GToast.CreateMessage(getActivity(), "Select start date first.", GToast.ERROR).show();
            }
        });

        mViewModel.getAllBranchNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtBranchDestination.setAdapter(adapter);
            txtBranchDestination.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtBranchDestination.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(txtBranchDestination.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    infoModel.setDestinat(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));
        btnSubmit.setOnClickListener(v->{
            infoModel.setDateFrom(FormatUIText.formatTextToData(Objects.requireNonNull(txtDateFrom.getText()).toString()));
            infoModel.setDateThru(FormatUIText.formatTextToData(Objects.requireNonNull(txtDateTo.getText()).toString()));
            infoModel.setRemarksx(Objects.requireNonNull(txtRemarks.getText()).toString());
            mViewModel.saveObLeave(infoModel, new VMObApplication.OnSubmitOBLeaveListener() {
                @Override
                public void onSuccess() {
                    poProgress.dismiss();
                    loMessage.initDialog();
                    loMessage.setPositiveButton("Okay", (v, dialog) ->{
                        dialog.dismiss();
                        txtBranchDestination.setText("");
                        txtDateFrom.setText("");
                        txtDateTo.setText("");
                        txtNoDays.setText("");
                        txtRemarks.setText("");
                    });
                    loMessage.setTitle("PET Manager");
                    loMessage.setMessage("Your business trip application has been submitted.");
                    loMessage.show();
                }

                @Override
                public void onFailed(String message) {
                    poProgress.dismiss();
                    loMessage.initDialog();
                    loMessage.setPositiveButton("Okay", (v, dialog) -> dialog.dismiss());
                    loMessage.setTitle("PET Manager");
                    loMessage.setMessage(message);
                    loMessage.show();
                }
            });
        });
    }
}