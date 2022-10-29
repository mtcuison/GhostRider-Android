package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ReviewLoanApp extends AppCompatActivity {

    private String TransNox;
    private TextView lblClientNm;
    private ListView recyclerView;
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
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_loan_app);
        initWidgets();
        json();
    }

    private void json() {
        Intent receiveIntent = getIntent();
        String param = receiveIntent.getStringExtra("params");
        try {
            JSONObject object = new JSONObject(param);

            ArrayList<String> items = new ArrayList<>();
            items.add(object.getString("sEmployedx") + " " + object.getString("sSEmploydx"));
            items.add(object.getString("sPensionxx"));
            items.add(object.getString("sFinancexx"));
            items.add(object.getString("sPensionxx"));
            items.add(object.getString("sPensionxx"));
            items.add(object.getString("sPensionxx"));

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, items);
            recyclerView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}