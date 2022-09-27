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

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.lib.PetManager.EmployeeLeave;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMLeaveApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Fragment_LeaveApplication extends Fragment {

    private VMLeaveApplication mViewModel;
    private EmployeeLeave.LeaveApplication poLeave;
    private TextView lblTransNox, lblUsername, lblPosition, lblBranch;
    private Spinner spnType;
    private TextInputEditText txtDateFrom, txtDateTo, txtNoDays, txtRemarks;
    private MaterialButton btnSubmit;

    private LoadDialog poProgress;
    private MessageBox poMessage;

    private long mLastClickTime = 0;

    public static Fragment_LeaveApplication newInstance() {
        return new Fragment_LeaveApplication();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_application, container, false);

        lblUsername = view.findViewById(R.id.lbl_username);
        lblPosition = view.findViewById(R.id.lbl_userPosition);
        lblBranch = view.findViewById(R.id.lbl_userBranch);

        lblTransNox = view.findViewById(R.id.lbl_transnox);
        spnType = view.findViewById(R.id.spn_leaveType);
        txtDateFrom = view.findViewById(R.id.txt_dateFrom);
        txtDateTo = view.findViewById(R.id.txt_dateTo);
        txtNoDays = view.findViewById(R.id.txt_noOfDays);
        txtRemarks = view.findViewById(R.id.txt_remarks);
        btnSubmit = view.findViewById(R.id.btn_submit);

        poProgress = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());

        mViewModel = new ViewModelProvider(this).get(VMLeaveApplication.class);
        poLeave = new EmployeeLeave.LeaveApplication();
//        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
//            try{
//                lblUsername.setText(eEmployeeInfo.getUserName());
//                lblPosition.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
//                poLeave.setEmploName(eEmployeeInfo.getUserName());
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//        });

        mViewModel.getLeaveTypeList().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnType.setAdapter(stringArrayAdapter));

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try{
                lblBranch.setText(eBranchInfo.getBranchNm());
                poLeave.setBranchNme(eBranchInfo.getBranchNm());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        txtDateFrom.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (viewx, year, month, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    if(!txtDateTo.getText().toString().isEmpty()){
                        Date dateTo = dateFormatter.parse(Objects.requireNonNull(txtDateTo.getText()).toString());
                        String lsFrom = dateFormatter.format(newDate.getTime());
                        Date dateFrom1 = dateFormatter.parse(lsFrom);
                        int result = dateFrom1.compareTo(dateTo);
                        if(result <= 0) {
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
                @SuppressLint("SetTextI18n") final DatePickerDialog dateFrom = new DatePickerDialog(getActivity(), (viewx, year, month, dayOfMonth) -> {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, month, dayOfMonth);
                    try {
                        Date dateFrom1 = dateFormatter.parse(Objects.requireNonNull(txtDateFrom.getText()).toString());
                        Date dateTo = newDate.getTime();
                        assert dateFrom1 != null;
                        int result = dateTo.compareTo(dateFrom1);
                        if (result >= 0) {
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

        btnSubmit.setOnClickListener(v -> {
            long time = SystemClock.elapsedRealtime() - mLastClickTime;
            if(time < 5000) {
                Toast.makeText(requireContext(), "Please wait...", Toast.LENGTH_LONG).show();
            } else {
                mLastClickTime = SystemClock.elapsedRealtime();
                poLeave.setLeaveType(String.valueOf(spnType.getSelectedItemPosition()));
                poLeave.setDateFromx(Objects.requireNonNull(txtDateFrom.getText()).toString());
                poLeave.setDateThrux(Objects.requireNonNull(txtDateTo.getText()).toString());
                String lsNoDays = Objects.requireNonNull(txtNoDays.getText()).toString();
                int lnNoDays = 0;
                if (!lsNoDays.trim().isEmpty()) {
                    lnNoDays = Integer.parseInt(lsNoDays);
                }
                poLeave.setNoOfDaysx(lnNoDays);
                poLeave.setRemarksxx(Objects.requireNonNull(txtRemarks.getText()).toString());
                mViewModel.SaveApplication(poLeave, new VMLeaveApplication.LeaveApplicationCallback() {
                    @Override
                    public void OnSave(String Title, String message) {
                        poProgress.initDialog(Title, message, false);
                        poProgress.show();
                    }

                    @Override
                    public void OnSuccess(String message) {
                        poProgress.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Leave Application");
                        poMessage.setMessage(message);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                            spnType.setSelection(0);
                            txtDateFrom.setText("");
                            txtDateTo.setText("");
                            txtNoDays.setText("");
                            txtRemarks.setText("");
                            requireActivity().finish();
                        });
                        poMessage.show();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poProgress.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Leave Application");
                        poMessage.setMessage(message);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }
                });
            }
        });
        return view;
    }
}