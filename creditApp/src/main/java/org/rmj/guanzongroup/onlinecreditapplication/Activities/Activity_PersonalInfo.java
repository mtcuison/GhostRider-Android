package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;

import java.util.Objects;

public class Activity_PersonalInfo extends AppCompatActivity implements ViewModelCallBack {

    private VMPersonalInfo mViewModel;
    private PersonalInfoModel infoModel;
    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt,
            txtMothNm, txtMobileNo1, txtMobileNo2, txtMobileNo3, txtMobileYr1, txtMobileYr2,
            txtMobileYr3, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount;
    private TextInputLayout tilMothNm, tilMobileYr1, tilMobileYr2, tilMobileYr3;
    private AutoCompleteTextView txtProvince, txtTown, txtCitizen;
    private RadioGroup rgGender;
    private AutoCompleteTextView spnCivilStatus, spnMobile1, spnMobile2, spnMobile3;

    private String psMob1NetTp = "-1";
    private String psMob2NetTp = "-1";
    private String psMob3NetTp = "-1";

    private final String transnox = "";
    private TextInputEditText[] txtMobileNo;
    private AutoCompleteTextView[] txtMobileType;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;
    private Toolbar toolbar;

    private String[] psMobNetTp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        initWidgets();

        mViewModel = new ViewModelProvider(this).get(VMPersonalInfo.class);
        mViewModel.setTransNox(transnox);

