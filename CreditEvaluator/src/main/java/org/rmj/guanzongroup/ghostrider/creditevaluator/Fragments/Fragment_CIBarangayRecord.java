package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static androidx.lifecycle.Lifecycle.Event.ON_STOP;
//import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecord;

public class Fragment_CIBarangayRecord extends Fragment implements ViewModelCallBack, LifecycleObserver {

    private static final String TAG = Fragment_CIBarangayRecord.class.getSimpleName();
    private VMCIBarangayRecords mViewModel;
    private CIBarangayRecordInfoModel ciModel;
    private RadioGroup rgRecord,rgFeedbak1,rgFeedbak2,rgFeedbak3;
    private TextInputLayout tilRecord,tilFBRemark1,tilFBRemark2,tilFBRemark3;
    private TextInputEditText tieRecord;
    private TextInputEditText tieFullname1, tieContact1, tieFBRemark1, tieRel1;
    private TextInputEditText tieFullname2, tieContact2, tieFBRemark2, tieRel2;
    private TextInputEditText tieFullname3, tieContact3, tieFBRemark3, tieRel3;
    private MaterialButton btnNext, btnPrevious, btnAdd1, btnAdd2, btnAdd3;

    private TextView sCompnyNm;
    private TextView dTransact;
    private TextView sModelNm;
    private TextView nTerm;
    private TextView nMobile;
    private TextView sTransNox;

    private MessageBox poMessage;
    List<CIBarangayRecordInfoModel> arrayList = new ArrayList<>();
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
        btnAdd1 = view.findViewById(R.id.btn_ci_add1);

//      Neighbor 2
        tieFullname2 = view.findViewById(R.id.tie_ci_neighborName2);
        tieContact2 = view.findViewById(R.id.tie_ci_NeighborContact2);
        tieRel2 = view.findViewById(R.id.tie_ci_neighborRel2);
        tilFBRemark2 = view.findViewById(R.id.til_ci_fbRemark2);
        tieFBRemark2 = view.findViewById(R.id.tie_ci_fbRemark2);
        btnAdd2 = view.findViewById(R.id.btn_ci_add2);

//      Neighbor 3
        tieFullname3 = view.findViewById(R.id.tie_ci_neighborName3);
        tieContact3 = view.findViewById(R.id.tie_ci_NeighborContact3);
        tieRel3 = view.findViewById(R.id.tie_ci_neighborRel3);
        tilFBRemark3 = view.findViewById(R.id.til_ci_fbRemark3);
        tieFBRemark3 = view.findViewById(R.id.tie_ci_fbRemark3);
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

        btnPrevious.setOnClickListener(v -> {
            Activity_CIApplication.getInstance().moveToPageNumber(1);
        });
        btnAdd1.setOnClickListener(v -> {
            try {
            ciModel.setFBRemrk1(tieFBRemark1.getText().toString());
            ciModel.setNeighbr1(tieFullname1.getText().toString());
            ciModel.setReltnCD1(tieRel1.getText().toString());
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
                ciModel.setReltnCD2(tieRel2.getText().toString());
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
                ciModel.setReltnCD3(tieRel3.getText().toString());
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
                    ciModel.setFeedBck1("0");
                    tieFBRemark1.setText("");
                    tilFBRemark1.setVisibility(View.GONE);
                }
                else {
                    ciModel.setFeedBck1("1");
                    tilFBRemark1.setVisibility(View.VISIBLE);
                }
            }
            if(rbView.getId() == R.id.rg_ci_Feedback2){
                if(checkedId == R.id.rb_ci_postiveFeed2) {
                    ciModel.setFeedBck2("0");
                    tieFBRemark2.setText("");
                    tilFBRemark2.setVisibility(View.GONE);
                }
                else{
                    ciModel.setFeedBck2("1");
                    tilFBRemark2.setVisibility(View.VISIBLE);
                }
            }
            if(rbView.getId() == R.id.rg_ci_Feedback3){
                if(checkedId == R.id.rb_ci_postiveFeed3) {
                    ciModel.setFeedBck3("0");
                    tilFBRemark3.setVisibility(View.GONE);
                    tieFBRemark3.setText("");
                }
                else {
                    ciModel.setFeedBck3("1");
                    tilFBRemark3.setVisibility(View.VISIBLE);
                }
            }
        }
    }

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
                if (!val.trim().isEmpty()){
                    ciModel.setReltnCD1(val);
                    tieRel1.setEnabled(false);
                }
                tieRel1.setText(val);
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
                tilFBRemark2.setVisibility(View.GONE);
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak1.getChildCount(); i++){
                        ((RadioButton)rgFeedbak1.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("0")){
                    rgFeedbak1.check(R.id.rb_ci_postiveFeed1);
                    tilFBRemark1.setVisibility(View.GONE);
                }
                else if(val.equalsIgnoreCase("1")){
                    rgFeedbak1.check(R.id.rb_ci_negativeFeed1);
                    mViewModel.getsFBRemark1().observe(getViewLifecycleOwner(), remarks ->{
                        ciModel.setFBRemrk1(remarks);
                        tieFBRemark1.setText(remarks);
                    });
                    tilFBRemark1.setVisibility(View.VISIBLE);
                    tieFBRemark1.setClickable(false);
                    tieFBRemark1.setEnabled(false);
                }
                ciModel.setFeedBck1(val);
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
                if (!val.trim().isEmpty()){
                    ciModel.setReltnCD2(val);
                    tieRel2.setEnabled(false);
                }
                tieRel2.setText(val);
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
                tilFBRemark2.setVisibility(View.GONE);
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak2.getChildCount(); i++){
                        ((RadioButton)rgFeedbak2.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("0")){
                    rgFeedbak2.check(R.id.rb_ci_postiveFeed2);
                    tilFBRemark2.setVisibility(View.GONE);
                }
                else if(val.equalsIgnoreCase("1")){
                    rgFeedbak2.check(R.id.rb_ci_negativeFeed2);
                    tilFBRemark2.setVisibility(View.VISIBLE);
                    tieFBRemark2.setClickable(false);
                    tieFBRemark2.setEnabled(false);
                }
                mViewModel.getsFBRemark2().observe(getViewLifecycleOwner(), remarks ->{
                    ciModel.setFBRemrk2(remarks);
                    tieFBRemark2.setText(remarks);
                });
                ciModel.setFeedBck2(val);
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
                if (!val.trim().isEmpty()){
                    ciModel.setReltnCD3(val);
                    tieRel3.setEnabled(false);
                }
                tieRel3.setText(val);
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
                tilFBRemark3.setVisibility(View.GONE);
                if (!val.trim().isEmpty()){
                    for(int i = 0; i < rgFeedbak3.getChildCount(); i++){
                        ((RadioButton)rgFeedbak3.getChildAt(i)).setClickable(false);
                    }
                }
                if(val.equalsIgnoreCase("0")){
                    rgFeedbak3.check(R.id.rb_ci_postiveFeed3);
                    tilFBRemark3.setVisibility(View.GONE);
                }
                else if(val.equalsIgnoreCase("1")){
                    rgFeedbak3.check(R.id.rb_ci_negativeFeed3);
                    mViewModel.getsFBRemark3().observe(getViewLifecycleOwner(), remarks ->{
                        ciModel.setFBRemrk3(remarks);
                        tieFBRemark3.setText(remarks);
                    });
                    tilFBRemark3.setVisibility(View.VISIBLE);
                    tieFBRemark3.setClickable(false);
                    tieFBRemark3.setEnabled(false);
                }
                ciModel.setFeedBck3(val);
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
