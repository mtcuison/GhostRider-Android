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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Financier;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMFinancierInfo;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.util.Objects;

public class Activity_Finance extends AppCompatActivity {

    private VMFinancierInfo mViewModel ;
    private AutoCompleteTextView spnRelation;
    private String relationX = "-1";
    private TextInputEditText txtFNamex, txtFIncme, txtFMoble, txtFFacbk, txtFEmail;
    private MessageBox poMessage;
    private AutoCompleteTextView txtFCntry;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Activity_Finance.this).get(VMFinancierInfo.class);
        poMessage = new MessageBox(Activity_Finance.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_Finance.this, app -> {
            try{
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        Financier loDetail = (Financier) args;
                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        spnRelation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setFinancierRelation(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(v -> SavePersonalInfo());

    }

    private void SavePersonalInfo() {

        mViewModel.getModel().setFinancierName(Objects.requireNonNull(txtFNamex.getText()).toString().trim());
        mViewModel.getModel().setRangeOfIncome(Long.parseLong(Objects.requireNonNull(txtFIncme.getText()).toString().trim()));
        mViewModel.getModel().setCountryName(Objects.requireNonNull(txtFCntry.getText()).toString().trim());
        mViewModel.getModel().setMobileNo(Objects.requireNonNull(txtFMoble.getText()).toString().trim());
        mViewModel.getModel().setFacebook(Objects.requireNonNull(txtFFacbk.getText()).toString().trim());
        mViewModel.getModel().setEmail(Objects.requireNonNull(txtFEmail.getText()).toString().trim());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_Finance.this, Activity_PensionInfo.class);
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
        toolbar = findViewById(R.id.toolbar_Finance);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Finance Info");


        spnRelation = findViewById(R.id.spn_financierRelation);

        txtFNamex = findViewById(R.id.txt_financierName);
        txtFIncme = findViewById(R.id.txt_financierInc);
        txtFCntry = findViewById(R.id.txt_financierCntry);
        txtFMoble = findViewById(R.id.txt_financierContact);
        txtFFacbk = findViewById(R.id.txt_financierFb);
        txtFEmail = findViewById(R.id.txt_financierEmail);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

        spnRelation.setAdapter(new ArrayAdapter<>(Activity_Finance.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.FINANCE_SOURCE));
        spnRelation.setDropDownBackgroundResource(R.drawable.bg_gradient_light);


    }
}