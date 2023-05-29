package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GCircle.Apps.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMPersonalInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Activity_ClientInfo extends AppCompatActivity {

    private VMPersonalInfo mViewModel;
    private MessageBox poMessage;

    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx,  txtBirthDt,
            txtEmailAdd, txtMobileNo,  txtHouseNox, txtAddress;

    private MaterialAutoCompleteTextView txtMunicipl,txtBPlace;
    private RadioGroup rgGender;
    private MaterialAutoCompleteTextView spnCivilStatus;
    private MaterialButton btnContinue, btnPrev;
    private MaterialCheckBox txtMobileType1, txtMobileType2, txtMobileType3;

    private MaterialToolbar toolbar;
    private String sTansNox = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poMessage = new MessageBox(Activity_ClientInfo.this);
        setContentView(R.layout.activity_client_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetTownProvinceList().observe(Activity_ClientInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try {
                    ArrayList<String> string = new ArrayList<>();
                    for (int x = 0; x < loList.size(); x++) {
                        String lsTown = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
//                        String lsTown =  loList.get(x).sProvName ;
                        string.add(lsTown);

                    }
                    ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_ClientInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
                    txtBPlace.setAdapter(adapters);
                    txtBPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtBPlace.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setBirthPlc(loList.get(x).sTownIDxx);
                                    mViewModel.getModel().setsBirthIDx(lsLabel);
                                    break;
                                }

                            }
                        }
                    });
                    ArrayAdapter<String> adapters1 = new ArrayAdapter<>(Activity_ClientInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
                    txtMunicipl.setAdapter(adapters1);
                    txtMunicipl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtMunicipl.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setTownIDxx(loList.get(x).sTownIDxx);
                                    mViewModel.getModel().setsMncplNme(lsLabel);
                                    break;
                                }

                            }



                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        txtBirthDt.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_ClientInfo.this,
                    android.R.style.Theme_Holo_Dialog, (view131, year, monthOfYear, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String lsDate = dateFormatter.format(newDate.getTime());
                    txtBirthDt.setText(lsDate);
                    Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                    lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
//                    Log.d(TAG, "Save formatted time: " + lsDate);
                    mViewModel.getModel().setBirthDte(lsDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(new Date().getTime());
            StartTime.show();
        });

        rgGender.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.rb_male) {
                mViewModel.getModel().setGenderCd("0");
            }
            if (i == R.id.rb_female) {
                mViewModel.getModel().setGenderCd("1");
            }
        });

        btnContinue.setOnClickListener(view ->{
            mViewModel.getModel().setFrstName(txtFrstNm.getText().toString());
            mViewModel.getModel().setMiddName(txtMiddNm.getText().toString());
            mViewModel.getModel().setLastName(txtLastNm.getText().toString());
            mViewModel.getModel().setSuffixNm(txtSuffixx.getText().toString());
            mViewModel.getModel().setHouseNox(txtHouseNox.getText().toString());
            mViewModel.getModel().setAddressx(txtAddress.getText().toString());
            mViewModel.getModel().setEmailAdd(txtEmailAdd.getText().toString());
            mViewModel.getModel().setMobileNo(txtMobileNo.getText().toString());
            mViewModel.SaveData(new OnSaveInfoListener() {
                @Override
                public void OnSave(String args) {
                    finish();
                    overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                }

                @Override
                public void OnFailed(String message) {
                    poMessage.initDialog();
                    poMessage.setTitle("Product Inquiry");
                    poMessage.setMessage(message);
                    poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                    poMessage.show();
                }
            });
        });
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        mViewModel = new ViewModelProvider(Activity_ClientInfo.this).get(VMPersonalInfo.class);
        poMessage = new MessageBox(Activity_ClientInfo.this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Guanzon Group OACS");

        txtLastNm = findViewById(R.id.txt_lastname);
        txtFrstNm = findViewById(R.id.txt_firstname);
        txtMiddNm = findViewById(R.id.txt_middname);
        txtSuffixx = findViewById(R.id.txt_suffix);
        txtBirthDt = findViewById(R.id.txt_birthdate);
        txtBPlace = findViewById(R.id.txt_bpTown);
        rgGender = findViewById(R.id.rg_gender);
        txtMobileNo = findViewById(R.id.txt_mobile);
        txtEmailAdd = findViewById(R.id.txt_emailAdd);
        txtHouseNox = findViewById(R.id.txt_houseNox);
        txtAddress = findViewById(R.id.txt_address);
        txtMunicipl = findViewById(R.id.txt_town);

        btnContinue = findViewById(R.id.btnContinue);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}