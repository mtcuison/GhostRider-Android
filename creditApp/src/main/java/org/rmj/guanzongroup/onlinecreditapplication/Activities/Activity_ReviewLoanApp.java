package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_ReviewLoanApp extends AppCompatActivity {

    private String TransNox;
    private TextView lblClientNm;
    private RecyclerView recyclerView;
    private ImageView imgClient;
    private ImageButton btnCamera;
    private Button btnSave, btnPrvs;

    private List<ReviewAppDetail> plDetail;
    private ECreditApplicantInfo poInfo;
    private ImageFileCreator poCamera;
    private EImageInfo poImage;
    private LoadDialog poDialogx;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_loan_app);
        initWidgets();
    }

    private void initWidgets() {
        lblClientNm = findViewById(R.id.lbl_clientNme);

        recyclerView = findViewById(R.id.recyclerview_applicationInfo);

        imgClient = findViewById(R.id.img_loanApplicant);

        btnCamera = findViewById(R.id.btn_camera);
        btnSave = findViewById(R.id.btn_loanAppSave);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        plDetail = new ArrayList<>();
        String lsImageNme = TransNox;
        poCamera = new ImageFileCreator(this, AppConstants.SUB_FOLDER_CREDIT_APP, lsImageNme);
        poImage = new EImageInfo();
        poImage.setImageNme(lsImageNme);
        poDialogx = new LoadDialog(this);
        poMessage = new MessageBox(this);

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