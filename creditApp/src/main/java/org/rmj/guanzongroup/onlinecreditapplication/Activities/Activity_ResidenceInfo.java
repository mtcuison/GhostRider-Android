package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ResidenceInfo extends AppCompatActivity {

    private VMResidenceInfo mViewModel;
    private MessageBox poMessage;

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
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_ResidenceInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try {
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            ClientResidence loDetail = (ClientResidence) args;
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mViewModel.GetTownProvinceList().observe(Activity_ResidenceInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try {
                    ArrayList<String> provAdapter = new ArrayList<>();
                    for (int x = 0; x < loList.size(); x++){
                        String lsProv = loList.get(x).sProvName;
                        provAdapter.add(lsProv);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this,
                            android.R.layout.simple_spinner_dropdown_item,provAdapter.toArray(new String[0]));
                    txtProvince.setAdapter(adapter);
                    txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    txtProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0 ; x < loList.size() ; x++){
                                String lsLabel = loList.get(x).sProvName;
                                String lsSlctd = txtProvince.getText().toString().trim();
                                if( lsSlctd.equalsIgnoreCase(lsLabel)){
                                    mViewModel.getModel().setProvinceID(loList.get(x).sProvIDxx);
                                    mViewModel.getModel().setProvinceNm(lsLabel);
                                }
                            }
//                            mViewModel.GetTownProvinceList().observe(Activity_ResidenceInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
//                                @Override
//                                public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
//                                        ArrayList<String> strings = new ArrayList<>();
//                                        for(int x = 0 ; x < loList.size(); x++){
//                                            String lsTown = loList.get(x).sTownName;
//                                            strings.add(lsTown);
//                                        }
//
//                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_ResidenceInfo.this,
//                                                android.R.layout.simple_spinner_dropdown_item,strings.toArray(new String[0]));
//                                        txtMunicipality.setAdapter(adapter);
//                                        txtMunicipality.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                                        txtMunicipality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                for (int x = 0 ; x < loList.size() ; x++){
//                                                    String lsLabel = loList.get(x).sTownName;
//                                                    String lsSlctd = txtMunicipality.getText().toString().trim();
//                                                    if( lsSlctd.equalsIgnoreCase(lsLabel)){
//                                                        mViewModel.getModel().setMunicipalID(loList.get(x).sTownIDxx);
//                                                        mViewModel.getModel().setMunicipalNm(lsLabel);
//                                                    }
//                                                }
//
//                                                mViewModel.GetBarangayList(mViewModel.getModel().getMunicipalID()).observe(Activity_ResidenceInfo.this, new Observer<List<EBarangayInfo>>() {
//                                                    @Override
//                                                    public void onChanged(List<EBarangayInfo> BrgyList) {
//                                                        ArrayList<String> string = new ArrayList<>();
//                                                        for(int x = 0 ; x < BrgyList.size(); x++) {
//                                                            String lsBrgy = BrgyList.get(x).getBrgyName();
//                                                            string.add(lsBrgy);
//                                                        }
//                                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_ResidenceInfo.this,
//                                                                android.R.layout.simple_spinner_dropdown_item,string.toArray(new String[0]));
//                                                        txtBarangay.setAdapter(adapters);
//                                                        txtBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                                                        txtBarangay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                                            @Override
//                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                                for (int x = 0 ; x < BrgyList.size() ; x++){
//                                                                    String lsLabel = BrgyList.get(x).getBrgyName();
//                                                                    String lsSlctd = txtBarangay.getText().toString().trim();
//                                                                    if( lsSlctd.equalsIgnoreCase(lsLabel)){
//                                                                        mViewModel.getModel().setBarangayID(BrgyList.get(x).getBrgyIDxx());
//                                                                        mViewModel.getModel().setBarangayName(lsLabel);
//                                                                    }
//                                                                }
//                                                            }
//                                                        });
//                                                    }
//                                                });
//                                            }
//                                        });
//
//
//                                }
//                            });
                        }
                    });

                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });

