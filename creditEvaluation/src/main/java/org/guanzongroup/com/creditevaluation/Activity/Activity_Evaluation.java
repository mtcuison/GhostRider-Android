package org.guanzongroup.com.creditevaluation.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_CI_ADDRESS;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.guanzongroup.com.creditevaluation.Adapter.Adapter_CIEvaluation_Headers;
import org.guanzongroup.com.creditevaluation.Adapter.Adapter_CI_Evaluation;
import org.guanzongroup.com.creditevaluation.Adapter.onEvaluate;
import org.guanzongroup.com.creditevaluation.Adapter.onSelectResultListener;
import org.guanzongroup.com.creditevaluation.Core.FindingsParser;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.Dialog.DialogCIReason;
import org.guanzongroup.com.creditevaluation.Model.AdditionalInfoModel;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Activity_Evaluation extends AppCompatActivity {

    private static final String TAG = Activity_Evaluation.class.getSimpleName();
    private VMEvaluation mViewModel;
    private TextInputEditText txtRecordRemarks,txtPersonnel, txtPosition, txtMobileNo;
    private TextInputEditText txtNeighbor1, txtNeighbor2, txtNeighbor3;
    private TextInputLayout tilRecordRemarks;
    private RadioGroup rgHasRecord;
    private TextView lblTransNox,lblClientName,lblDTransact, lblBranch;
    private MaterialButton btnSaveNeighbor1, btnSaveNeighbor2,btnSaveNeighbor3;
    private Button btnSave,btnUpload,btnSaveAdditional;

    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();
    private AdditionalInfoModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private EImageInfo poImageInfo;

    private ImageFileCreator poLocation;

    private String psParent = "",
                   psKeyxxx = "",
                   psResult = "",
                    psAlttude = "",
                    psLongtde = "";

    private onEvaluate poAdLstnr;

    private final ActivityResultLauncher<Intent> poCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try{
            if(result.getResultCode() == RESULT_OK) {
                poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(poImageInfo.getFileLoct()));
                mViewModel.saveResidenceImageInfo(poImageInfo);
                mViewModel.SaveAddressResult(psParent, psAlttude, psLongtde, new VMEvaluation.onSaveAdditionalInfo() {
                    @Override
                    public void OnSuccessResult(String args, String message) {
                        SaveCIResult(psParent, psKeyxxx, psResult);
                        poAdLstnr.OnSetResult(true);
                    }

                    @Override
                    public void OnFailedResult(String message) {

                    }
                });
            } else {
                poAdLstnr.OnSetResult(false);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        try {
            initWidgets();
            initIntentData();
            mViewModel = new ViewModelProvider(this).get(VMEvaluation.class);
            mViewModel.setTransNox(getIntent().getStringExtra("transno"));
            mViewModel.getCIEvaluation(getIntent().getStringExtra("transno")).observe(this, ci->{
                setupEvaluationAdapter(ci);

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

                if(ci.getPrsnBrgy() != null &&
                        ci.getPrsnPstn() != null &&
                        ci.getPrsnNmbr() != null) {
                    findViewById(R.id.rb_ci_noRecord).setEnabled(false);
                    findViewById(R.id.rb_ci_withRecord).setEnabled(false);
                    txtRecordRemarks.setEnabled(false);
                    txtPersonnel.setEnabled(false);
                    txtPosition.setEnabled(false);
                    txtMobileNo.setEnabled(false);
                    findViewById(R.id.btnSaveAdditional).setVisibility(View.GONE);
                    findViewById(R.id.btnEditAdditional).setVisibility(View.VISIBLE);
                }

                if(ci.getNeighBr1() != null){
                    infoModel.setNeighbr1(ci.getNeighBr1());
                    txtNeighbor1.setText(ci.getNeighBr1());
                    txtNeighbor1.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor1).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor1).setVisibility(View.VISIBLE);
                }

                if(ci.getNeighBr2() != null){
                    infoModel.setNeighbr2(ci.getNeighBr2());
                    txtNeighbor2.setText(ci.getNeighBr2());
                    txtNeighbor2.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor2).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor2).setVisibility(View.VISIBLE);
                }

                if(ci.getNeighBr3() != null){
                    infoModel.setNeighbr3(ci.getNeighBr3());
                    txtNeighbor3.setText(ci.getNeighBr3());
                    txtNeighbor3.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor3).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor3).setVisibility(View.VISIBLE);
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

                if(ci.getUploaded().equalsIgnoreCase("1")){
                    findViewById(R.id.linear_approve).setVisibility(View.VISIBLE);
                    findViewById(R.id.linear_upload).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.linear_approve).setVisibility(View.GONE);
                    findViewById(R.id.linear_upload).setVisibility(View.VISIBLE);
                }

            });
        } catch (Exception e){
            e.printStackTrace();
        }



        rgHasRecord.setOnCheckedChangeListener(new ONCIHasRecord(rgHasRecord,mViewModel));
        btnSaveAdditional.setOnClickListener(v -> {
            infoModel.setRemRecrd(Objects.requireNonNull(txtRecordRemarks.getText()).toString());
            infoModel.setAsstPersonnel(Objects.requireNonNull(txtPersonnel.getText()).toString());
            infoModel.setAsstPosition(Objects.requireNonNull(txtPosition.getText()).toString());
            infoModel.setMobileNo(Objects.requireNonNull(txtMobileNo.getText()).toString());
            mViewModel.saveAdditionalInfo(infoModel, "Additional", new VMEvaluation.onSaveAdditionalInfo() {
                @Override
                public void OnSuccessResult(String args, String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                    findViewById(R.id.rb_ci_noRecord).setEnabled(false);
                    findViewById(R.id.rb_ci_withRecord).setEnabled(false);
                    txtRecordRemarks.setEnabled(false);
                    txtPersonnel.setEnabled(false);
                    txtPosition.setEnabled(false);
                    txtMobileNo.setEnabled(false);
                    findViewById(R.id.btnSaveAdditional).setVisibility(View.GONE);
                    findViewById(R.id.btnEditAdditional).setVisibility(View.VISIBLE);
                }

                @Override
                public void OnFailedResult(String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnSaveNeighbor1.setOnClickListener(v -> {
            infoModel.setNeighbr1(Objects.requireNonNull(txtNeighbor1.getText()).toString());
            mViewModel.saveAdditionalInfo(infoModel,"Neighbor1",  new VMEvaluation.onSaveAdditionalInfo() {
                @Override
                public void OnSuccessResult(String args, String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                    txtNeighbor1.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor1).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor1).setVisibility(View.VISIBLE);
                }

                @Override
                public void OnFailedResult(String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnSaveNeighbor2.setOnClickListener(v -> {
            infoModel.setNeighbr2(Objects.requireNonNull(txtNeighbor2.getText()).toString());
            mViewModel.saveAdditionalInfo(infoModel,"Neighbor2",  new VMEvaluation.onSaveAdditionalInfo() {
                @Override
                public void OnSuccessResult(String args, String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                    txtNeighbor2.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor2).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor2).setVisibility(View.VISIBLE);
                }

                @Override
                public void OnFailedResult(String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                }
            });

        });
        btnSaveNeighbor3.setOnClickListener(v -> {
            infoModel.setNeighbr3(Objects.requireNonNull(txtNeighbor3.getText()).toString());
            mViewModel.saveAdditionalInfo(infoModel,"Neighbor3",  new VMEvaluation.onSaveAdditionalInfo() {
                @Override
                public void OnSuccessResult(String args, String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                    txtNeighbor3.setEnabled(false);
                    findViewById(R.id.btnSaveNeighbor3).setVisibility(View.GONE);
                    findViewById(R.id.btnEditNeighbor3).setVisibility(View.VISIBLE);
                }

                @Override
                public void OnFailedResult(String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                }
            });

        });

        btnSave.setOnClickListener(v ->  {
            if(!infoModel.isValidEvaluation()){
                errorMessage(infoModel.getMessage());
            }else{
                if (!infoModel.getcRcmdtnx1().isEmpty() && !infoModel.getsRcmdtnx1().isEmpty()){
                    sendCIEvaluation();
                }else{
                    initDialog();
                }
            }

        });

        btnUpload.setOnClickListener(v ->  {
            Log.e("sizes", String.valueOf(poChild.size()));
            if(poChild.size() > 0){
                errorMessage("Please finish all required findings.");
            }else{
                if(!infoModel.isValidEvaluation()){
                    errorMessage(infoModel.getMessage());
                }else {
                    mViewModel.UploadEvaluationResult(new VMEvaluation.onPostCIEvaluation() {
                        @Override
                        public void OnPost() {
                            poDialogx.initDialog("CI Evaluation", "Uploading latest data. Please wait...", false);
                            poDialogx.show();
                        }

                        @Override
                        public void OnSuccessPost(String args) {
                            Log.e("args", args);
                            poDialogx.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("CI Evaluation");
                            poMessage.setMessage(args);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> {
                                dialog.dismiss();
                            });
                            poMessage.show();
                            btnSave.setEnabled(true);
                        }

                        @Override
                        public void OnFailedPost(String message) {
                            Log.e("message", message);
                            poDialogx.dismiss();
                            errorMessage(message);
                        }
                    });
                }
            }
        });

        findViewById(R.id.btnEditAdditional).setOnClickListener(v -> {
            findViewById(R.id.rb_ci_noRecord).setEnabled(true);
            findViewById(R.id.rb_ci_withRecord).setEnabled(true);
            txtRecordRemarks.setEnabled(true);
            txtPersonnel.setEnabled(true);
            txtPosition.setEnabled(true);
            txtMobileNo.setEnabled(true);
            findViewById(R.id.btnSaveAdditional).setVisibility(View.VISIBLE);
            findViewById(R.id.btnEditAdditional).setVisibility(View.GONE);
        });
        findViewById(R.id.btnEditNeighbor1).setOnClickListener(v -> {
            txtNeighbor1.setEnabled(true);
            findViewById(R.id.btnEditNeighbor1).setVisibility(View.GONE);
            findViewById(R.id.btnSaveNeighbor1).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.btnEditNeighbor2).setOnClickListener(v -> {
            txtNeighbor2.setEnabled(true);
            findViewById(R.id.btnEditNeighbor2).setVisibility(View.GONE);
            findViewById(R.id.btnSaveNeighbor2).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.btnEditNeighbor3).setOnClickListener(v -> {
            txtNeighbor3.setEnabled(true);
            findViewById(R.id.btnEditNeighbor3).setVisibility(View.GONE);
            findViewById(R.id.btnSaveNeighbor3).setVisibility(View.VISIBLE);
        });
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

        poLocation = new ImageFileCreator(this, SUB_FOLDER_CI_ADDRESS, getIntent().getStringExtra("transno"));
        infoModel = new AdditionalInfoModel();

        poDialogx = new LoadDialog(Activity_Evaluation.this);
        poMessage = new MessageBox(Activity_Evaluation.this);
        txtPersonnel = findViewById(R.id.tie_ci_asstPersonel);
        txtPosition = findViewById(R.id.tie_ci_asstPosition);
        txtMobileNo = findViewById(R.id.tie_ci_asstPhoneNumber);
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
        btnSaveNeighbor1 = findViewById(R.id.btnSaveNeighbor1);
        btnSaveNeighbor2 = findViewById(R.id.btnSaveNeighbor2);
        btnSaveNeighbor3 = findViewById(R.id.btnSaveNeighbor3);
        btnSaveAdditional = findViewById(R.id.btnSaveAdditional);

        btnUpload = findViewById(R.id.btnUpload);
        btnSave = findViewById(R.id.btnSave);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                } else {
                    infoModel.setHasRecrd("1");
                    tilRecordRemarks.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void initDialog(){
        DialogCIReason loDialog = new DialogCIReason(Activity_Evaluation.this);
        loDialog.initDialogCIReason((dialog, transtat, reason) -> {
            infoModel.setTranstat(transtat);
            infoModel.setsRemarks(reason);
            dialog.dismiss();
            if(infoModel.isApproved()){
                sendCIEvaluation();
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

    public void showDialogImg(){
        poMessage.initDialog();
        poMessage.setTitle("Residence Info");
        poMessage.setMessage("Please take applicant residence picture. \n");
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            poLocation.setTransNox(mViewModel.getTransNox());
            poLocation.CreateFile((openCamera, camUsage, photPath, FileName) -> {
                poImageInfo = new EImageInfo();
                poImageInfo.setDtlSrcNo(mViewModel.getTransNox());
                poImageInfo.setSourceNo(mViewModel.getTransNox());
                poImageInfo.setImageNme(FileName);
                poImageInfo.setFileLoct(photPath);
//                psAlttude = String.valueOf(latitude);
//                psLongtde = String.valueOf(longitude);
                openCamera.putExtra("android.intent.extras.CAMERA_FACING", 1);
                poCamera.launch(openCamera);
            });
        });
        poMessage.show();
    }

    private void sendCIEvaluation(){
        mViewModel.saveAdditionalInfo(infoModel, "Approval", new VMEvaluation.onSaveAdditionalInfo() {
            @Override
            public void OnSuccessResult(String args, String message) {
                mViewModel.postCIEvaluation(new VMEvaluation.onPostCIEvaluation() {
                    @Override
                    public void OnPost() {
                        poDialogx.initDialog("CI Evaluation", "Sending approval to server. Please wait...", false);
                        poDialogx.show();
                    }

                    @Override
                    public void OnSuccessPost(String args) {
                        poDialogx.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("CI Evaluation List");
                        poMessage.setMessage(args);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                            finish();
                        });
                        poMessage.show();
                    }

                    @Override
                    public void OnFailedPost(String message) {
                        poDialogx.dismiss();
                        errorMessage(message);
                    }
                });
            }

            @Override
            public void OnFailedResult(String message) {

            }
        });
    }

    private void SaveCIResult(String fsPar, String fsKey, String fsRes){
        try{
            Log.d(TAG, fsPar + ", " + fsKey + ", " + fsRes);
            mViewModel.SaveEvaluationResult(fsPar, fsKey, fsRes, new VMEvaluation.onSaveAdditionalInfo() {
                @Override
                public void OnSuccessResult(String args, String message) {
                    Toast.makeText(Activity_Evaluation.this, "Record save!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void OnFailedResult(String message) {
                    Toast.makeText(Activity_Evaluation.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupEvaluationAdapter(ECreditOnlineApplicationCI ci){
        try {
            JSONArray laEval = new JSONArray();
            JSONObject loDetail = FindingsParser.scanForEvaluation(ci.getAddressx(), ci.getAddrFndg());
            if(!loDetail.getJSONArray("detail").getJSONObject(0).toString().equalsIgnoreCase("{}")){
                laEval.put(FindingsParser.scanForEvaluation(ci.getAddressx(), ci.getAddrFndg()));
            }

            loDetail = FindingsParser.scanForEvaluation(ci.getIncomexx(), ci.getIncmFndg());
            if(!loDetail.getJSONArray("detail").getJSONObject(0).toString().equalsIgnoreCase("{}")){
                laEval.put(FindingsParser.scanForEvaluation(ci.getIncomexx(), ci.getIncmFndg()));
            }

            loDetail = FindingsParser.scanForEvaluation(ci.getAssetsxx(), ci.getAsstFndg());
            if(!loDetail.getJSONArray("detail").getJSONObject(0).toString().equalsIgnoreCase("{}")){
                laEval.put(FindingsParser.scanForEvaluation(ci.getAssetsxx(), ci.getAsstFndg()));
            }
            Log.d(TAG, laEval.toString());
            Adapter_CIEvaluation_Headers loAdapter = new Adapter_CIEvaluation_Headers(Activity_Evaluation.this, laEval, false, new onSelectResultListener() {
                @Override
                public void OnCorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                    if(fsPar.equalsIgnoreCase("primary_address")){
                        poAdLstnr = listener;
                        psParent = "primary_address";
                        psKeyxxx = fsKey;
                        psResult = fsRes;
                        showDialogImg();
                    } else if(fsPar.equalsIgnoreCase("present_address")){
                        poAdLstnr = listener;
                        psParent = "present_address";
                        psKeyxxx = fsKey;
                        psResult = fsRes;
                        showDialogImg();
                    } else {
                        SaveCIResult(fsPar, fsKey, fsRes);
                        listener.OnSetResult(true);
                    }
                }

                @Override
                public void OnIncorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                    if(fsPar.equalsIgnoreCase("primary_address")){
                        poAdLstnr = listener;
                        psParent = "primary_address";
                        psKeyxxx = fsKey;
                        psResult = fsRes;
                        showDialogImg();
                    } else if(fsPar.equalsIgnoreCase("present_address")){
                        poAdLstnr = listener;
                        psParent = "present_address";
                        psKeyxxx = fsKey;
                        psResult = fsRes;
                        showDialogImg();
                    } else {
                        SaveCIResult(fsPar, fsKey, fsRes);
                        listener.OnSetResult(true);
                    }
                }
            });

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            LinearLayoutManager loManager = new LinearLayoutManager(Activity_Evaluation.this);
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(loAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}