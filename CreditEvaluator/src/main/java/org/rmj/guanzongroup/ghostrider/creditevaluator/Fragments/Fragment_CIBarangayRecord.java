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

package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;

import java.util.ArrayList;
import java.util.List;
public class Fragment_CIBarangayRecord extends Fragment implements ViewModelCallBack, LifecycleObserver {

    private static final String TAG = Fragment_CIBarangayRecord.class.getSimpleName();
    private VMCIBarangayRecords mViewModel;
    private CIBarangayRecordInfoModel ciModel;
    private RadioGroup rgRecord,rgFeedbak1,rgFeedbak2,rgFeedbak3;
    private TextInputLayout tilRecord,tilFBRemark1,tilFBRemark2,tilFBRemark3;
    private TextInputEditText tieRecord;
    private AutoCompleteTextView tieRel1, tieRel2, tieRel3;
    private TextInputEditText tieFullname1, tieContact1, tieFBRemark1, tieAddress1;
    private TextInputEditText tieFullname2, tieContact2, tieFBRemark2, tieAddress2;
    private TextInputEditText tieFullname3, tieContact3, tieFBRemark3, tieAddress3;
    private MaterialButton btnNext, btnPrevious, btnAdd1, btnAdd2, btnAdd3;

    private TextView sCompnyNm;
    private TextView dTransact;
    private TextView sModelNm;
    private TextView nTerm;
    private TextView nMobile;
    private TextView sTransNox;

    private MessageBox poMessage;

