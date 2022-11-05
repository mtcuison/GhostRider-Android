package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Financier;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Pension;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;

import java.util.Objects;

public class Activity_PensionInfo extends AppCompatActivity {

    private VMPensionInfo mViewModel ;
    private MessageBox poMessage;

    private AutoCompleteTextView spnSector;
    private String sectorPosition = "-1";
    private TextInputEditText txtRangxx, txtYearxx, txtOthInc, txtRngInc;
    private Button btnPrvs, btnNext;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_PensionInfo.this).get(VMPensionInfo.class);
        setContentView(R.layout.activity_pension_info);
        poMessage = new MessageBox(Activity_PensionInfo.this);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_PensionInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try {
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            Pension loDetail = (Pension) args;
                        }
                    });
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });

        spnSector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setPensionSector(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(v -> SaveResidenceInfo());
    }

    private void SaveResidenceInfo() {

        mViewModel.getModel().setPensionIncomeRange(Long.parseLong(Objects.requireNonNull(txtRangxx.getText()).toString().trim()));
        mViewModel.getModel().setRetirementYear(Objects.requireNonNull(txtYearxx.getText()).toString().trim());
        mViewModel.getModel().setNatureOfIncome(Objects.requireNonNull(txtRngInc.getText()).toString().trim());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_PensionInfo.this, Activity_SpouseInfo.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
            }

            @Override
            public void OnFailed(String message) {
                poMessage.initDialog();
                poMessage.setTitle("Credit Online Application");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                poMessage.show();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }


    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_PensionInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pension Info");


        spnSector = findViewById(R.id.spn_psnSector);

        txtRangxx = findViewById(R.id.txt_psnIncRange);
        txtYearxx = findViewById(R.id.txt_psnRtrmntYear);
        txtOthInc = findViewById(R.id.txt_natureOfIncome);
        txtRngInc = findViewById(R.id.txt_incRange);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

        spnSector.setAdapter(new ArrayAdapter<>(Activity_PensionInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.PENSION_SECTOR));
        spnSector.setDropDownBackgroundResource(R.drawable.bg_gradient_light);



    }
}