package org.guanzongroup.com.creditevaluation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.guanzongroup.com.creditevaluation.Adapter.EvaluationAdapter;
import org.guanzongroup.com.creditevaluation.Adapter.NoScrollExListView;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.Dialog.DialogCIReason;
import org.guanzongroup.com.creditevaluation.Model.AdditionalInfoModel;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;
import org.guanzongroup.com.creditevaluation.ViewModel.ViewModelCallback;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Activity_Evaluation extends AppCompatActivity implements VMEvaluation.ORetrieveCallBack, VMEvaluation.onSaveAdditionalInfo{

    private static final String TAG = Activity_Evaluation.class.getSimpleName();
    private VMEvaluation mViewModel;
    private CardView cvForEvaluation;
//    private ExpandableListView listView;
    private NoScrollExListView listView;
    private RadioGroup rgEval;
    private oChildFndg loChild;
    private ExpandableListAdapter adapter;
    private TextInputEditText txtRecordRemarks,txtPersonnel, txtPosition, txtMobileNo;
    private TextInputEditText txtNeighbor1, txtNeighbor2, txtNeighbor3;
    private TextInputLayout tilRecordRemarks;
    private RadioGroup rgHasRecord;
    private TextView lblTransNox,lblClientName,lblDTransact, lblBranch;
    private MaterialButton btnSaveAsstPersonnel, btnSaveAsstPosition,btnSaveAsstPhoneNo;
    private MaterialButton btnSaveNeighbor1, btnSaveNeighbor2,btnSaveNeighbor3,btnSaveRecord;
    private Button btnSave;

    private final List<oParentFndg> poParentLst = new ArrayList<>();
    private List<oChildFndg> poChildLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();
    private AdditionalInfoModel infoModel;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        try {
            initWidgets();
            initIntentData();
            mViewModel = new ViewModelProvider(this).get(VMEvaluation.class);
            mViewModel.RetrieveApplicationData(getIntent().getStringExtra("transno"), Activity_Evaluation.this);
            mViewModel.setTransNox(getIntent().getStringExtra("transno"));
            mViewModel.getCIEvaluation(getIntent().getStringExtra("transno")).observe(this, ci->{
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
                    infoModel.setTranstat(ci.getRcmdtnc1());
                }
                if (ci.getHasRecrd() != null) {
                    infoModel.setHasRecrd(ci.getHasRecrd());
                }
                if (ci.getRecrdRem() != null) {
                    infoModel.setRemRecrd(ci.getRcmdtnc1());
                    if(ci.getHasRecrd().equalsIgnoreCase("1")){
                        tilRecordRemarks.setVisibility(View.VISIBLE);
                        txtRecordRemarks.setText(ci.getRecrdRem());
                    }
                }
                if (ci.getRcmdtns1() != null) {
                    infoModel.setsRemarks(ci.getRcmdtns1());
                    txtRecordRemarks.setText(ci.getRcmdtns1());
                }
            });
            rgHasRecord.setOnCheckedChangeListener(new ONCIHasRecord(rgHasRecord,mViewModel));
            btnSaveAsstPhoneNo.setOnClickListener(v -> {
                infoModel.setMobileNo(txtMobileNo.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"PhoneNo", Activity_Evaluation.this);
            });
            btnSaveNeighbor1.setOnClickListener(v -> {
                infoModel.setNeighbr1(txtNeighbor1.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Neighbor1", Activity_Evaluation.this);
            });
            btnSaveNeighbor2.setOnClickListener(v -> {
                infoModel.setNeighbr2(txtNeighbor2.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Neighbor2", Activity_Evaluation.this);

            });
            btnSaveNeighbor3.setOnClickListener(v -> {
                infoModel.setNeighbr3(txtNeighbor3.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Neighbor3", Activity_Evaluation.this);

            });

            btnSaveRecord.setOnClickListener(v -> {
                infoModel.setRemRecrd(txtRecordRemarks.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Record", Activity_Evaluation.this);

            });
            btnSave.setOnClickListener(v ->  {
                if(!infoModel.isValidEvaluation()){
                    errorMessage(infoModel.getMessage());
                }else{
                     initDialog();
                }
            });
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
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
        poMessage = new MessageBox(Activity_Evaluation.this);
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
        btnSaveRecord = findViewById(R.id.btnSaveRecord);
        btnSaveAsstPersonnel = findViewById(R.id.btnSaveAsstPersonnel);
        btnSaveAsstPosition = findViewById(R.id.btnSaveAsstPosition);
        btnSaveAsstPhoneNo = findViewById(R.id.btnSaveAsstPhoneNo);
        btnSaveNeighbor1 = findViewById(R.id.btnSaveNeighbor1);
        btnSaveNeighbor2 = findViewById(R.id.btnSaveNeighbor2);
        btnSaveNeighbor3 = findViewById(R.id.btnSaveNeighbor3);
        btnSave = findViewById(R.id.btnSave);

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
                listView.setAdapter(new EvaluationAdapter(Activity_Evaluation.this, poParentLst, poChild, new EvaluationAdapter.OnConfirmInfoListener() {
                    @Override
                    public void OnConfirm(oParentFndg foParent, oChildFndg foChild) {
                        Log.e(TAG, "Parent : " + foParent.getParentDescript() +
                                ", Child : " + foChild.getLabel() +
                                ", Value : " + foChild.getValue());
                    }
                }));

            }else {
                cvForEvaluation.setVisibility(View.GONE);
            }

        }catch(NullPointerException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void OnSuccessResult(String args,String message) {
        GToast.CreateMessage(this,message,GToast.INFORMATION).show();
        if(args.equalsIgnoreCase("Approval")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);
        }
    }

    @Override
    public void OnFailedResult(String message) {
        errorMessage(message);
    }

    class ONCIHasRecord implements RadioGroup.OnCheckedChangeListener{

        View rbView;
        VMEvaluation mViewModel;
        ONCIHasRecord(View view, VMEvaluation viewModel)
        {
            this.rbView = view;
            this.mViewModel = viewModel;
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rg_ci_brgyRecord){
                if(checkedId == R.id.rb_ci_noRecord) {
                    infoModel.setHasRecrd("0");
                    txtRecordRemarks.setText("");
                    tilRecordRemarks.setVisibility(View.GONE);
                }
                else {
                    infoModel.setHasRecrd("1");
                    tilRecordRemarks.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onRetrieveSuccess(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData) {
        Log.e("hash map ", foEvaluate.toString());

        initRecyclerData(foEvaluate,foData);
    }

    @Override
    public void onRetrieveFailed(String message) {

    }

    public void initDialog(){
        DialogCIReason loDialog = new DialogCIReason(Activity_Evaluation.this);
        loDialog.initDialogCIReason((dialog, transtat, reason) -> {
            infoModel.setTranstat(transtat);
            infoModel.setsRemarks(reason);
            dialog.dismiss();
            if(infoModel.isApproved()){
                mViewModel.saveAdditionalInfo(infoModel, "Approval",Activity_Evaluation.this);
            }
        });
        loDialog.show();
    }
    public void errorMessage(String message){
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation List");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
}