    public static Fragment_CIBarangayRecord newInstance() {
        return new Fragment_CIBarangayRecord();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_barangay_record, container, false);
        poMessage = new MessageBox(getContext());
        ciModel = new CIBarangayRecordInfoModel();
        initWidgets(root);
        initClientInfo();
        return root;
    }
    public void initWidgets(View view){

        rgRecord = view.findViewById(R.id.rg_ci_brgyRecord);
        rgFeedbak1 = view.findViewById(R.id.rg_ci_Feedback1);
        rgFeedbak2 = view.findViewById(R.id.rg_ci_Feedback2);
        rgFeedbak3 = view.findViewById(R.id.rg_ci_Feedback3);
        tilRecord = view.findViewById(R.id.til_ci_record);
        tieRecord = view.findViewById(R.id.tie_ci_record);
//      Neighbor 1
        tieFullname1 = view.findViewById(R.id.tie_ci_neighborName1);
        tieContact1 = view.findViewById(R.id.tie_ci_NeighborContact1);
        tieRel1 = view.findViewById(R.id.tie_ci_neighborRel1);
        tilFBRemark1 = view.findViewById(R.id.til_ci_fbRemark1);
        tieFBRemark1 = view.findViewById(R.id.tie_ci_fbRemark1);
        tieAddress1 = view.findViewById(R.id.tie_ci_address1);
        btnAdd1 = view.findViewById(R.id.btn_ci_add1);

//      Neighbor 2
        tieFullname2 = view.findViewById(R.id.tie_ci_neighborName2);
        tieContact2 = view.findViewById(R.id.tie_ci_NeighborContact2);
        tieRel2 = view.findViewById(R.id.tie_ci_neighborRel2);
        tilFBRemark2 = view.findViewById(R.id.til_ci_fbRemark2);
        tieFBRemark2 = view.findViewById(R.id.tie_ci_fbRemark2);
        tieAddress2 = view.findViewById(R.id.tie_ci_address2);
        btnAdd2 = view.findViewById(R.id.btn_ci_add2);

//      Neighbor 3
        tieFullname3 = view.findViewById(R.id.tie_ci_neighborName3);
        tieContact3 = view.findViewById(R.id.tie_ci_NeighborContact3);
        tieRel3 = view.findViewById(R.id.tie_ci_neighborRel3);
        tilFBRemark3 = view.findViewById(R.id.til_ci_fbRemark3);
        tieFBRemark3 = view.findViewById(R.id.tie_ci_fbRemark3);
        tieAddress3 = view.findViewById(R.id.tie_ci_address3);
        btnAdd3 = view.findViewById(R.id.btn_ci_add3);

        btnNext = view.findViewById(R.id.btn_ciAppNext);
        btnPrevious = view.findViewById(R.id.btn_ciAppPrvs);

        sCompnyNm = view.findViewById(R.id.lbl_ci_applicantName);
        dTransact = view.findViewById(R.id.lbl_ci_applicationDate);
        sModelNm = view.findViewById(R.id.lbl_ci_modelName);
        nTerm = view.findViewById(R.id.lbl_ci_accntTerm);
        nMobile = view.findViewById(R.id.lbl_ci_mobileNo);
        sTransNox = view.findViewById(R.id.lbl_ci_transNox);

    }

    public void initClientInfo(){
        sTransNox.setText(Activity_CIApplication.getInstance().getTransNox());
        sCompnyNm.setText(Activity_CIApplication.getInstance().getsCompnyNm());
        dTransact.setText(Activity_CIApplication.getInstance().getdTransact());
        sModelNm.setText(Activity_CIApplication.getInstance().getsModelNm());
        nTerm.setText(Activity_CIApplication.getInstance().getnTerm());
        nMobile.setText(Activity_CIApplication.getInstance().getnMobile());
    }
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(VMCIBarangayRecords.class);
        // TODO: Use the ViewModel

        mViewModel.setsTransNox(Activity_CIApplication.getInstance().getTransNox());
        mViewModel.getCIByTransNox().observe(getViewLifecycleOwner(), eciEvaluation -> {
            mViewModel.setCurrentCIDetail(eciEvaluation);
        });

        rgRecord.setOnCheckedChangeListener(new ONCIBarangayRecord(rgRecord,mViewModel));
        rgFeedbak1.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak1,mViewModel));
        rgFeedbak2.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak2,mViewModel));
        rgFeedbak3.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak3,mViewModel));
        mViewModel.getAllRelatnDs().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, strings);
            tieRel1.setAdapter(adapter);
            tieRel1.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            tieRel2.setAdapter(adapter);
            tieRel2.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            tieRel3.setAdapter(adapter);
            tieRel3.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        tieRel1.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getRelation().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(tieRel1.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getRelatnDs())){
                    Log.e(TAG, eBranchInfos.get(x).getRelatnID());
                    ciModel.setReltnCD1(eBranchInfos.get(x).getRelatnID());
                    break;
                }else{
                    ciModel.setReltnCD1("");
                }
            }
        }));
        tieRel2.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getRelation().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(tieRel2.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getRelatnDs())){
                    Log.e(TAG, eBranchInfos.get(x).getRelatnID());
                    ciModel.setReltnCD2(eBranchInfos.get(x).getRelatnID());
                    break;
                }
            }
        }));
        tieRel3.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getRelation().observe(getViewLifecycleOwner(), eBranchInfos -> {
            for(int x = 0; x < eBranchInfos.size(); x++){
                if(tieRel3.getText().toString().equalsIgnoreCase(eBranchInfos.get(x).getRelatnDs())){
                    Log.e(TAG, eBranchInfos.get(x).getRelatnID());
                    ciModel.setReltnCD3(eBranchInfos.get(x).getRelatnID());
                    break;
                }
            }
        }));


        btnPrevious.setOnClickListener(v -> {
            Activity_CIApplication.getInstance().moveToPageNumber(1);
        });
        btnAdd1.setOnClickListener(v -> {
            try {
                ciModel.setFBRemrk1(tieFBRemark1.getText().toString());
                ciModel.setNeighbr1(tieFullname1.getText().toString());
                ciModel.setAddress1(tieAddress1.getText().toString());
                ciModel.setMobileN1(tieContact1.getText().toString());
                mViewModel.saveNeighbor(ciModel, "Neighbor1",Fragment_CIBarangayRecord.this);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        btnAdd2.setOnClickListener(v -> {
            try {
                ciModel.setFBRemrk2(tieFBRemark2.getText().toString());
                ciModel.setNeighbr2(tieFullname2.getText().toString());
                ciModel.setAddress2(tieAddress2.getText().toString());
                ciModel.setMobileN2(tieContact2.getText().toString());
                mViewModel.saveNeighbor(ciModel, "Neighbor2",Fragment_CIBarangayRecord.this);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        btnAdd3.setOnClickListener(v -> {
            try {
                ciModel.setFBRemrk3(tieFBRemark3.getText().toString());
                ciModel.setNeighbr3(tieFullname3.getText().toString());
                ciModel.setAddress3(tieAddress3.getText().toString());
                ciModel.setMobileN3(tieContact3.getText().toString());
                mViewModel.saveNeighbor(ciModel, "Neighbor3",Fragment_CIBarangayRecord.this);
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });
        btnNext.setOnClickListener(v -> {
            try {
                if (btnAdd1.getVisibility() != View.GONE){
                    errorMessage("Please fill out neighbor 1 fields and save first!");
                }else if (btnAdd3.getVisibility() != View.GONE){
                    errorMessage("Please fill out neighbor 2 fields and save first!");
                } else if (btnAdd3.getVisibility() != View.GONE){
                    errorMessage("Please fill out neighbor 3 fields and save first!");
                }else{
                    ciModel.setRemRecrd(tieRecord.getText().toString());
                    mViewModel.saveNeighbor(ciModel, "Neighbor",Fragment_CIBarangayRecord.this);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });
        setData();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {
        if (args.equalsIgnoreCase("Neighbor1")){
            btnAdd1.setVisibility(View.GONE);
        }else if (args.equalsIgnoreCase("Neighbor2")){
            btnAdd2.setVisibility(View.GONE);
        }else if (args.equalsIgnoreCase("Neighbor3")){
            btnAdd3.setVisibility(View.GONE);
        }else{
            Activity_CIApplication.getInstance().moveToPageNumber(3);
        }
    }

    @Override
    public void onFailedResult(String message) {
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();


    }

    class ONCIBarangayRecord implements RadioGroup.OnCheckedChangeListener{

        View rbView;
        VMCIBarangayRecords mViewModel;
        ONCIBarangayRecord(View view, VMCIBarangayRecords viewModel)
        {
            this.rbView = view;
            this.mViewModel = viewModel;
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rg_ci_brgyRecord){
                if(checkedId == R.id.rb_ci_noRecord) {
                    ciModel.setHasRecrd("0");
                    tieRecord.setText("");
                    tilRecord.setVisibility(View.GONE);
                }
                else {
                    ciModel.setHasRecrd("1");
                    tilRecord.setVisibility(View.VISIBLE);
                }

            }
            if(rbView.getId() == R.id.rg_ci_Feedback1){
                if(checkedId == R.id.rb_ci_postiveFeed1) {
                    ciModel.setFeedBck1("1");
                }
                else {
                    ciModel.setFeedBck1("0");
                }
            }
            if(rbView.getId() == R.id.rg_ci_Feedback2){
                if(checkedId == R.id.rb_ci_postiveFeed2) {
                    ciModel.setFeedBck2("1");
                }
                else{
                    ciModel.setFeedBck2("0");
                }
            }
            if(rbView.getId() == R.id.rg_ci_Feedback3){
                if(checkedId == R.id.rb_ci_postiveFeed3) {
                    ciModel.setFeedBck3("1");
                }
                else {
                    ciModel.setFeedBck3("0");
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void setData(){
        try {
//            Neighbor 1
            mViewModel.getsNeigbor1().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setNeighbr1(val);
                    tieFullname1.setEnabled(false);
                }
                tieFullname1.setText(val);
            });
            mViewModel.getsRel1().observe(getViewLifecycleOwner(), val ->{
                mViewModel.getRelation().observe(getViewLifecycleOwner(), sReltn->{
                    int relID;
                    if (val.isEmpty()){
                        relID = -1;
                    }else {
                        relID = Integer.parseInt(val);
                    }
                    for (int i = 0; i < sReltn.size(); i++){
                        if (i == relID){
                            tieRel1.setText(sReltn.get(i).getRelatnDs());
                            ciModel.setReltnCD1(val);
                            tieRel1.setEnabled(false);
                        }

                    }
                });

            });
            mViewModel.getsMobile1().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setMobileN1(val);
                    tieContact1.setEnabled(false);
                    btnAdd1.setVisibility(View.GONE);
                }
                tieContact1.setText(val);
            });
            mViewModel.getsFeedback1().observe(getViewLifecycleOwner(), val ->{
                rgFeedbak1.clearCheck();
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak1.getChildCount(); i++){
                        ((RadioButton)rgFeedbak1.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("1")){
                    rgFeedbak1.check(R.id.rb_ci_postiveFeed1);
                }
                else if(val.equalsIgnoreCase("0")){
                    rgFeedbak1.check(R.id.rb_ci_negativeFeed1);

                }
                ciModel.setFeedBck1(val);
            });
            mViewModel.getsFBRemark1().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setFBRemrk1(remarks);
                    tieFBRemark1.setText(remarks);
                    tieFBRemark1.setClickable(false);
                    tieFBRemark1.setEnabled(false);
                }
            });
            mViewModel.getAddress1().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setAddress1(remarks);
                    tieAddress1.setClickable(false);
                    tieAddress1.setEnabled(false);
                }
                tieAddress1.setText(remarks);
            });

//            Neighbor 2
            mViewModel.getsNeigbor2().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setNeighbr2(val);
                    tieFullname2.setEnabled(false);
                }
                tieFullname2.setText(val);
            });
            mViewModel.getsRel2().observe(getViewLifecycleOwner(), val ->{
                mViewModel.getRelation().observe(getViewLifecycleOwner(), sReltn->{
                    int relID;
                    if (val.isEmpty()){
                        relID = -1;
                    }else {
                        relID = Integer.parseInt(val);
                    }
                    for (int i = 0; i < sReltn.size(); i++){
                        if (i == relID){
                            tieRel2.setText(sReltn.get(i).getRelatnDs());
                            ciModel.setReltnCD2(val);
                            tieRel2.setEnabled(false);
                        }

                    }
                });

            });
            mViewModel.getsMobile2().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setMobileN2(val);
                    tieContact2.setEnabled(false);
                    btnAdd2.setVisibility(View.GONE);
                }
                tieContact2.setText(val);
            });
            mViewModel.getsFeedback2().observe(getViewLifecycleOwner(), val ->{
                rgFeedbak2.clearCheck();
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak2.getChildCount(); i++){
                        ((RadioButton)rgFeedbak2.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("1")){
                    rgFeedbak2.check(R.id.rb_ci_postiveFeed2);
                }
                else if(val.equalsIgnoreCase("0")){
                    rgFeedbak2.check(R.id.rb_ci_negativeFeed2);
                }

                ciModel.setFeedBck2(val);
            });
            mViewModel.getsFBRemark2().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setFBRemrk2(remarks);
                    tieFBRemark2.setText(remarks);
                    tieFBRemark2.setClickable(false);
                    tieFBRemark2.setEnabled(false);
                }
            });
            mViewModel.getAddress2().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setAddress2(remarks);
                    tieAddress2.setClickable(false);
                    tieAddress2.setEnabled(false);
                }
                tieAddress2.setText(remarks);
            });
