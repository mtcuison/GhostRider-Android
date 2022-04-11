package org.guanzongroup.com.creditevaluation.Activity;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.SUB_FOLDER_CI_ADDRESS;
import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.APP_SYNC_DATA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.guanzongroup.com.creditevaluation.Adapter.EvaluationAdapter;
import org.guanzongroup.com.creditevaluation.Adapter.NoScrollExListView;
import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.Dialog.DialogCIReason;
import org.guanzongroup.com.creditevaluation.Model.AdditionalInfoModel;
import org.guanzongroup.com.creditevaluation.R;
import org.guanzongroup.com.creditevaluation.ViewModel.VMEvaluation;
import org.guanzongroup.com.creditevaluation.ViewModel.ViewModelCallback;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Activity_Evaluation extends AppCompatActivity implements VMEvaluation.onSaveAdditionalInfo{

    private static final String TAG = Activity_Evaluation.class.getSimpleName();
    private VMEvaluation mViewModel;
    private CardView cvForEvaluation;
    //    private ExpandableListView listView;
    private NoScrollExListView listView;
    private RadioGroup rgEval;
    private RadioButton rbNoRecord, rbYesRecord;
    private oChildFndg loChild;
    private EvaluationAdapter adapter;
    private TextInputEditText txtRecordRemarks,txtPersonnel, txtPosition, txtMobileNo;
    private TextInputEditText txtNeighbor1, txtNeighbor2, txtNeighbor3;
    private TextInputLayout tilRecordRemarks;
    private RadioGroup rgHasRecord;
    private TextView lblTransNox,lblClientName,lblDTransact, lblBranch;
    private MaterialButton btnSaveAsstPersonnel, btnSaveAsstPosition,btnSaveAsstPhoneNo;
    private MaterialButton btnSaveNeighbor1, btnSaveNeighbor2,btnSaveNeighbor3,btnSaveRecord;
    private Button btnSave,btnUpload;

    private final List<oParentFndg> poParentLst = new ArrayList<>();
    private List<oChildFndg> poChildLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild = new HashMap<>();
    private AdditionalInfoModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private oParentFndg parent;
    private oChildFndg child;

    private ImageFileCreator poLocation;
    private EImageInfo poImageInfo;
    @RequiresApi(api = Build.VERSION_CODES.N)
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
                try {
                    mViewModel.parseToEvaluationData(ci);
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

            });
            mViewModel.getParsedEvaluationData().observe(Activity_Evaluation.this, foEvaluate->{
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
                        adapter = new EvaluationAdapter(Activity_Evaluation.this, poParentLst, poChild, new EvaluationAdapter.OnConfirmInfoListener() {
                            @Override
                            public void OnConfirm(oParentFndg foParent, oChildFndg foChild) {
                                Log.e(TAG, "Parent : " + foParent.getParentDescript() +
                                        ", Child : " + foChild.getLabel() +
                                        ", Value : " + foChild.getValue());
                                parent = foParent;
                                child = foChild;
                                if(foParent.getParentDescript().equalsIgnoreCase("Present Address")){
                                    showDialogImg(foParent, foChild);
                                }else if(foParent.getParentDescript().equalsIgnoreCase("Primary Address")){
                                    showDialogImg(foParent, foChild);
                                }else{
                                    mViewModel.saveDataEvaluation(foParent,foChild, Activity_Evaluation.this);
                                }
                            }
                        });
                        listView.setAdapter(adapter);

                    }else {
                        cvForEvaluation.setVisibility(View.GONE);
                    }

                }catch(NullPointerException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            });
            rgHasRecord.setOnCheckedChangeListener(new ONCIHasRecord(rgHasRecord,mViewModel));
            btnSaveAsstPersonnel.setOnClickListener(v -> {
                infoModel.setAsstPersonnel(txtPersonnel.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Personnel", Activity_Evaluation.this);
            });
            btnSaveAsstPosition.setOnClickListener(v -> {
                infoModel.setAsstPosition(txtPosition.getText().toString());
                mViewModel.saveAdditionalInfo(infoModel,"Position", Activity_Evaluation.this);
            });
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
                   if (infoModel.getcRcmdtnx1() != null && infoModel.getsRcmdtnx1() != null){
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

        poLocation = new ImageFileCreator(this, SUB_FOLDER_CI_ADDRESS, getIntent().getStringExtra("transno"));
        infoModel = new AdditionalInfoModel();

        poDialogx = new LoadDialog(Activity_Evaluation.this);
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
        btnUpload = findViewById(R.id.btnUpload);

        rbNoRecord = findViewById(R.id.rb_ci_noRecord);
        rbYesRecord = findViewById(R.id.rb_ci_withRecord);
        btnSave = findViewById(R.id.btnSave);

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
                listView.setAdapter(new EvaluationAdapter(Activity_Evaluation.this, poParentLst, poChild, new EvaluationAdapter.OnConfirmInfoListener() {
                    @Override
                    public void OnConfirm(oParentFndg foParent, oChildFndg foChild) {
                        Log.e(TAG, "Parent : " + foParent.getParentDescript() +
                                ", Child : " + foChild.getLabel() +
                                ", Value : " + foChild.getValue());
                        parent = foParent;
                        child = foChild;
                        if(foParent.getParentDescript().equalsIgnoreCase("Present Address")){
                            showDialogImg(foParent,foChild);
                        }else if(foParent.getParentDescript().equalsIgnoreCase("Primary Address")){
                            showDialogImg(foParent,foChild);
                        }else{
                            mViewModel.saveDataEvaluation(foParent,foChild, Activity_Evaluation.this);
                        }
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


    @Override
    public void OnSuccessResult(String args,String message) {
        GToast.CreateMessage(this,message,GToast.INFORMATION).show();
        if(args.equalsIgnoreCase("Approval")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sendCIEvaluation();
                }
            }, 2000); // wait for 5 seconds
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

//    @Override
//    public void onRetrieveSuccess(HashMap<oParentFndg, List<oChildFndg>> foEvaluate, ECreditOnlineApplicationCI foData) {
//        initRecyclerData(foEvaluate,foData);
//    }
//
//    @Override
//    public void onRetrieveFailed(String message) {
//
//    }

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

    public void showDialogImg(oParentFndg foParent, oChildFndg foChild){

        parent = foParent;
        child = foChild;
        poMessage.initDialog();
        poMessage.setTitle("Residence Info");
        poMessage.setMessage("Please take applicant residence picture. \n");
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            poLocation.setTransNox(mViewModel.getTransNox());
            poLocation.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                poImageInfo = new EImageInfo();
                poImageInfo.setDtlSrcNo(mViewModel.getTransNox());
                poImageInfo.setSourceNo(mViewModel.getTransNox());
                poImageInfo.setImageNme(FileName);
                poImageInfo.setFileLoct(photPath);
                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
            });
        });
        poMessage.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                try {
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(poImageInfo.getFileLoct()));
//                    mViewModel.saveResidenceImageInfo(poImageInfo);
                    mViewModel.saveDataEvaluation(parent,child, Activity_Evaluation.this);
                    new LocationRetriever(Activity_Evaluation.this, Activity_Evaluation.this).getLocation(new LocationRetriever.LocationRetrieveCallback() {
                        @Override
                        public void OnRetrieve(String message, double latitude, double longitude) {
                            poImageInfo.setLatitude(String.valueOf(latitude));
                            poImageInfo.setLongitud(String.valueOf(longitude));
                            boolean isPrimary = parent.getParentDescript().equalsIgnoreCase("Primary Address");
                            mViewModel.SaveImageInfo(poImageInfo, isPrimary, new EvaluatorManager.OnActionCallback() {
                                @Override
                                public void OnSuccess(String args) {
                                    Toast.makeText(Activity_Evaluation.this, "Image Save!", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void OnFailed(String message) {
                                    Toast.makeText(Activity_Evaluation.this, "Failed saving image. " + message, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void sendCIEvaluation(){
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

}