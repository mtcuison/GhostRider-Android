package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditAppConstants;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.MobileNo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanAppDetailReviewAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMComakerResidence;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMReviewLoanApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ReviewLoanApp extends AppCompatActivity {

    private String TransNox;
    private TextView lblClientNm;
//    private ListView recyclerView;
    private RecyclerView recyclerView;
    private ImageView imgClient;
    private ImageButton btnCamera;
    private Button btnSave, btnPrvs;

//    private List<ReviewAppDetail> plDetail;
//    private ECreditApplicantInfo poInfo;
//    private ImageFileCreator poCamera;
//    private EImageInfo poImage;
//    private LoadDialog poDialogx;
    private MessageBox poMessage;

    private VMReviewLoanApp mViewModel;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ReviewLoanApp.this).get(VMReviewLoanApp.class);
        poMessage = new MessageBox(Activity_ReviewLoanApp.this);
        setContentView(R.layout.activity_review_loan_app);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());

        mViewModel.GetApplication().observe(Activity_ReviewLoanApp.this, app -> {
            try {
                mViewModel.getModel().GetApplication(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        List<ReviewAppDetail> loDetail =  (List<ReviewAppDetail>) args;
                        try {
                            setUpFieldsFromLocalDB(loDetail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        btnPrvs.setOnClickListener(v -> finish());
    }


    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_ReviewLoanApp);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Review Loan Info");

        lblClientNm = findViewById(R.id.lbl_clientNme);

        recyclerView = findViewById(R.id.recyclerview_applicationInfo);

        imgClient = findViewById(R.id.img_loanApplicant);

        btnCamera = findViewById(R.id.btn_camera);

        btnSave = findViewById(R.id.btn_loanAppSave);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

//        plDetail = new ArrayList<>();
        String lsImageNme = TransNox;
//        poCamera = new ImageFileCreator(this, AppConstants.SUB_FOLDER_CREDIT_APP, lsImageNme);
//        poImage = new EImageInfo();
//        poImage.setImageNme(lsImageNme);
//        poDialogx = new LoadDialog(this);
//        poMessage = new MessageBox(this);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ReviewLoanApp.this, Activity_PersonalInfo.class);
            startActivity(intent);
            finish();
        });
        btnPrvs.setOnClickListener(v -> {
            Intent intent = new Intent(Activity_ReviewLoanApp.this, Activity_ComakerResidence.class);
            startActivity(intent);
            finish();
        });

    }


    @SuppressLint("NewApi")
    public void setUpFieldsFromLocalDB(List<ReviewAppDetail> infoList) throws JSONException {
        LoanAppDetailReviewAdapter loAdapter = new LoanAppDetailReviewAdapter(infoList, () -> {

        });
        LinearLayoutManager loManager = new LinearLayoutManager(Activity_ReviewLoanApp.this);
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);
        recyclerView.setAdapter(loAdapter);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        getViewModelStore().clear();
        super.onDestroy();
    }
}