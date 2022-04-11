/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/28/22, 8:53 AM
 * project file last modified : 3/28/22, 8:50 AM
 */

package org.guanzongroup.com.creditevaluation.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_CI_ADDRESS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.guanzongroup.com.creditevaluation.Adapter.CIHistoryPreviewAdapter;
import org.guanzongroup.com.creditevaluation.Adapter.EvaluationAdapter;
import org.guanzongroup.com.creditevaluation.Adapter.NoScrollExListView;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.Dialog.DialogCIReason;
import org.guanzongroup.com.creditevaluation.Model.AdditionalInfoModel;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMCIHistoryPreview;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluationCIHistoryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Activity_CIHistoryPreview extends AppCompatActivity {
    private static final String TAG = Activity_CIHistoryPreview.class.getSimpleName();
    private VMCIHistoryPreview mViewModel;
    private CardView cvForEvaluation;
    //    private ExpandableListView listView;
    private NoScrollExListView listView;
    private RadioGroup rgEval;
    private RadioButton rbNoRecord, rbYesRecord;
    private oChildFndg loChild;
    private ExpandableListAdapter adapter;
    private TextInputEditText txtRecordRemarks,txtPersonnel, txtPosition, txtMobileNo;
    private TextInputEditText txtNeighbor1, txtNeighbor2, txtNeighbor3;
    private TextInputLayout tilRecordRemarks;
    private RadioGroup rgHasRecord;
    private TextView lblTransNox,lblClientName,lblDTransact, lblBranch;
    private MaterialButton btnApprove;
    private final List<oParentFndg> poParentLst = new ArrayList<>();
    private List<oChildFndg> poChildLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();
    private AdditionalInfoModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private oParentFndg parent;
    private oChildFndg child;
    private String psTransNo = "";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cihistory_preview);
        try {
            initWidgets();
            initIntentData();
            psTransNo = getIntent().getStringExtra("transno");
            mViewModel = new ViewModelProvider(this).get(VMCIHistoryPreview.class);
            mViewModel.getCIEvaluation(getIntent().getStringExtra("transno")).observe(this, ci->{
                try {
                    mViewModel.parseToEvaluationPreviewData(ci);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(ci.getPrsnBrgy() != null){
                    txtPersonnel.setText(ci.getPrsnBrgy());
                    infoModel.setAsstPersonnel(ci.getPrsnBrgy());
                }
                if(ci.getPrsnPstn() != null){
                    txtPosition.setText(ci.getPrsnPstn());
                    infoModel.setAsstPosition(ci.getPrsnPstn());
                }
                if(ci.getPrsnNmbr() != null){
                    txtMobileNo.setText(ci.getPrsnNmbr());
                    infoModel.setMobileNo(ci.getPrsnNmbr());
                }
                if(ci.getNeighBr1() != null){
                    infoModel.setNeighbr1(ci.getNeighBr1());
                    txtNeighbor1.setText(ci.getNeighBr1());
                }
                if(ci.getNeighBr2() != null){
                    infoModel.setNeighbr2(ci.getNeighBr2());
                    txtNeighbor2.setText(ci.getNeighBr2());
                }
                if(ci.getNeighBr3() != null){
                    infoModel.setNeighbr3(ci.getNeighBr3());
                    txtNeighbor3.setText(ci.getNeighBr3());
                }
                if (ci.getRcmdtnc1() != null) {
                    infoModel.setcRcmdtnx1(ci.getRcmdtnc1());

                }
                if (ci.getRcmdtns1() != null) {
                    infoModel.setsRcmdtnx1(ci.getRcmdtns1());
                }

                if (ci.getHasRecrd() != null) {
                    infoModel.setHasRecrd(ci.getHasRecrd());
                    if(ci.getHasRecrd().equalsIgnoreCase("0")){
                        rgHasRecord.check(R.id.rb_ci_noRecord);
                    }else{
                        rgHasRecord.check(R.id.rb_ci_withRecord);
                    }
                }
                if (ci.getRecrdRem() != null) {
                    infoModel.setRemRecrd(ci.getRecrdRem());
                    if(ci.getHasRecrd().equalsIgnoreCase("1")){
                        tilRecordRemarks.setVisibility(View.VISIBLE);
                        txtRecordRemarks.setText(ci.getRecrdRem());
                    }
                }
                mViewModel.getParsedEvaluationPreviewData().observe(Activity_CIHistoryPreview.this, foEvaluate->{
                    try{
                        if (foEvaluate.size()>0){
                            poParentLst.clear();
                            poChild.clear();
                            foEvaluate.forEach((oParent, oChild) -> {
                                poChildLst = new ArrayList<>();
                                poParentLst.add(oParent);
                                if(oChild.size() > 0) {
                                    for(int x = 0; x < oChild.size(); x++){
                                        oChildFndg loChild = oChild.get(x);
                                        poChildLst.add(loChild);
                                        poChild.put(oParent,poChildLst);
                                    }
                                }
                            });
                            if(poChild.size() <= 0){
                                cvForEvaluation.setVisibility(View.GONE);
                            }

                            listView.setAdapter(new CIHistoryPreviewAdapter(Activity_CIHistoryPreview.this, poParentLst, poChild));

                        }else {
                            cvForEvaluation.setVisibility(View.GONE);
                        }

                    }catch(NullPointerException e){
                        e.printStackTrace();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                });
            });

        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        btnApprove.setOnClickListener(new OnApproveListener());
    }
    private void initIntentData(){
        lblTransNox.setText("Transaction No.: " + getIntent().getStringExtra("transno"));
        lblClientName.setText(getIntent().getStringExtra("ClientNm"));
        lblDTransact.setText(getIntent().getStringExtra("dTransact"));
        lblBranch.setText(getIntent().getStringExtra("Branch"));
    }
    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_ci_evaluation);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        infoModel = new AdditionalInfoModel();
        btnApprove = findViewById(R.id.btn_Approve);
        poDialogx = new LoadDialog(Activity_CIHistoryPreview.this);
        poMessage = new MessageBox(Activity_CIHistoryPreview.this);
        listView = findViewById(R.id.expListview);
        txtPersonnel = findViewById(R.id.tie_ci_asstPersonel);
        txtPosition = findViewById(R.id.tie_ci_asstPosition);
        txtMobileNo = findViewById(R.id.tie_ci_asstPhoneNo);
        cvForEvaluation = findViewById(R.id.cvForEvaluation);
        txtNeighbor1 = findViewById(R.id.tie_ci_neighborName1);
        txtNeighbor2 = findViewById(R.id.tie_ci_neighborName2);
        txtNeighbor3 = findViewById(R.id.tie_ci_neighborName3);
        tilRecordRemarks = findViewById(R.id.til_ci_record);
        txtRecordRemarks = findViewById(R.id.tie_ci_record);
        rgHasRecord = findViewById(R.id.rg_ci_brgyRecord);
        lblTransNox = findViewById(R.id.lbl_ci_transNox);
        lblClientName = findViewById(R.id.lbl_ci_applicantName);
        lblDTransact = findViewById(R.id.lbl_ci_applicationDate);
        lblBranch = findViewById(R.id.lbl_ci_branch);
        rbNoRecord = findViewById(R.id.rb_ci_noRecord);
        rbYesRecord = findViewById(R.id.rb_ci_withRecord);
        rbNoRecord.setClickable(false);
        rbYesRecord.setClickable(false);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    private void initRecyclerData(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI loData){
        try{
            if (foEvaluate.size()>0){
                poParentLst.clear();
                poChild.clear();
                foEvaluate.forEach((oParent, oChild) -> {
                    poChildLst = new ArrayList<>();
                    poParentLst.add(oParent);
                    if(oChild.size() > 0) {
                        for(int x = 0; x < oChild.size(); x++){
                            oChildFndg loChild = oChild.get(x);
                            poChildLst.add(loChild);
                            poChild.put(oParent,poChildLst);
                        }
                    }
                });
                listView.setAdapter(new EvaluationAdapter(Activity_CIHistoryPreview.this, poParentLst, poChild, new EvaluationAdapter.OnConfirmInfoListener() {
                    @Override
                    public void OnConfirm(oParentFndg foParent, oChildFndg foChild) {

                    }
                }));
                listView.setHeightWrapContent();
            }else {
                cvForEvaluation.setVisibility(View.GONE);
            }

        }catch(NullPointerException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private class OnApproveListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DialogCIReason loDialog = new DialogCIReason(Activity_CIHistoryPreview.this);
            loDialog.initDialogCIReason((dialog, transtat, reason) -> {
                infoModel.setTranstat(transtat);
                infoModel.setsRemarks(reason);
                dialog.dismiss();
                if(infoModel.isApproved()){
                    LoadDialog loading = new LoadDialog(Activity_CIHistoryPreview.this);
                    mViewModel.PostBHApproval(psTransNo, infoModel.getTranstat(), infoModel.getsRemarks(), new VMEvaluationCIHistoryInfo.OnTransactionCallBack() {
                        @Override
                        public void onSuccess(String fsMessage) {
                            loading.dismiss();
                            MessageBox msgBox = new MessageBox(Activity_CIHistoryPreview.this);
                            msgBox.initDialog();
                            msgBox.setTitle("CI Evaluation");
                            msgBox.setMessage(fsMessage);
                            msgBox.setPositiveButton("Okay", (v, diags) -> {
                                diags.dismiss();
                            });
                            msgBox.show();
                        }

                        @Override
                        public void onFailed(String fsMessage) {
                            loading.dismiss();
                            MessageBox msgBox = new MessageBox(Activity_CIHistoryPreview.this);
                            msgBox.initDialog();
                            msgBox.setTitle("CI Evaluation");
                            msgBox.setMessage(fsMessage);
                            msgBox.setPositiveButton("Okay", (v, diags) -> {
                                diags.dismiss();
                            });
                            msgBox.show();
                        }

                        @Override
                        public void onLoad() {
                            loading.initDialog("CI Evaluation", "CI Evalation Approval Processing...Please wait.", false);
                            loading.show();
                        }
                    });
                }
            });
            loDialog.show();
        }
    }

}