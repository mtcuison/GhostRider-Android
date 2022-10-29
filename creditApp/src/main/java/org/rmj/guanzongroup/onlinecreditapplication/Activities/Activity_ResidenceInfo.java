package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;

import java.util.Objects;

public class Activity_ResidenceInfo extends AppCompatActivity {

    private VMResidenceInfo mViewModel;
//    private ResidenceInfoModel infoModel;
    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2, txtRelationship,
            txtLgnthStay, txtMonthlyExp, txtPLandMark, txtPHouseNox, txtPAddress1, txtPAddress2;
    private AutoCompleteTextView txtBarangay,
            txtMunicipality, txtProvince, txtPBarangay, txtPMunicipl, txtPProvince;
    private CheckBox cbOneAddress;
    private AutoCompleteTextView spnLgnthStay, spnHouseHold, spnHouseType;
    private String spnLgnthStayPosition = "-1",
            spnHouseHoldPosition = "-1",
            spnHouseTypePosition = "-1";
    private TextInputLayout tilRelationship;
    private LinearLayout lnOtherInfo, lnPermaAddx;
    private Button btnNext;
    private Button btnPrvs;
    private RadioGroup rgOwnsership, rgGarage;

    private String TransNox = "";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ResidenceInfo.this).get(VMResidenceInfo.class);
        setContentView(R.layout.activity_residence_info);
