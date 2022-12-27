package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
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
//    private MessageBox poMessage;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_loan_app);
        initWidgets();



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