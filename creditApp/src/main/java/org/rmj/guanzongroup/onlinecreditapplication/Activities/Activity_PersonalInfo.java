package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.MobileNo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Activity_PersonalInfo extends AppCompatActivity {
    private static final String TAG = Activity_PersonalInfo.class.getSimpleName();

    private VMPersonalInfo mViewModel;
    private MobileNo[] poMobile = new MobileNo[3];

    private MessageBox poMessage;

    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt,
            txtMothNm, txtMobileNo1, txtMobileNo2, txtMobileNo3, txtMobileYr1, txtMobileYr2,
            txtMobileYr3, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount;
    private TextInputLayout tilMothNm, tilMobileYr1, tilMobileYr2, tilMobileYr3;
    private AutoCompleteTextView txtTown, txtCitizen;
    private RadioGroup rgGender;
    private AutoCompleteTextView spnCivilStatus;
    private MaterialButton btnNext;

    private TextInputEditText[] txtMobileNo;
    private AutoCompleteTextView[] txtMobileType;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPersonalInfo.class);
        poMessage = new MessageBox(Activity_PersonalInfo.this);
        setContentView(R.layout.activity_personal_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());

        mViewModel.GetApplication().observe(Activity_PersonalInfo.this, app -> {
            try{
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        Personal loDetail = (Personal) args;

                    }
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });


        mViewModel.GetTownProvinceList().observe(Activity_PersonalInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try{
                    ArrayList<String> strings = new ArrayList<>();
                    for(int x = 0; x < loList.size(); x++){
                        String lsTowns = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                        strings.add(lsTowns);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_PersonalInfo.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                    txtTown.setAdapter(adapter);
                    txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

                    txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtTown.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setBrthPlce(loList.get(x).sTownIDxx);
                                    mViewModel.getModel().setBirthPlc(lsLabel);
                                    break;
                                }
                            }
                        }
                    });

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetCountryList().observe(Activity_PersonalInfo.this, new Observer<List<ECountryInfo>>() {
            @Override
            public void onChanged(List<ECountryInfo> loList) {
                try{
                    ArrayList<String> strings = new ArrayList<>();
                    for(int x = 0; x < loList.size(); x++){
                        strings.add(loList.get(x).getNational());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_PersonalInfo.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
                    txtCitizen.setAdapter(adapter);
                    txtCitizen.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

                    txtCitizen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).getNational();
                                String lsSlctd = txtCitizen.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setCitizenx(loList.get(x).getCntryCde());
                                    mViewModel.getModel().setCtznShip(loList.get(x).getNational());
                                    break;
                                }
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(Activity_PersonalInfo.this, android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.CIVIL_STATUS);
        spnCivilStatus.setAdapter(loAdapter);
        spnCivilStatus.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        loAdapter = new ArrayAdapter<>(Activity_PersonalInfo.this, android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        txtMobileType[0].setAdapter(loAdapter);
        txtMobileType[0].setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        txtMobileType[1].setAdapter(loAdapter);
        txtMobileType[1].setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        txtMobileType[2].setAdapter(loAdapter);
        txtMobileType[2].setDropDownBackgroundResource(R.drawable.bg_gradient_light);

        txtMobileType[0].setOnItemClickListener((parent, view, position, id) -> {
            //TODO: Replace the spinner(dropdown list) for selection of mobile number type into check box with label 'PostPaid'
            // The default value for mobile no type will be prepaid if not check.
//            pnMobile[0] = position;
            if(position == 1) {
                tilMobileYear[0].setVisibility(View.VISIBLE);
            } else {
                tilMobileYear[0].setVisibility(View.GONE);
            }
        });
        txtMobileType[1].setOnItemClickListener((parent, view, position, id) -> {
            //TODO: Replace the spinner(dropdown list) for selection of mobile number type into check box with label 'PostPaid'
            // The default value for mobile no type will be prepaid if not check.
//            pnMobile[1] = position;
            if(position == 1) {
                tilMobileYear[1].setVisibility(View.VISIBLE);
            } else {
                tilMobileYear[1].setVisibility(View.GONE);
            }
        });
        txtMobileType[2].setOnItemClickListener((parent, view, position, id) -> {
            //TODO: Replace the spinner(dropdown list) for selection of mobile number type into check box with label 'PostPaid'
            // The default value for mobile no type will be prepaid if not check.
//            pnMobile[2] = position;
            if(position == 1) {
                tilMobileYear[2].setVisibility(View.VISIBLE);
            } else {
                tilMobileYear[2].setVisibility(View.GONE);
            }
        });

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_male) {
                mViewModel.getModel().setGender("0");
            }
            if (i == R.id.rb_female) {
                mViewModel.getModel().setGender("1");
            }
            if (i == R.id.rb_lgbt) {
                mViewModel.getModel().setGender("2");
            }
            SetupMaidenEntry();
        });

        spnCivilStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setCvlStats(String.valueOf(position));
                SetupMaidenEntry();
            }
        });

        btnNext.setOnClickListener(v -> SavePersonalInfo());