        mViewModel.getCreditApplicantInfo().observe(Activity_PersonalInfo.this, eCreditApplicantInfo -> {

            try {
                mViewModel.setGOCasDetailInfo(eCreditApplicantInfo);
                setUpFieldsFromLocalDB(eCreditApplicantInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getPersonalInfoModel().observe(Activity_PersonalInfo.this, personalInfoModel -> infoModel = personalInfoModel);

        mViewModel.getProvinceNameList().observe(Activity_PersonalInfo.this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_PersonalInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtProvince.setAdapter(adapter);
            txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtProvince.setOnItemClickListener((adapterView, view, i, l) ->
                mViewModel.getProvinceInfoList().observe(Activity_PersonalInfo.this, provinceInfos -> {
                    for (int x = 0; x < provinceInfos.size(); x++) {
                        if (txtProvince.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())) {
                            mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                            break;
                        }
                    }

                    mViewModel.getAllTownNames().observe(Activity_PersonalInfo.this, strings -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
                        txtTown.setAdapter(adapter);
                        txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    });
                }));

        txtTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(Activity_PersonalInfo.this, townInfoList -> {
            for (int x = 0; x < townInfoList.size(); x++) {
                if (txtTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())) {
                    mViewModel.setTownID(townInfoList.get(x).getTownIDxx());
                    break;
                }
            }
        }));

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_male) {
                mViewModel.setGender("0");
            }
            if (i == R.id.rb_female) {
                mViewModel.setGender("1");
            }
            if (i == R.id.rb_lgbt) {
                mViewModel.setGender("2");
            }
        });

        mViewModel.getCivilStatus().observe(Activity_PersonalInfo.this, stringArrayAdapter -> {
            spnCivilStatus.setAdapter(stringArrayAdapter);
            spnCivilStatus.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.setMotherMaidenNameVisibility().observe(Activity_PersonalInfo.this, integer -> tilMothNm.setVisibility(integer));

        spnCivilStatus.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.setCvlStats(String.valueOf(i)));

        mViewModel.getAllCountryCitizenNames().observe(Activity_PersonalInfo.this, strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, strings);
            txtCitizen.setAdapter(adapter);
            txtCitizen.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        txtCitizen.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(Activity_PersonalInfo.this, eCountryInfos -> {
            for (int x = 0; x < eCountryInfos.size(); x++) {
                if (txtCitizen.getText().toString().equalsIgnoreCase(eCountryInfos.get(x).getNational())) {
                    mViewModel.setCitizenship(eCountryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));

        mViewModel.getMobileNoType().observe(Activity_PersonalInfo.this, stringArrayAdapter -> {
            for (AutoCompleteTextView autoCompleteTextView : txtMobileType) {
                autoCompleteTextView.setAdapter(stringArrayAdapter);
                autoCompleteTextView.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            }
        });
        for (int i = 0; i < txtMobileType.length; i++ ){
            txtMobileType[i].setOnItemClickListener(new Activity_PersonalInfo.OnItemClickListener(txtMobileType[i]));
        }
    }

    @SuppressLint("NewApi")
    public void setUpFieldsFromLocalDB(ECreditApplicantInfo credits) throws JSONException {
        if (credits.getApplInfo() != null) {
            try {

                JSONObject appInfo = new JSONObject(credits.getApplInfo());
                txtLastNm.setText(appInfo.getString("sLastName"));
                txtFrstNm.setText(appInfo.getString("sFrstName"));
                txtMiddNm.setText(appInfo.getString("sMiddName"));
                txtSuffixx.setText(appInfo.getString("sSuffixNm"));
                txtNickNm.setText(appInfo.getString("sNickName"));
                txtBirthDt.setText(appInfo.getString("dBirthDte"));

                spnCivilStatus.setText(CreditAppConstants.CIVIL_STATUS[Integer.parseInt(appInfo.getString("cCvilStat"))], false);
                spnCivilStatus.setSelection(Integer.parseInt(appInfo.getString("cCvilStat")));
                mViewModel.setCvlStats(appInfo.getString("cCvilStat"));
                mViewModel.getTownProvinceByTownID(appInfo.getString("sBirthPlc")).observe(Activity_PersonalInfo.this, townProvinceInfo -> {
                    txtTown.setText(townProvinceInfo.sTownName);
                    txtProvince.setText(townProvinceInfo.sProvName);
                    mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                    mViewModel.setProvID(townProvinceInfo.sProvIDxx);
                });
                mViewModel.getClientCitizenship(appInfo.getString("sCitizenx")).observe(Activity_PersonalInfo.this, citizen -> {
                    txtCitizen.setText(citizen);
                    try {
                        mViewModel.setCitizenship(appInfo.getString("sCitizenx"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                for (int i = 0; i < rgGender.getChildCount(); i++) {
                    if (i == Integer.parseInt(appInfo.getString("cGenderCd"))) {
                        mViewModel.setGender(appInfo.getString("cGenderCd"));
                        ((RadioButton) rgGender.getChildAt(i)).setChecked(true);
                    }
                }


                JSONArray arrayEmail = new JSONArray(appInfo.getString("email_address"));
                JSONObject emailObj = new JSONObject(arrayEmail.get(0).toString());
                txtEmailAdd.setText(emailObj.getString("sEmailAdd"));
                JSONArray arrayContact = new JSONArray(appInfo.getString("mobile_number"));
                for (int j = 0; j < arrayContact.length(); j++) {
                    JSONObject contact = new JSONObject(arrayContact.get(j).toString());
                    txtMobileNo[j].setText(contact.getString("sMobileNo"));
                    txtMobileYear[j].setText(contact.getInt("nPostYear") + "");
                    txtMobileType[j].setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(contact.getString("cPostPaid"))], false);
                    psMobNetTp[j] = contact.getString("cPostPaid");
                    if (contact.getString("cPostPaid").equalsIgnoreCase("0")) {
                        tilMobileYear[j].setVisibility(View.GONE);
                        txtMobileType[j].setSelection(0);
                    } else {
                        tilMobileYear[j].setVisibility(View.VISIBLE);
                        txtMobileType[j].setSelection(1);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            txtLastNm.getText().clear();
            txtFrstNm.getText().clear();
            txtMiddNm.getText().clear();
            txtSuffixx.getText().clear();
            txtNickNm.getText().clear();
            txtBirthDt.getText().clear();
            txtCitizen.getText().clear();
            txtTown.getText().clear();
            txtProvince.getText().clear();
            rgGender.clearCheck();
            spnCivilStatus.getText().clear();
            txtMobileNo[0].getText().clear();
            txtMobileNo[1].getText().clear();
            txtMobileNo[2].getText().clear();
            txtMobileType[0].getText().clear();
            txtMobileType[1].getText().clear();
            txtMobileType[2].getText().clear();
            txtMobileYear[0].getText().clear();
            txtMobileYear[1].getText().clear();
            txtMobileYear[2].getText().clear();
            txtEmailAdd.getText().clear();
            txtTellNox.getText().clear();
            txtViberAccount.getText().clear();
            txtFbAccount.getText().clear();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("sample", infoModel);
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(1);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(this, message, GToast.ERROR).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void SavePersonalInfo() {
        infoModel.setLastName(Objects.requireNonNull(txtLastNm.getText()).toString());
        infoModel.setFrstName(Objects.requireNonNull(txtFrstNm.getText()).toString());
        infoModel.setMiddName(Objects.requireNonNull(txtMiddNm.getText()).toString());
        infoModel.setSuffix(Objects.requireNonNull(txtSuffixx.getText()).toString());
        infoModel.setNickName(Objects.requireNonNull(txtNickNm.getText()).toString());
        infoModel.setBrthDate(Objects.requireNonNull(txtBirthDt.getText()).toString());
        infoModel.setBrthPlce(Objects.requireNonNull(txtProvince.getText()).toString());
        infoModel.setMotherNm(Objects.requireNonNull(txtMothNm.getText()).toString());
        infoModel.clearMobileNo();
        if (txtMobileNo[0] != null || !Objects.requireNonNull(txtMobileNo[0].getText()).toString().trim().isEmpty()) {
            if (Integer.parseInt(psMob1NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[0].getText().toString(), psMobNetTp[0], Integer.parseInt(Objects.requireNonNull(txtMobileYear[0].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[0].getText().toString(), psMobNetTp[0], 0);
            }
        }
        if (!Objects.requireNonNull(txtMobileNo[1].getText()).toString().trim().isEmpty()) {
            if (Integer.parseInt(psMob2NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[1].getText().toString(), psMobNetTp[1], Integer.parseInt(Objects.requireNonNull(txtMobileYear[1].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[1].getText().toString(), psMobNetTp[1], 0);
            }
        }
        if (!Objects.requireNonNull(txtMobileNo[2].getText()).toString().trim().isEmpty()) {
            if (Integer.parseInt(psMob3NetTp) == 1) {
                infoModel.setMobileNo(txtMobileNo[2].getText().toString(), psMobNetTp[2], Integer.parseInt(Objects.requireNonNull(txtMobileYear[2].getText()).toString()));
            } else {
                infoModel.setMobileNo(txtMobileNo[2].getText().toString(), psMobNetTp[2], 0);
            }
        }
        infoModel.setPhoneNox(Objects.requireNonNull(txtTellNox.getText()).toString());
        infoModel.setEmailAdd(Objects.requireNonNull(txtEmailAdd.getText()).toString());
        infoModel.setFbAccntx(Objects.requireNonNull(txtFbAccount.getText()).toString());
        infoModel.setVbrAccnt(Objects.requireNonNull(txtViberAccount.getText()).toString());
        mViewModel.SavePersonalInfo(infoModel, Activity_PersonalInfo.this);
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (txtMobileType[0].equals(poView)) {
                psMob1NetTp = String.valueOf(i);
                psMobNetTp[0] = String.valueOf(i);
                if (i == 1) {
                    tilMobileYear[0].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[0].setVisibility(View.GONE);
                }

            }
            if (txtMobileType[1].equals(poView)) {
                psMob2NetTp = String.valueOf(i);
                psMobNetTp[1] = String.valueOf(i);
                if (i == 1) {
                    tilMobileYear[1].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[1].setVisibility(View.GONE);
                }
            }
            if (txtMobileType[2].equals(poView)) {
                psMob3NetTp = String.valueOf(i);
                psMobNetTp[2] = String.valueOf(i);
                if (i == 1) {
                    tilMobileYear[2].setVisibility(View.VISIBLE);
                } else {
                    tilMobileYear[2].setVisibility(View.GONE);
                }
            }
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
        MaterialButton btnNext = findViewById(R.id.btn_creditAppNext);

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

        btnNext.setOnClickListener(view -> SavePersonalInfo());

    }
}