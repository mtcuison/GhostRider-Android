package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Personal;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseResidence;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseResidence;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_SpouseResidenceInfo extends AppCompatActivity {

    private VMSpouseResidence mViewModel;
    private MessageBox poMessage;

    private CheckBox cbLivingWithSpouse;
    private TextInputEditText txtLandMark, txtHouseNox, txtAddress1, txtAddress2;
    private AutoCompleteTextView txtBarangay, txtTown, txtProvince;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;
    private String transnox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(Activity_SpouseResidenceInfo.this).get(VMSpouseResidence.class);
        poMessage = new MessageBox(Activity_SpouseResidenceInfo.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spouse_residence_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_SpouseResidenceInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try{
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            SpouseResidence loDetail = (SpouseResidence) args;

                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetTownProvinceList().observe(Activity_SpouseResidenceInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try {
                    ArrayList<String> strings = new ArrayList<>();
                    for(int x = 0 ; x < loList.size(); x++){
                        String lsTown = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                        strings.add(lsTown);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_SpouseResidenceInfo.this,
                            android.R.layout.simple_spinner_dropdown_item,strings.toArray(new String[0]));
                    txtTown.setAdapter(adapter);
                    txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0 ; x < loList.size() ; x++){
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtTown.getText().toString().trim();
                                if( lsSlctd.equalsIgnoreCase(lsLabel)){
                                    mViewModel.getModel().setMunicipalID(loList.get(x).sTownIDxx);
                                    mViewModel.getModel().setMunicipalNm(lsLabel);
                                }
                            }

                            mViewModel.GetBarangayList(mViewModel.getModel().getMunicipalID()).observe(Activity_SpouseResidenceInfo.this, new Observer<List<EBarangayInfo>>() {
                                @Override
                                public void onChanged(List<EBarangayInfo> BrgyList) {
                                    ArrayList<String> string = new ArrayList<>();
                                    for(int x = 0 ; x < BrgyList.size(); x++) {
                                        String lsBrgy = BrgyList.get(x).getBrgyName();
                                        string.add(lsBrgy);
                                    }
                                    ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_SpouseResidenceInfo.this,
                                            android.R.layout.simple_spinner_dropdown_item,string.toArray(new String[0]));
                                    txtBarangay.setAdapter(adapters);
                                    txtBarangay.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                                    txtBarangay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            for (int x = 0 ; x < BrgyList.size() ; x++){
                                                String lsLabel = BrgyList.get(x).getBrgyName();
                                                String lsSlctd = txtBarangay.getText().toString().trim();
                                                if( lsSlctd.equalsIgnoreCase(lsLabel)){
                                                    mViewModel.getModel().setBarangayID(BrgyList.get(x).getBrgyIDxx());
                                                    mViewModel.getModel().setBarangayName(lsLabel);
                                                }
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        btnNext.setOnClickListener(v -> SaveResidenceInfo());
    }

    private void SaveResidenceInfo() {

        mViewModel.getModel().setLandMark(Objects.requireNonNull(txtLandMark.getText()).toString().trim());
        mViewModel.getModel().setHouseNox(Objects.requireNonNull(txtHouseNox.getText()).toString().trim());
        mViewModel.getModel().setAddress1(Objects.requireNonNull(txtAddress1.getText()).toString().trim());
        mViewModel.getModel().setAddress2(Objects.requireNonNull(txtAddress2.getText()).toString().trim());

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_SpouseResidenceInfo.this, Activity_SpouseEmploymentInfo.class);
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
        toolbar = findViewById(R.id.toolbar_SpouseResidenceInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Residence Info");


        txtLandMark = findViewById(R.id.txt_landmark);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress1 = findViewById(R.id.txt_address);
        txtAddress2 = findViewById(R.id.txt_address2);
        txtProvince = findViewById(R.id.txt_province);
        txtTown = findViewById(R.id.txt_town);
        txtBarangay = findViewById(R.id.txt_barangay);

        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

    }

}