//        Neighbor 3
            mViewModel.getsNeigbor3().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setNeighbr3(val);
                    tieFullname3.setEnabled(false);
                }
                tieFullname3.setText(val);
            });
            mViewModel.getsRel3().observe(getViewLifecycleOwner(), val ->{
                mViewModel.getRelation().observe(getViewLifecycleOwner(), sReltn->{
                    int relID;
                    if (val.isEmpty()){
                        relID = -1;
                    }else {
                        relID = Integer.parseInt(val);
                    }
                    for (int i = 0; i < sReltn.size(); i++){
                        if (i == relID){
                            tieRel3.setText(sReltn.get(i).getRelatnDs());
                            ciModel.setReltnCD3(val);
                            tieRel3.setEnabled(false);
                        }

                    }
                });

            });
            mViewModel.getsMobile3().observe(getViewLifecycleOwner(), val ->{
                if (!val.trim().isEmpty()){
                    ciModel.setMobileN3(val);
                    tieContact3.setEnabled(false);
                    btnAdd3.setVisibility(View.GONE);
                }
                tieContact3.setText(val);
            });
            mViewModel.getsFeedback3().observe(getViewLifecycleOwner(), val ->{
                rgFeedbak3.clearCheck();
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak3.getChildCount(); i++){
                        ((RadioButton)rgFeedbak3.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("1")){
                    rgFeedbak3.check(R.id.rb_ci_postiveFeed3);
                }
                else if(val.equalsIgnoreCase("0")){
                    rgFeedbak3.check(R.id.rb_ci_negativeFeed3);
                }
                ciModel.setFeedBck3(val);
            });
            mViewModel.getsFBRemark3().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setFBRemrk3(remarks);
                    tieFBRemark3.setText(remarks);
                    tieFBRemark3.setClickable(false);
                    tieFBRemark3.setEnabled(false);
                }
            });
            mViewModel.getAddress3().observe(getViewLifecycleOwner(), remarks ->{
                if (!remarks.isEmpty()){
                    ciModel.setAddress3(remarks);
                    tieAddress3.setClickable(false);
                    tieAddress3.setEnabled(false);
                }
                tieAddress3.setText(remarks);
            });
            mViewModel.getsHasRecord().observe(getViewLifecycleOwner(), val ->{
                rgRecord.clearCheck();
                tilRecord.setVisibility(View.GONE);
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgRecord.getChildCount(); i++){
                        ((RadioButton)rgRecord.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("0")){
                    rgRecord.check(R.id.rb_ci_noRecord);
                    tilRecord.setVisibility(View.GONE);
                }
                else if(val.equalsIgnoreCase("1")){
                    rgRecord.check(R.id.rb_ci_withRecord);
                    mViewModel.getsRemRecord().observe(getViewLifecycleOwner(), remarks ->{
                        ciModel.setRemRecrd(remarks);
                        tieRecord.setText(remarks);
                    });
                    tilRecord.setVisibility(View.VISIBLE);
                    tieRecord.setClickable(false);
                    tieRecord.setEnabled(false);
                }
                ciModel.setFeedBck3(val);
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    public void errorMessage(String message){
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();
    }

}