//

        spnHouseHold.setAdapter(new ArrayAdapter<>(Activity_ResidenceInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.HOUSEHOLDS));
        spnHouseHold.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnHouseHold.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setHouseHold(String.valueOf(position));
            }
        });

        spnHouseType.setAdapter(new ArrayAdapter<>(Activity_ResidenceInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.HOUSE_TYPE));
        spnHouseType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnHouseType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setHouseType(String.valueOf(position));
            }
        });


        btnNext.setOnClickListener(v -> SaveResidenceInfo());

    }

    private void SaveResidenceInfo() {

        mViewModel.getModel().setOneAddress(cbOneAddress.isChecked());
        mViewModel.getModel().setLandMark(Objects.requireNonNull(txtLandMark.getText()).toString());
        mViewModel.getModel().setHouseNox(Objects.requireNonNull(txtHouseNox.getText()).toString());
        mViewModel.getModel().setAddress1(Objects.requireNonNull(txtAddress1.getText()).toString());
        mViewModel.getModel().setAddress2(Objects.requireNonNull(txtAddress2.getText()).toString());
        mViewModel.getModel().setOwnerRelation(Objects.requireNonNull(txtRelationship.getText()).toString());
//        mViewModel.getModel().setLenghtOfStay(Double.parseDouble(txtLgnthStay.getText().toString()));
//        mViewModel.getModel().setMonthlyExpenses(Double.parseDouble(txtMonthlyExp.getText().toString()));
        mViewModel.getModel().setIsYear(Integer.parseInt(spnLgnthStayPosition));
        mViewModel.getModel().setPermanentLandMark(Objects.requireNonNull(txtPLandMark.getText()).toString());
        mViewModel.getModel().setPermanentHouseNo(Objects.requireNonNull(txtPHouseNox.getText()).toString());
        mViewModel.getModel().setPermanentAddress1(Objects.requireNonNull(txtPAddress1.getText()).toString());
        mViewModel.getModel().setPermanentAddress2(Objects.requireNonNull(txtPAddress2.getText()).toString());
//        mViewModel.getModel().setPermanentProvinceNm(txtPProvince.getText().toString());
//        mViewModel.getModel().setPermanentMunicipalNm(txtPMunicipl.getText().toString());
//        mViewModel.getModel().setPermanentBarangayName(txtPBarangay.getText().toString());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_ResidenceInfo.this, Activity_EmploymentInfo.class);
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
//                mViewModel.getModel().setHouseHold(String.valueOf(i));
//            }
//            if (spnHouseType.equals(poView)) {
//                spnHouseTypePosition = String.valueOf(i);
//                mViewModel.getModel().setHouseType(spnHouseTypePosition);
//            }
//            if (spnLgnthStay.equals(poView)) {
//                spnLgnthStayPosition = String.valueOf(i);
//            }
//
//        }
//    }


    private class OnHouseOwnershipSelectListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (radioGroup.getId() == R.id.rg_ownership) {
                if (i == R.id.rb_owned) {
                    lnOtherInfo.setVisibility(View.GONE);
                    mViewModel.getModel().setHouseOwn("0");
                }
                if (i == R.id.rb_rent) {
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.GONE);
                    mViewModel.getModel().setHouseOwn("1");
                    mViewModel.getModel().setLenghtOfStay(Double.parseDouble(Objects.requireNonNull(txtLgnthStay.getText()).toString().trim()));
                }
                if (i == R.id.rb_careTaker) {
                    lnOtherInfo.setVisibility(View.VISIBLE);
                    tilRelationship.setVisibility(View.VISIBLE);
                    mViewModel.getModel().setHouseOwn("2");
                    mViewModel.getModel().setMonthlyExpenses(Double.parseDouble(Objects.requireNonNull(txtMonthlyExp.getText()).toString()));

                }
            } else {
                if (i == R.id.rb_yes) {
                    mViewModel.getModel().setHasGarage("1");
                }
                if (i == R.id.rb_no) {
                    mViewModel.getModel().setHasGarage("0");
                }
            }
        }
    }

    private class OnAddressSetListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b) {
                lnPermaAddx.setVisibility(View.GONE);
            } else {
                lnPermaAddx.setVisibility(View.VISIBLE);
            }
        }
    }
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

    private void initWidgets() {

        toolbar = findViewById(R.id.toolbar_ResidenceInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Residence Info");

        cbOneAddress = findViewById(R.id.cb_oneAddress);

        txtLandMark = findViewById(R.id.txt_landmark);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress1 = findViewById(R.id.txt_address);
        txtAddress2 = findViewById(R.id.txt_address2);
        txtBarangay = findViewById(R.id.txt_barangay);
        txtMunicipality = findViewById(R.id.txt_town);
//        txtProvince = findViewById(R.id.txt_province);
        txtRelationship = findViewById(R.id.txt_relationship);
        txtLgnthStay = findViewById(R.id.txt_lenghtStay);
        txtMonthlyExp = findViewById(R.id.txt_monthlyExp);
        txtPLandMark = findViewById(R.id.txt_perm_landmark);
        txtPHouseNox = findViewById(R.id.txt_perm_houseNox);
        txtPAddress1 = findViewById(R.id.txt_perm_address);
        txtPAddress2 = findViewById(R.id.txt_perm_address2);
        txtPBarangay = findViewById(R.id.txt_perm_barangay);
        txtPMunicipl = findViewById(R.id.txt_perm_town);
//        txtPProvince = findViewById(R.id.txt_perm_province);

        spnLgnthStay = findViewById(R.id.spn_lenghtStay);
        spnHouseHold = findViewById(R.id.spn_houseHold);
        spnHouseType = findViewById(R.id.spn_houseType);

        rgOwnsership = findViewById(R.id.rg_ownership);
        rgGarage = findViewById(R.id.rg_garage);

        tilRelationship = findViewById(R.id.til_relationship);

        lnOtherInfo = findViewById(R.id.linear_otherInfo);
        lnPermaAddx = findViewById(R.id.linear_permanentAdd);

        cbOneAddress.setOnCheckedChangeListener(new OnAddressSetListener());
        rgOwnsership.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());
        rgGarage.setOnCheckedChangeListener(new OnHouseOwnershipSelectListener());

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

    }
}