//    @SuppressLint("NewApi")
//    public void setUpFieldsFromLocalDB(ECreditApplicantInfo credits) throws JSONException {
//        if (credits.getApplInfo() != null) {
//            try {
//
//                JSONObject appInfo = new JSONObject(credits.getApplInfo());
//                txtLastNm.setText(appInfo.getString("sLastName"));
//                txtFrstNm.setText(appInfo.getString("sFrstName"));
//                txtMiddNm.setText(appInfo.getString("sMiddName"));
//                txtSuffixx.setText(appInfo.getString("sSuffixNm"));
//                txtNickNm.setText(appInfo.getString("sNickName"));
//                txtBirthDt.setText(appInfo.getString("dBirthDte"));
//
//                spnCivilStatus.setText(CreditAppConstants.CIVIL_STATUS[Integer.parseInt(appInfo.getString("cCvilStat"))], false);
//                spnCivilStatus.setSelection(Integer.parseInt(appInfo.getString("cCvilStat")));
//                mViewModel.setCvlStats(appInfo.getString("cCvilStat"));
//                mViewModel.getTownProvinceByTownID(appInfo.getString("sBirthPlc")).observe(Activity_PersonalInfo.this, townProvinceInfo -> {
//                    txtTown.setText(townProvinceInfo.sTownName);
//                    txtProvince.setText(townProvinceInfo.sProvName);
//                    mViewModel.setTownID(townProvinceInfo.sTownIDxx);
//                    mViewModel.setProvID(townProvinceInfo.sProvIDxx);
//                });
//                mViewModel.getClientCitizenship(appInfo.getString("sCitizenx")).observe(Activity_PersonalInfo.this, citizen -> {
//                    txtCitizen.setText(citizen);
//                    try {
//                        mViewModel.setCitizenship(appInfo.getString("sCitizenx"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                });
//                for (int i = 0; i < rgGender.getChildCount(); i++) {
//                    if (i == Integer.parseInt(appInfo.getString("cGenderCd"))) {
//                        mViewModel.setGender(appInfo.getString("cGenderCd"));
//                        ((RadioButton) rgGender.getChildAt(i)).setChecked(true);
//                    }
//                }
//
//
//                JSONArray arrayEmail = new JSONArray(appInfo.getString("email_address"));
//                JSONObject emailObj = new JSONObject(arrayEmail.get(0).toString());
//                txtEmailAdd.setText(emailObj.getString("sEmailAdd"));
//                JSONArray arrayContact = new JSONArray(appInfo.getString("mobile_number"));
//                for (int j = 0; j < arrayContact.length(); j++) {
//                    JSONObject contact = new JSONObject(arrayContact.get(j).toString());
//                    txtMobileNo[j].setText(contact.getString("sMobileNo"));
//                    txtMobileYear[j].setText(contact.getInt("nPostYear") + "");
//                    txtMobileType[j].setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(contact.getString("cPostPaid"))], false);
//                    psMobNetTp[j] = contact.getString("cPostPaid");
//                    if (contact.getString("cPostPaid").equalsIgnoreCase("0")) {
//                        tilMobileYear[j].setVisibility(View.GONE);
//                        txtMobileType[j].setSelection(0);
//                    } else {
//                        tilMobileYear[j].setVisibility(View.VISIBLE);
//                        txtMobileType[j].setSelection(1);
//                    }
//
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else {
//            txtLastNm.getText().clear();
//            txtFrstNm.getText().clear();
//            txtMiddNm.getText().clear();
//            txtSuffixx.getText().clear();
//            txtNickNm.getText().clear();
//            txtBirthDt.getText().clear();
//            txtCitizen.getText().clear();
//            txtTown.getText().clear();
//            txtProvince.getText().clear();
//            rgGender.clearCheck();
//            spnCivilStatus.getText().clear();
//            txtMobileNo[0].getText().clear();
//            txtMobileNo[1].getText().clear();
//            txtMobileNo[2].getText().clear();
//            txtMobileType[0].getText().clear();
//            txtMobileType[1].getText().clear();
//            txtMobileType[2].getText().clear();
//            txtMobileYear[0].getText().clear();
//            txtMobileYear[1].getText().clear();
//            txtMobileYear[2].getText().clear();
//            txtEmailAdd.getText().clear();
//            txtTellNox.getText().clear();
//            txtViberAccount.getText().clear();
//            txtFbAccount.getText().clear();
//        }
//    }
    }

    private void SavePersonalInfo() {
        mViewModel.getModel().setLastName(Objects.requireNonNull(txtLastNm.getText()).toString());
        mViewModel.getModel().setFrstName(Objects.requireNonNull(txtFrstNm.getText()).toString());
        mViewModel.getModel().setMiddName(Objects.requireNonNull(txtMiddNm.getText()).toString());
        mViewModel.getModel().setSuffix(Objects.requireNonNull(txtSuffixx.getText()).toString());
        mViewModel.getModel().setNickName(Objects.requireNonNull(txtNickNm.getText()).toString());
        mViewModel.getModel().setMotherNm(Objects.requireNonNull(txtMothNm.getText()).toString());

        if (txtMobileNo[0] != null || !Objects.requireNonNull(txtMobileNo[0].getText()).toString().trim().isEmpty()) {
            poMobile[0] = new MobileNo();
            poMobile[0].setMobileNo(txtMobileNo[0].getText().toString());
//            loMobile[1].setIsPostPd();
            if(poMobile[0].getIsPostPd().equalsIgnoreCase("1")) {
                poMobile[0].setPostYear(Integer.parseInt(txtMobileYear[0].getText().toString()));
            }
            mViewModel.getModel().setMobileNo1(poMobile[0]);
        }
        if (!Objects.requireNonNull(txtMobileNo[1].getText()).toString().trim().isEmpty()) {
            poMobile[1] = new MobileNo();
            poMobile[1].setMobileNo(txtMobileNo[1].getText().toString());
//            loMobile[1].setIsPostPd();
            if(poMobile[1].getIsPostPd().equalsIgnoreCase("1")) {
                poMobile[1].setPostYear(Integer.parseInt(txtMobileYear[1].getText().toString()));
            }
            mViewModel.getModel().setMobileNo2(poMobile[1]);
        }
        if (!Objects.requireNonNull(txtMobileNo[2].getText()).toString().trim().isEmpty()) {
            poMobile[2] = new MobileNo();
            poMobile[2].setMobileNo(txtMobileNo[2].getText().toString());
//            poMobile[2].setIsPostPd();
            if(poMobile[2].getIsPostPd().equalsIgnoreCase("1")) {
                poMobile[2].setPostYear(Integer.parseInt(txtMobileYear[2].getText().toString()));
            }
            mViewModel.getModel().setMobileNo3(poMobile[2]);
        }

        mViewModel.getModel().setPhoneNox(Objects.requireNonNull(txtTellNox.getText()).toString());
        mViewModel.getModel().setEmailAdd(Objects.requireNonNull(txtEmailAdd.getText()).toString());
        mViewModel.getModel().setFbAccntx(Objects.requireNonNull(txtFbAccount.getText()).toString());
        mViewModel.getModel().setVbrAccnt(Objects.requireNonNull(txtViberAccount.getText()).toString());
        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_PersonalInfo.this, Activity_ResidenceInfo.class);
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

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Guanzon Group OACS");

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

        txtTown = findViewById(R.id.txt_bpTown);
        txtCitizen = findViewById(R.id.txt_citizenship);
        rgGender = findViewById(R.id.rg_gender);
        spnCivilStatus = findViewById(R.id.spn_civilStatus);

        txtBirthDt.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_PersonalInfo.this,
                    android.R.style.Theme_Holo_Dialog,  (view131, year, monthOfYear, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String lsDate = dateFormatter.format(newDate.getTime());
                    txtBirthDt.setText(lsDate);
                    Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                    lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                    Log.d(TAG, "Save formatted time: " + lsDate);
                    mViewModel.getModel().setBrthDate(lsDate);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(new Date().getTime());
            StartTime.show();
        });

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
    }

    private void SetupMaidenEntry(){
        if(mViewModel.getModel().getGender().equalsIgnoreCase("1")) {
            if (mViewModel.getModel().getCvlStats().equalsIgnoreCase("1") ||
                    mViewModel.getModel().getCvlStats().equalsIgnoreCase("3")) {
                tilMothNm.setVisibility(View.VISIBLE);
            } else {
                tilMothNm.setVisibility(View.GONE);
            }
        } else {
            tilMothNm.setVisibility(View.GONE);
        }
    }
}