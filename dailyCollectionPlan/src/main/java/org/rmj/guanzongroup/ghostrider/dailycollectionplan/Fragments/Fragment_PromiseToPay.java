package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DatePickerFragment;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fragment_PromiseToPay extends Fragment implements ViewModelCallback {

    private VMPromiseToPay mViewModel;
    private TextInputLayout tilBranchName;
    private TextInputEditText ptpDate, ptpCollName;
    private AutoCompleteTextView ptpBranchName;
    private MaterialButton btnPtp;
    private RadioGroup rgPtpAppUnit;

    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo;
    private String IsAppointmentUnitX = "", CollId;
    private PromiseToPayModel infoModel;

    private MessageBox poMessage;
    public static Fragment_PromiseToPay newInstance() {
        return new Fragment_PromiseToPay();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promise_to_pay, container, false);
        infoModel = new PromiseToPayModel();
        poMessage = new MessageBox(getActivity());
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v) {
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);

        lblAccNo = v.findViewById(R.id.tvAccountNo);
        lblClientNm = v.findViewById(R.id.tvClientname);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);

        tilBranchName = v.findViewById(R.id.til_ptp_branchName);
        ptpDate = v.findViewById(R.id.pToPayDate);
        ptpBranchName = v.findViewById(R.id.txt_ptp_branchName);
        rgPtpAppUnit = v.findViewById(R.id.rb_ap_ptpBranch);
        ptpCollName = v.findViewById(R.id.txt_ptp_collectorName);
        btnPtp = v.findViewById(R.id.btn_ptp_submit);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel = new ViewModelProvider(this).get(VMPromiseToPay.class);
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.setViewPtpBranch().observe(getViewLifecycleOwner(), integer -> tilBranchName.setVisibility(integer));
        mViewModel.getPtpDate().observe(getViewLifecycleOwner(), date -> ptpDate.setText(date));
        mViewModel.getCollectionMaster().observe(getViewLifecycleOwner(), s ->  {
            CollId = s.getCollctID();
            ptpCollName.setText(s.getCollName());
        });

        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
            } catch (Exception


                    e){
                e.printStackTrace();
            }
        });

        mViewModel.getAllBranchNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            ptpBranchName.setAdapter(adapter);
        });
        ptpBranchName.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(this, eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(ptpBranchName.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getBranchNm())){
                    mViewModel.setBanchCde(eBranchInfos.get(x).getBranchCd());
                    break;
                }
            }
        }));
        mViewModel.getUserBranchEmployee().observe(getViewLifecycleOwner(), eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddress.setText(eBranchInfo.getAddressx());
        });

        ptpDate.setOnClickListener(v ->  showDatePickerDialog(ptpDate));
        rgPtpAppUnit.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgPtpAppUnit,mViewModel));
        btnPtp.setOnClickListener( v -> submitPtp(Remarksx));
        // TODO: Use the ViewModel
    }
    public void showDatePickerDialog(final TextInputEditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = dateFormat.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                SimpleDateFormat printFormat = new SimpleDateFormat("MMMM dd, yyyy");
                String lsDate = printFormat.format(calendar.getTime());
                mViewModel.setPsPtpDate(lsDate);
                editText.setText(lsDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            getActivity().finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        poMessage.setTitle("Transaction Failed");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

    class OnDependencyStatusSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View rbView;
        VMPromiseToPay vm;

        OnDependencyStatusSelectionListener(View view, VMPromiseToPay vmPromiseToPay){
            this.rbView = view;
            this.vm = vmPromiseToPay;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rb_ap_ptpBranch){
                String appointment = "0";
                if(checkedId == R.id.rb_ptpBranch) {
                    IsAppointmentUnitX = "1";
                    appointment = "1";
                } else {
                    IsAppointmentUnitX = "0";
                    appointment = "0";
                    vm.setBanchCde("");
                }
                vm.setIsAppointmentUnitX(appointment);
            }
        }
    }

    public void submitPtp(String remarks){
        infoModel.setPtpRemarks(remarks);
        infoModel.setPtpAppointmentUnit(IsAppointmentUnitX);
        infoModel.setPtpBranch(ptpBranchName.getText().toString());
        infoModel.setPtpCollectorName(ptpCollName.getText().toString());
        mViewModel.savePtpInfo(infoModel, Fragment_PromiseToPay.this);
    }
}