//        initWidgets();
//
//        mViewModel.setTransNox(TransNox);
//        mViewModel.getCreditApplicationInfo().observe(Activity_ResidenceInfo.this, eCreditApplicantInfo -> {
//            mViewModel.setGOCasDetailInfo(eCreditApplicantInfo);
//            try {
//                setFieldDataFromLocalDB(eCreditApplicantInfo);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        });
//
//        mViewModel.getProvinceNameList().observe(Activity_ResidenceInfo.this, strings -> {
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
//            txtProvince.setAdapter(adapter);
//            txtPProvince.setAdapter(adapter);
//            txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//            txtPProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        });
//
//        txtProvince.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) ->
//                mViewModel.getProvinceInfoList().observe(Activity_ResidenceInfo.this, eProvinceInfos -> {
//                    for (int x = 0; x < eProvinceInfos.size(); x++) {
//                        if (txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())) {
//                            mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
//                            infoModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
//                            break;
//                        }
//                    }
//                    mViewModel.getTownNameList().observe(Activity_ResidenceInfo.this, strings -> {
//                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
//                        txtMunicipality.setAdapter(adapter);
//                        txtMunicipality.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                    });
//                }));
//
//        txtMunicipality.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(Activity_ResidenceInfo.this, eTownInfos -> {
//            for (int x = 0; x < eTownInfos.size(); x++) {
//                if (txtMunicipality.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())) {
//                    mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
//                    infoModel.setMunicipalID(eTownInfos.get(x).getTownIDxx());
//                    break;
//                }
//            }
//
//            mViewModel.getBarangayNameList().observe(Activity_ResidenceInfo.this, strings -> {
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
//                txtBarangay.setAdapter(adapter);
//                txtBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//            });
//        }));
//
//        txtBarangay.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBarangayInfoList().observe(Activity_ResidenceInfo.this, eBarangayInfos -> {
//            for (int x = 0; x < eBarangayInfos.size(); x++) {
//                if (txtBarangay.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())) {
//                    mViewModel.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
//                    infoModel.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
//                    break;
//                }
//            }
//        }));
//
//        txtPProvince.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(Activity_ResidenceInfo.this, eProvinceInfos -> {
//            for (int x = 0; x < eProvinceInfos.size(); x++) {
//                if (txtPProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())) {
//                    mViewModel.setPermanentProvinceID(eProvinceInfos.get(x).getProvIDxx());
//                    infoModel.setPermanentProvinceID(eProvinceInfos.get(x).getProvIDxx());
//                    break;
//                }
//            }
//
//            mViewModel.getPermanentTownNameList().observe(Activity_ResidenceInfo.this, strings -> {
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
//                txtPMunicipl.setAdapter(adapter);
//                txtPMunicipl.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//            });
//        }));
//
//        txtPMunicipl.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getPermanentTownInfoList().observe(Activity_ResidenceInfo.this, eTownInfos -> {
//            for (int x = 0; x < eTownInfos.size(); x++) {
//                if (txtPMunicipl.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())) {
//                    mViewModel.setPermanentTownID(eTownInfos.get(x).getTownIDxx());
//                    infoModel.setPermanentMunicipalID(eTownInfos.get(x).getTownIDxx());
//                    break;
//                }
//            }
//
//            mViewModel.getPermanentBarangayNameList().observe(Activity_ResidenceInfo.this, strings -> {
//                ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this, android.R.layout.simple_spinner_dropdown_item, strings);
//                txtPBarangay.setAdapter(adapter);
//                txtPBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//
//            });
//        }));
//
//        txtPBarangay.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getPermanentBarangayInfoList().observe(Activity_ResidenceInfo.this, eBarangayInfos -> {
//            for (int x = 0; x < eBarangayInfos.size(); x++) {
//                if (txtPBarangay.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())) {
//                    mViewModel.setPermanentBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
//                    infoModel.setPermanentBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
//                    break;
//                }
//            }
//        }));
//
//        mViewModel.getHouseHolds().observe(Activity_ResidenceInfo.this, stringArrayAdapter -> {
//            spnHouseHold.setAdapter(stringArrayAdapter);
//            spnHouseHold.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        });
//
//        mViewModel.getHouseType().observe(Activity_ResidenceInfo.this, stringArrayAdapter -> {
//            spnHouseType.setAdapter(stringArrayAdapter);
//            spnHouseType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        });
//
//        mViewModel.getLenghtOfStay().observe(Activity_ResidenceInfo.this, stringArrayAdapter -> {
//            spnLgnthStay.setAdapter(stringArrayAdapter);
//            spnLgnthStay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//        });
//        spnHouseHold.setOnItemClickListener(new OnItemClickListener(spnHouseHold));
//        spnHouseType.setOnItemClickListener(new OnItemClickListener(spnHouseType));
//        spnLgnthStay.setOnItemClickListener(new OnItemClickListener(spnLgnthStay));
//
//        btnNext.setOnClickListener(v -> {
//            SaveResidenceInfo();
//        });
//        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(0));
//
//    }
//
//    private void SaveResidenceInfo() {
//        infoModel.setOneAddress(cbOneAddress.isChecked());
//        infoModel.setLandMark(Objects.requireNonNull(txtLandMark.getText()).toString());
//        infoModel.setHouseNox(Objects.requireNonNull(txtHouseNox.getText()).toString());
//        infoModel.setAddress1(Objects.requireNonNull(txtAddress1.getText()).toString());
//        infoModel.setAddress2(Objects.requireNonNull(txtAddress2.getText()).toString());
//        infoModel.setProvinceNm(txtProvince.getText().toString());
//        infoModel.setMunicipalNm(txtMunicipality.getText().toString());
//        infoModel.setBarangayName(txtBarangay.getText().toString());
//        infoModel.setOwnerRelation(Objects.requireNonNull(txtRelationship.getText()).toString());
//        infoModel.setLenghtOfStay(Objects.requireNonNull(txtLgnthStay.getText()).toString());
//        infoModel.setMonthlyExpenses(Objects.requireNonNull(txtMonthlyExp.getText()).toString());
//        infoModel.setIsYear(spnLgnthStayPosition);
//        infoModel.setPermanentLandMark(Objects.requireNonNull(txtPLandMark.getText()).toString());
//        infoModel.setPermanentHouseNo(Objects.requireNonNull(txtPHouseNox.getText()).toString());
//        infoModel.setPermanentAddress1(Objects.requireNonNull(txtPAddress1.getText()).toString());
//        infoModel.setPermanentAddress2(Objects.requireNonNull(txtPAddress2.getText()).toString());
//        infoModel.setPermanentProvinceNm(txtPProvince.getText().toString());
//        infoModel.setPermanentMunicipalNm(txtPMunicipl.getText().toString());
//        infoModel.setPermanentBarangayName(txtPBarangay.getText().toString());
//        mViewModel.SaveResidenceInfo(infoModel, Activity_ResidenceInfo.this);
//    }
//
//    @Override
//    public void onSaveSuccessResult(String args) {
//        Activity_CreditApplication.getInstance().moveToPageNumber(2);
//    }
//
//    @Override
//    public void onFailedResult(String message) {
//        GToast.CreateMessage(Activity_ResidenceInfo.this, message, GToast.ERROR).show();
//    }
//
//    class OnItemClickListener implements AdapterView.OnItemClickListener {
//        AutoCompleteTextView poView;
//
//        public OnItemClickListener(AutoCompleteTextView view) {
//            this.poView = view;
//        }
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            if (spnHouseHold.equals(poView)) {
//                spnHouseHoldPosition = String.valueOf(i);
//                infoModel.setHouseHold(String.valueOf(i));
//            }
//            if (spnHouseType.equals(poView)) {
//                spnHouseTypePosition = String.valueOf(i);
//                infoModel.setHouseType(spnHouseTypePosition);
//            }
//            if (spnLgnthStay.equals(poView)) {
//                spnLgnthStayPosition = String.valueOf(i);
//            }
//
//        }
//    }
//
//    private class OnHouseOwnershipSelectListener implements RadioGroup.OnCheckedChangeListener {
//
//        @Override
//        public void onCheckedChanged(RadioGroup radioGroup, int i) {
//            if (radioGroup.getId() == R.id.rg_ownership) {
//                if (i == R.id.rb_owned) {
//                    lnOtherInfo.setVisibility(View.GONE);
//                    infoModel.setHouseOwn("0");
//                }
//                if (i == R.id.rb_rent) {
//                    lnOtherInfo.setVisibility(View.VISIBLE);
//                    tilRelationship.setVisibility(View.GONE);
//                    infoModel.setHouseOwn("1");
//                }
//                if (i == R.id.rb_careTaker) {
//                    lnOtherInfo.setVisibility(View.VISIBLE);
//                    tilRelationship.setVisibility(View.VISIBLE);
//                    infoModel.setHouseOwn("2");
//                }
//            } else {
//                if (i == R.id.rb_yes) {
//                    infoModel.setHasGarage("1");
//                }
//                if (i == R.id.rb_no) {
//                    infoModel.setHasGarage("0");
//                }
//            }
//        }
//    }
//
//    private class OnAddressSetListener implements CompoundButton.OnCheckedChangeListener {
//
//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//            if (b) {
//                lnPermaAddx.setVisibility(View.GONE);
//            } else {
//                lnPermaAddx.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public void setFieldDataFromLocalDB(ECreditApplicantInfo credits) throws JSONException {
//        try {
//            if (credits.getResidnce() != null) {
//                cbOneAddress.setChecked(Boolean.parseBoolean(credits.getDetlInfo()));
//                JSONObject residenceObj = new JSONObject(credits.getResidnce());
//                JSONObject presentObj = new JSONObject(residenceObj.getString("present_address"));
//                JSONObject permanentObj = new JSONObject(residenceObj.getString("permanent_address"));
//
//                mViewModel.getBrgyTownProvinceInfoWithID(permanentObj.getString("sBrgyIDxx")).observe(Activity_ResidenceInfo.this, townProvinceInfo -> {
//                    txtPMunicipl.setText(townProvinceInfo.sTownName);
//                    txtPProvince.setText(townProvinceInfo.sProvName);
//                    txtPBarangay.setText(townProvinceInfo.sProvName);
//                    mViewModel.setPermanentProvinceID(townProvinceInfo.sProvIDxx);
//                    mViewModel.setPermanentTownID(townProvinceInfo.sTownIDxx);
//                    mViewModel.setPermanentBarangayID(townProvinceInfo.sBrgyIDxx);
//
//                });
//                mViewModel.getBrgyTownProvinceInfoWithID(presentObj.getString("sBrgyIDxx")).observe(Activity_ResidenceInfo.this, townProvinceInfo -> {
//                    txtMunicipality.setText(townProvinceInfo.sTownName);
//                    txtProvince.setText(townProvinceInfo.sProvName);
//                    txtBarangay.setText(townProvinceInfo.sProvName);
//                    mViewModel.setProvinceID(townProvinceInfo.sProvIDxx);
//                    mViewModel.setTownID(townProvinceInfo.sTownIDxx);
//                    mViewModel.setBarangayID(townProvinceInfo.sBrgyIDxx);
//                });
//                txtLandMark.setText(presentObj.getString("sLandMark"));
//                txtHouseNox.setText(presentObj.getString("sHouseNox"));
//                txtAddress1.setText(presentObj.getString("sAddress1"));
//                txtAddress2.setText(presentObj.getString("sAddress2"));
//
//                txtPLandMark.setText(permanentObj.getString("sLandMark"));
//                txtPHouseNox.setText(permanentObj.getString("sHouseNox"));
//                txtPAddress1.setText(permanentObj.getString("sAddress1"));
//                txtPAddress2.setText(permanentObj.getString("sAddress2"));
//
//                if (residenceObj.getString("cOwnershp").equalsIgnoreCase("0")) {
//                    rgOwnsership.check(R.id.rb_owned);
//                    infoModel.setHouseOwn("0");
//                } else if (residenceObj.getString("cOwnershp").equalsIgnoreCase("1")) {
//                    rgOwnsership.check(R.id.rb_rent);
//                    infoModel.setHouseOwn("1");
//                } else if (residenceObj.getString("cOwnershp").equalsIgnoreCase("2")) {
//                    rgOwnsership.check(R.id.rb_careTaker);
//                    infoModel.setHouseOwn("2");
//                }
//                if (residenceObj.getString("cGaragexx").equalsIgnoreCase("0")) {
//                    rgGarage.check(R.id.rb_no);
//                    infoModel.setHasGarage("0");
//                } else {
//                    rgGarage.check(R.id.rb_yes);
//                    infoModel.setHasGarage("1");
//                }
//                infoModel.setHouseHold(residenceObj.getString("cOwnOther"));
//                spnHouseHold.setText(CreditAppConstants.HOUSEHOLDS[Integer.parseInt(residenceObj.getString("cOwnOther"))]);
//
//                spnHouseType.setText(CreditAppConstants.HOUSE_TYPE[Integer.parseInt(residenceObj.getString("cHouseTyp"))]);
//                infoModel.setHouseType(residenceObj.getString("cHouseTyp"));
//
//            } else {
//                cbOneAddress.setChecked(false);
//                txtLandMark.getText().clear();
//                txtHouseNox.getText().clear();
//                txtAddress1.getText().clear();
//                txtAddress2.getText().clear();
//                txtBarangay.getText().clear();
//                txtMunicipality.getText().clear();
//                txtProvince.getText().clear();
//                txtRelationship.getText().clear();
//                txtLgnthStay.getText().clear();
//                txtMonthlyExp.getText().clear();
//                txtPLandMark.getText().clear();
//                txtPHouseNox.getText().clear();
//                txtPAddress1.getText().clear();
//                txtPAddress2.getText().clear();
//                txtPBarangay.getText().clear();
//                txtPMunicipl.getText().clear();
//                txtPProvince.getText().clear();
//                spnLgnthStay.getText().clear();
//                spnHouseHold.getText().clear();
//                spnHouseType.getText().clear();
//                rgOwnsership.clearCheck();
//                rgGarage.clearCheck();
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void initWidgets() {
//
//        toolbar = findViewById(R.id.toolbar_ResidenceInfo);
//        setSupportActionBar(toolbar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Residence Info");
//
//        cbOneAddress = findViewById(R.id.cb_oneAddress);
//
//        txtLandMark = findViewById(R.id.txt_landmark);
//        txtHouseNox = findViewById(R.id.txt_houseNox);
//        txtAddress1 = findViewById(R.id.txt_address);
//        txtAddress2 = findViewById(R.id.txt_address2);
//        txtBarangay = findViewById(R.id.txt_barangay);
//        txtMunicipality = findViewById(R.id.txt_town);
//        txtProvince = findViewById(R.id.txt_province);
//        txtRelationship = findViewById(R.id.txt_relationship);
//        txtLgnthStay = findViewById(R.id.txt_lenghtStay);
//        txtMonthlyExp = findViewById(R.id.txt_monthlyExp);
//        txtPLandMark = findViewById(R.id.txt_perm_landmark);
//        txtPHouseNox = findViewById(R.id.txt_perm_houseNox);
//        txtPAddress1 = findViewById(R.id.txt_perm_address);
//        txtPAddress2 = findViewById(R.id.txt_perm_address2);
//        txtPBarangay = findViewById(R.id.txt_perm_barangay);
//        txtPMunicipl = findViewById(R.id.txt_perm_town);
//        txtPProvince = findViewById(R.id.txt_perm_province);
//
//        spnLgnthStay = findViewById(R.id.spn_lenghtStay);
//        spnHouseHold = findViewById(R.id.spn_houseHold);
//        spnHouseType = findViewById(R.id.spn_houseType);
//
//        rgOwnsership = findViewById(R.id.rg_ownership);
//        rgGarage = findViewById(R.id.rg_garage);
//
//        tilRelationship = findViewById(R.id.til_relationship);
//
//        lnOtherInfo = findViewById(R.id.linear_otherInfo);
//        lnPermaAddx = findViewById(R.id.linear_permanentAdd);
//
//        cbOneAddress.setOnCheckedChangeListener(new OnAddressSetListener());
//        rgOwnsership.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
//        rgGarage.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
//
//        btnNext = findViewById(R.id.btn_creditAppNext);
//        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

    }
}