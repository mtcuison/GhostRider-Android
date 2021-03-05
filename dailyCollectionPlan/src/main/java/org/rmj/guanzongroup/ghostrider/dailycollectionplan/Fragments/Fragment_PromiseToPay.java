package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.ViewModelCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Fragment_PromiseToPay extends Fragment implements ViewModelCallback {

    private VMPromiseToPay mViewModel;
    private TextInputLayout tilBranchName;
    private TextInputEditText ptpDate, ptpCollName;
    private AutoCompleteTextView ptpBranchName;
    private MaterialButton btnPtp;
    private RadioGroup rgPtpAppUnit;
    private ImageView btnCamera;
    private ImageFileCreator poImage;
    private EImageInfo poImageInfo;

    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo, lblImgPath;
    private String IsAppointmentUnitX = "", CollId;
    private PromiseToPayModel infoModel;
    private String lsDate = "";

    //Parameters From Activity_Transaction
    private String TransNox, Remarksx, AccntNox;
    private int EntryNox;
    private MessageBox poMessage;

    public static Fragment_PromiseToPay newInstance() {
        return new Fragment_PromiseToPay();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promise_to_pay, container, false);
        infoModel = new PromiseToPayModel();
        poMessage = new MessageBox(getContext());
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v) {
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);

        lblAccNo = v.findViewById(R.id.tvAccountNo);
        lblClientNm = v.findViewById(R.id.tvClientname);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);

        lblImgPath = v.findViewById(R.id.tvImgPath);

        tilBranchName = v.findViewById(R.id.til_ptp_branchName);
        ptpDate = v.findViewById(R.id.pToPayDate);
        ptpBranchName = v.findViewById(R.id.txt_ptp_branchName);
        rgPtpAppUnit = v.findViewById(R.id.rb_ap_ptpBranch);
        ptpCollName = v.findViewById(R.id.txt_ptp_collectorName);
        btnPtp = v.findViewById(R.id.btn_ptp_submit);
//        btnCamera = v.findViewById(R.id.imgPtpCamera);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initialize Parameters...
        Remarksx = Activity_Transaction.getInstance().getRemarksCode();
        TransNox = Activity_Transaction.getInstance().getTransNox();
        EntryNox = Activity_Transaction.getInstance().getEntryNox();
        AccntNox = Activity_Transaction.getInstance().getAccntNox();

        mViewModel = new ViewModelProvider(this).get(VMPromiseToPay.class);
        mViewModel.setParameter(TransNox, EntryNox, Remarksx);
        mViewModel.setViewPtpBranch().observe(getViewLifecycleOwner(), integer -> tilBranchName.setVisibility(integer));
        mViewModel.getPtpDate().observe(getViewLifecycleOwner(), date -> ptpDate.setText(date));
        mViewModel.getCollectionMaster().observe(getViewLifecycleOwner(), s ->  {
            CollId = s.getCollctID();
            ptpCollName.setText(s.getCollName());
            poImage = new ImageFileCreator(getActivity(), DCP_Constants.FOLDER_NAME, DCP_Constants.TRANSACT_PTP, AccntNox);
        });

        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblAccNo.setText(collectionDetail.getAcctNmbr());
                lblClientNm.setText(collectionDetail.getFullName());
                lblTransNo.setText(collectionDetail.getTransNox());
                mViewModel.setCurrentCollectionDetail(collectionDetail);
                mViewModel.setAccountNox(collectionDetail.getAcctNmbr());
                Log.e("", "Account No collection detail " + collectionDetail.getAcctNmbr() + " Account Instance" + AccntNox);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getAllBranchNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            ptpBranchName.setAdapter(adapter);
        });
        ptpBranchName.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getAllBranchInfo().observe(getViewLifecycleOwner(), eBranchInfos -> {
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

        ptpDate.setOnClickListener(v ->  {
            Log.e("remarks ", Remarksx);
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog  StartTime = new DatePickerDialog(getActivity(), (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                lsDate = dateFormatter.format(newDate.getTime());
                ptpDate.setText(lsDate);
                mViewModel.setPsPtpDate(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            StartTime.show();
        });
        rgPtpAppUnit.setOnCheckedChangeListener(new OnDependencyStatusSelectionListener(rgPtpAppUnit,mViewModel));
        btnPtp.setOnClickListener( v -> {
            try {
                submitPtp(Remarksx);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        // TODO: Use the ViewModel
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("REQUEST CODE", String.valueOf(requestCode));
        Log.e("RESULT CODE", String.valueOf(resultCode));
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                try {
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(infoModel.getPtpImgPath()));
                    mViewModel.saveImageInfo(poImageInfo);
                    submitPtp(Remarksx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //cameraCapture(mCurrentPhotoPath);
                //showDialog();}
            }else {
                infoModel.setPtpImgPath("");
            }
        }
    }
//
    @Override
    public void OnStartSaving() {

    }

    @Override
    public void OnSuccessResult(String[] args) {
        poMessage.initDialog();
        poMessage.setTitle("Transaction Success");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            Objects.requireNonNull(getActivity()).finish();
        });
        poMessage.show();
    }

    @Override
    public void OnFailedResult(String message) {
        if (message.trim().equalsIgnoreCase("empty"))
        {
            showDialogImg();
        }else {
            onFailedDialog(message);
        }
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

    public void submitPtp(String remarks) throws Exception{
        //save ImageInfo...

        //Saving CollectionDetail...
        infoModel.setPtpDate(Objects.requireNonNull(ptpDate.getText().toString()));
        infoModel.setPtpRemarks(remarks);
        infoModel.setPtpAppointmentUnit(IsAppointmentUnitX);
        infoModel.setPtpBranch(ptpBranchName.getText().toString());
        infoModel.setPtpCollectorName(Objects.requireNonNull(ptpCollName.getText()).toString());
        mViewModel.savePtpInfo(infoModel, Fragment_PromiseToPay.this);

    }
    public void showDialogImg(){
        poMessage.initDialog();
        poMessage.setTitle(Remarksx);
        poMessage.setMessage("Please take a selfie in customer's place in order to confirm transaction. \n" +
                "\n" +
                "NOTE: Take a selfie on your current place if customer is not visited");
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            poImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                infoModel.setPtpImgPath(photPath);
                poImageInfo = new EImageInfo();
                poImageInfo.setDtlSrcNo(AccntNox);
                poImageInfo.setSourceNo(TransNox);
                poImageInfo.setSourceCD("DCPa");
                poImageInfo.setImageNme(FileName);
                poImageInfo.setFileLoct(photPath);
                poImageInfo.setFileCode("0020");
                poImageInfo.setLatitude(String.valueOf(latitude));
                poImageInfo.setLongitud(String.valueOf(longitude));
                mViewModel.setLatitude(String.valueOf(latitude));
                mViewModel.setLongitude(String.valueOf(longitude));
                mViewModel.setImgName(FileName);
                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
            });
        });
        poMessage.setNegativeButton("Cancel", (view, dialog) -> {
            dialog.dismiss();
//            Objects.requireNonNull(getActivity()).finish();
        });
        poMessage.show();
    }
    public void onFailedDialog(String messages){
        poMessage.initDialog();
        poMessage.setTitle("Transaction Failed");
        poMessage.setMessage(messages);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
}