package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.util.Objects;

public class Activity_PersonalInfo extends AppCompatActivity {
    public static final String TAG = Activity_PersonalInfo.class.getSimpleName();

    private VMPersonalInfo mViewModel;
    private PersonalInfoModel infoModel;

    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt,
            txtMothNm, txtMobileNo1, txtMobileNo2, txtMobileNo3, txtMobileYr1, txtMobileYr2,
            txtMobileYr3, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount;
    private TextInputLayout tilMothNm, tilMobileYr1, tilMobileYr2, tilMobileYr3;
    private AutoCompleteTextView txtProvince, txtTown, txtCitizen, spnCivilStatus, spnMobile1,
            spnMobile2, spnMobile3;
    private RadioGroup rgGender;
    private MaterialButton btnNext;
    private Toolbar toolbar;

    private final String psMob1NetTp = "-1";
    private final String psMob2NetTp = "-1";
    private final String psMob3NetTp = "-1";
    private String sGender = "";

    private String transnox;
    private TextInputEditText[] txtMobileNo;
    private AutoCompleteTextView[] txtMobileType;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;

    private String[] psMobNetTp;
//    private JSONObject personInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initWidgets();

        try {
            JSONObject object = new JSONObject();
            object.put("stxtLastNm", txtLastNm.getText().toString().trim());
            object.put("stxtFrstNm", txtFrstNm.getText().toString().trim());
            object.put("stxtMiddNm", txtMiddNm.getText().toString().trim());
            object.put("stxtSuffixx", txtSuffixx.getText().toString().trim());
            object.put("stxtNickNm", txtNickNm.getText().toString().trim());
            object.put("stxtBirthDt", txtBirthDt.getText().toString().trim());
            object.put("stxtProvince", txtProvince.getText().toString().trim());
            object.put("stxtTown", txtTown.getText().toString().trim());

            object.put("srgGender", sGender.trim());

            object.put("sspnCivilStatus", spnCivilStatus.getText().toString().trim());
            object.put("stxtCitizen", txtCitizen.getText().toString().trim());
            object.put("stxtMothNm", txtMothNm.getText().toString().trim());

            object.put("stxtMobileNo1", txtMobileNo[0].getText().toString().trim());
            object.put("stxtMobileNo2", txtMobileNo[1].getText().toString().trim());
            object.put("stxtMobileNo3", txtMobileNo[2].getText().toString().trim());

            object.put("stxtMobileType1", txtMobileType[0].getText().toString().trim());
            object.put("stxtMobileType2", txtMobileType[1].getText().toString().trim());
            object.put("stxtMobileType3", txtMobileType[2].getText().toString().trim());

            object.put("stxtTellNox", txtTellNox.getText().toString().trim());
            object.put("stxtEmailAdd", txtEmailAdd.getText().toString().trim());
            object.put("stxtFbAccount", txtFbAccount.getText().toString().trim());
            object.put("stxtViberAccount", txtViberAccount.getText().toString().trim());

            btnNext.setOnClickListener(v -> {
                Intent intent = new Intent(Activity_PersonalInfo.this, Activity_ResidenceInfo.class);
                intent.putExtra("params", object.toString());
                startActivity(intent);
                finish();
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Personal Info");

        txtLastNm = findViewById(R.id.txt_lastname);
        txtFrstNm = findViewById(R.id.txt_firstname);
        txtMiddNm = findViewById(R.id.txt_middname);
        txtSuffixx = findViewById(R.id.txt_suffix);
        txtNickNm = findViewById(R.id.txt_nickname);
        txtBirthDt = findViewById(R.id.txt_birthdate);

        tilMothNm = findViewById(R.id.til_motherNme);
        txtMothNm = findViewById(R.id.txt_motherNme);

        txtTellNox = findViewById(R.id.txt_telephoneNo);
        txtEmailAdd = findViewById(R.id.txt_emailAdd);
        txtFbAccount = findViewById(R.id.txt_fbAccount);
        txtViberAccount = findViewById(R.id.txt_viberAccount);

        txtProvince = findViewById(R.id.txt_bpProvince);
        txtTown = findViewById(R.id.txt_bpTown);
        txtCitizen = findViewById(R.id.txt_citizenship);
        rgGender = findViewById(R.id.rg_gender);
        spnCivilStatus = findViewById(R.id.spn_civilStatus);

        txtBirthDt.addTextChangedListener(new OnDateSetListener(txtBirthDt));

        btnNext = findViewById(R.id.btn_creditAppNext);

        txtMobileNo = new TextInputEditText[]{
                findViewById(R.id.txt_mobileNo1),
                findViewById(R.id.txt_mobileNo2),
                findViewById(R.id.txt_mobileNo3),
        };
        tilMobileYear = new TextInputLayout[]{
                findViewById(R.id.til_mobileNo1Year),
                findViewById(R.id.til_mobileNo2Year),
                findViewById(R.id.til_mobileNo3Year)
        };
        txtMobileYear = new TextInputEditText[]{
                findViewById(R.id.txt_mobileNo1Year),
                findViewById(R.id.txt_mobileNo2Year),
                findViewById(R.id.txt_mobileNo3Year)
        };
        txtMobileType = new AutoCompleteTextView[]{
                findViewById(R.id.spn_mobile1Type),
                findViewById(R.id.spn_mobile2Type),
                findViewById(R.id.spn_mobile3Type)
        };
        psMobNetTp = new String[]{
                "-1",
                "-1",
                "-1"
        };

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_male) {
                sGender = "0";
            }
            if (i == R.id.rb_female) {
                sGender = "1";
            }
            if (i == R.id.rb_lgbt) {
                sGender = "2";
            }
        });

        spnCivilStatus.setAdapter(new ArrayAdapter<>(Activity_PersonalInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.CIVIL_STATUS));
        spnCivilStatus.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        for (int i = 0; i < txtMobileType.length; i++ ){
            txtMobileType[i].setAdapter(new ArrayAdapter<>(Activity_PersonalInfo.this,
                    android.R.layout.simple_list_item_1, CreditAppConstants.MOBILE_NO_TYPE));
            txtMobileType[i].setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        }




    }
}