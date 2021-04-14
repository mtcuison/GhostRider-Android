package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIBarangayRecordInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecords;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;

import java.util.ArrayList;
import java.util.List;
//import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIBarangayRecord;

public class Fragment_CIBarangayRecord extends Fragment implements ViewModelCallBack {

    private VMCIBarangayRecords mViewModel;
    private CIBarangayRecordInfoModel ciModel;
    private RadioGroup rgRecord,rgFeedbak1,rgFeedbak2,rgFeedbak3;
    private TextInputLayout tilRecord,tilFBRemark1,tilFBRemark2,tilFBRemark3;
    private TextInputEditText tieRecord;
    private TextInputEditText tieFullname1, tieContact1, tieFBRemark1, tieRel1;
    private TextInputEditText tieFullname2, tieContact2, tieFBRemark2, tieRel2;
    private TextInputEditText tieFullname3, tieContact3, tieFBRemark3, tieRel3;
    private MaterialButton btnNext, btnPrevious, btnAdd1, btnAdd2, btnAdd3;

    List<CIBarangayRecordInfoModel> arrayList = new ArrayList<>();
    public static Fragment_CIBarangayRecord newInstance() {
        return new Fragment_CIBarangayRecord();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_barangay_record, container, false);
        ciModel = new CIBarangayRecordInfoModel();
        initWidgets(root);
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

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCIBarangayRecords.class);
        // TODO: Use the ViewModel
        mViewModel.getCIByTransNox(Activity_CIApplication.getInstance().getTransNox()).observe(getViewLifecycleOwner(), eciEvaluation -> {

            mViewModel.setCurrentCIDetail(eciEvaluation);
            setData(eciEvaluation);
        });
        rgRecord.setOnCheckedChangeListener(new ONCIBarangayRecord(rgRecord,mViewModel));
        rgFeedbak1.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak1,mViewModel));
        rgFeedbak2.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak2,mViewModel));
        rgFeedbak3.setOnCheckedChangeListener(new ONCIBarangayRecord(rgFeedbak3,mViewModel));

        btnPrevious.setOnClickListener(v -> {
            Activity_CIApplication.getInstance().moveToPageNumber(1);
        });
        btnAdd1.setOnClickListener(v -> {
            ciModel.setFBRemrk1(tieFBRemark1.getText().toString());
            ciModel.setNeighbr1(tieFullname1.getText().toString());
            ciModel.setReltnCD1(tieRel1.getText().toString());
            ciModel.setMobileN1(tieContact1.getText().toString());

            arrayList.add(ciModel);
            mViewModel.saveNeighbor(ciModel, "Neighbor1",Fragment_CIBarangayRecord.this);
        });

        btnAdd2.setOnClickListener(v -> {
            ciModel.setFBRemrk2(tieFBRemark2.getText().toString());
            ciModel.setNeighbr2(tieFullname2.getText().toString());
            ciModel.setReltnCD2(tieRel2.getText().toString());
            ciModel.setMobileN2(tieContact2.getText().toString());

            arrayList.add(ciModel);
            mViewModel.saveNeighbor(ciModel, "Neighbor2",Fragment_CIBarangayRecord.this);
        });

        btnAdd3.setOnClickListener(v -> {
            ciModel.setFBRemrk3(tieFBRemark3.getText().toString());
            ciModel.setNeighbr3(tieFullname3.getText().toString());
            ciModel.setReltnCD3(tieRel3.getText().toString());
            ciModel.setMobileN3(tieContact3.getText().toString());
            arrayList.add(ciModel);
            mViewModel.saveNeighbor(ciModel, "Neighbor3",Fragment_CIBarangayRecord.this);
        });
        btnNext.setOnClickListener(v -> {
            ciModel.setRemRecrd(tieRecord.getText().toString());
            mViewModel.saveNeighbor(ciModel, "Neighbor",Fragment_CIBarangayRecord.this);
        });
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
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();

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

    public void setData(ECIEvaluation eciEvaluation){

        if (eciEvaluation.getHasRecrd() != null){
//                    Loop for radio button not clickable
            for(int i = 0; i < rgRecord.getChildCount(); i++){
                ((RadioButton)rgRecord.getChildAt(i)).setClickable(false);
            }
            if(eciEvaluation.getHasRecrd().equalsIgnoreCase("0")){
                ciModel.setHasRecrd("0");
                rgRecord.check(R.id.rb_ci_noRecord);
            }
            else {
                ciModel.setHasRecrd("1");
                ciModel.setRemRecrd(eciEvaluation.getRemRecrd());
                rgRecord.check(R.id.rb_ci_withRecord);
                tilRecord.setVisibility(View.VISIBLE);
                tieRecord.setText(eciEvaluation.getRemRecrd());
                tieRecord.setClickable(false);
            }

        }
//                Neighbor 1
        if(eciEvaluation.getFeedBck1() != null){
            for(int i = 0; i < rgFeedbak1.getChildCount(); i++){
                ((RadioButton)rgFeedbak1.getChildAt(i)).setClickable(false);
            }
            if(eciEvaluation.getFeedBck1().equalsIgnoreCase("0")){
                rgFeedbak1.check(R.id.rb_ci_postiveFeed1);
            }
            else {
                rgFeedbak1.check(R.id.rb_ci_negativeFeed1);
                ciModel.setFBRemrk1(eciEvaluation.getFBRemrk1());
            }

            ciModel.setFeedBck1(eciEvaluation.getFeedBck1());
        }
        if (eciEvaluation.getNeighbr1() != null){
            ciModel.setNeighbr1(eciEvaluation.getNeighbr1());
            tieFullname1.setText(eciEvaluation.getNeighbr1());
        }

        if (eciEvaluation.getMobileN1() != null){
            ciModel.setMobileN1(eciEvaluation.getMobileN1());
            tieContact1.setText(eciEvaluation.getMobileN1());
            btnAdd1.setVisibility(View.GONE);
        }
        if (eciEvaluation.getReltnCD1() != null){
            ciModel.setReltnCD1(eciEvaluation.getReltnCD1());
            tieRel1.setText(eciEvaluation.getReltnCD1());
        }

//                Neighbor 2
        if(eciEvaluation.getFeedBck2() != null){
            for(int i = 0; i < rgFeedbak2.getChildCount(); i++){
                ((RadioButton)rgFeedbak2.getChildAt(i)).setClickable(false);
            }
            if(eciEvaluation.getFeedBck2().equalsIgnoreCase("0")){
                rgFeedbak2.check(R.id.rb_ci_postiveFeed2);
            }
            else {
                rgFeedbak2.check(R.id.rb_ci_negativeFeed2);
                ciModel.setFBRemrk2(eciEvaluation.getFBRemrk2());
            }

            ciModel.setFeedBck2(eciEvaluation.getFeedBck2());
        }
        if (eciEvaluation.getNeighbr2() != null){
            ciModel.setNeighbr2(eciEvaluation.getNeighbr2());
            tieFullname2.setText(eciEvaluation.getNeighbr2());
        }

        if (eciEvaluation.getMobileN2() != null){
            ciModel.setMobileN2(eciEvaluation.getMobileN2());
            tieContact2.setText(eciEvaluation.getMobileN2());
            btnAdd2.setVisibility(View.GONE);
        }
        if (eciEvaluation.getReltnCD2() != null){
            ciModel.setReltnCD2(eciEvaluation.getReltnCD2());
            tieRel2.setText(eciEvaluation.getReltnCD2());
        }
//                Feedback 3
        if(eciEvaluation.getFeedBck3() != null){
            for(int i = 0; i < rgFeedbak3.getChildCount(); i++){
                ((RadioButton)rgFeedbak3.getChildAt(i)).setClickable(false);
            }
            if(eciEvaluation.getFeedBck3().equalsIgnoreCase("0")){
                rgFeedbak3.check(R.id.rb_ci_postiveFeed3);
            }
            else {
                rgFeedbak3.check(R.id.rb_ci_negativeFeed3);
                ciModel.setFBRemrk3(eciEvaluation.getFBRemrk3());
            }

            ciModel.setFeedBck3(eciEvaluation.getFeedBck3());
        }
        if (eciEvaluation.getNeighbr3() != null){
            ciModel.setNeighbr3(eciEvaluation.getNeighbr3());
            tieFullname3.setText(eciEvaluation.getNeighbr3());
        }

        if (eciEvaluation.getMobileN3() != null){
            ciModel.setMobileN3(eciEvaluation.getMobileN3());
            tieContact3.setText(eciEvaluation.getMobileN3());
            btnAdd3.setVisibility(View.GONE);
        }
        if (eciEvaluation.getReltnCD3() != null){
            ciModel.setReltnCD3(eciEvaluation.getReltnCD3());
            tieRel3.setText(eciEvaluation.getReltnCD3());
        }
    }
}