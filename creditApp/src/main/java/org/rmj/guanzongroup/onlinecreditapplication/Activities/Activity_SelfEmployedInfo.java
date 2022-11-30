package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Business;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseBusiness;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMBusinessInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Activity_SelfEmployedInfo extends AppCompatActivity {


    private VMBusinessInfo mViewModel;
    private MessageBox poMessage;
    private AutoCompleteTextView spnBussNtr, spnBussTyp, spnBussSze, spnLngSrvc;

    private TextInputEditText txtBussName, txtBussAdds, txtLnghtSrv, txtMnthlyIn, txtMnthlyEx;
    private AutoCompleteTextView txtProvnc, txtTownxx;
    private CheckBox cb_isYear;

    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_SelfEmployedInfo.this).get(VMBusinessInfo.class);
        poMessage = new MessageBox(Activity_SelfEmployedInfo.this);
        setContentView(R.layout.activity_self_employed_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_SelfEmployedInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                mViewModel.getModel().setTransNox(app.getTransNox());
                mViewModel.ParseData(app, new OnParseListener() {
                    @Override
                    public void OnParse(Object args) {
                        Business loDetail = (Business) args;
                    }
                });
            }
        });

//        mViewModel.GetTownProvinceList().observe(Activity_SelfEmployedInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onChanged(List<DTownInfo.TownProvinceInfo> provList) {
//                try {
//                    ArrayList<String> strings = new ArrayList<>();
//                    for (int x = 0; x < provList.size(); x++) {
//                        String lsProv = "" + provList.get(x).sProvName;
////                        String lsTown =  loList.get(x).sProvName ;
//                        strings.add(lsProv);
//
//                        Set<Object> set = new HashSet<>();
//                        strings.removeIf((String i) -> {
//                            return !set.add(i);
//                        });
//
//                    }
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_SelfEmployedInfo.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
//                    txtProvnc.setAdapter(adapter);
//                    txtProvnc.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                    txtProvnc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            for (int x = 0; x < provList.size(); x++) {
//                                String lsLabel = provList.get(x).sProvName;
//                                String lsSlctd = txtProvnc.getText().toString().trim();
//                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
//                                    mViewModel.getModel().setProvince(provList.get(x).sProvIDxx);
//                                    mViewModel.getModel().setProvince(lsLabel);
//                                    break;
//                                }
//                            }
//
//                            mViewModel.GetTownProvinceList().observe(Activity_SelfEmployedInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
//                                @Override
//                                public void onChanged(List<DTownInfo.TownProvinceInfo> townList) {
//                                    try {
//                                        ArrayList<String> string = new ArrayList<>();
//                                        for (int x = 0; x < townList.size(); x++) {
//                                            String lsTown = townList.get(x).sTownName + "";
////                        String lsTown =  loList.get(x).sProvName ;
//                                            string.add(lsTown);
//                                            Set<Object> set = new HashSet<>();
//                                            string.removeIf((String i) -> {
//                                                return !set.add(i);
//                                            });
//                                        }
//
//                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_SelfEmployedInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
//                                        txtTownxx.setAdapter(adapters);
//                                        txtTownxx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                                        txtTownxx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                for (int x = 0; x < townList.size(); x++) {
//                                                    String lsLabel = townList.get(x).sTownName;
//                                                    String lsSlctd = txtTownxx.getText().toString().trim();
//                                                    if (lsSlctd.equalsIgnoreCase(lsLabel)) {
//                                                        mViewModel.getModel().setTown(townList.get(x).sTownIDxx);
//                                                        mViewModel.getModel().setTown(lsLabel);
//                                                        break;
//                                                    }
//                                                }
//                                            }
//                                        });
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        }
//                    });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        mViewModel.GetTownProvinceList().observe(Activity_SelfEmployedInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try {
                    ArrayList<String> string = new ArrayList<>();
                    for (int x = 0; x < loList.size(); x++) {
                        String lsTown = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
//                        String lsTown =  loList.get(x).sProvName ;
                        string.add(lsTown);

                    }

                    ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_SelfEmployedInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
                    txtTownxx.setAdapter(adapters);
                    txtTownxx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    txtTownxx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtTownxx.getText().toString().trim();
                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
                                    mViewModel.getModel().setTown(loList.get(x).sTownIDxx);
                                    mViewModel.getModel().setTown(lsLabel);
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

        spnBussNtr.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBussNtr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBussNtr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setNatureOfBusiness(String.valueOf(position));
            }
        });

        spnBussTyp.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_TYPE));
        spnBussTyp.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBussTyp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setTypeOfBusiness(String.valueOf(position));
            }
        });

        spnBussSze.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_SIZE));
        spnBussSze.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBussSze.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setSizeOfBusiness(String.valueOf(position));
            }
        });
        spnLngSrvc.setAdapter(new ArrayAdapter<>(Activity_SelfEmployedInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnLngSrvc.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnLngSrvc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setIsYear(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSelfEmploymentInfo();
            }
        });

        btnPrvs.setOnClickListener(v -> finish());
    }

    private void SaveSelfEmploymentInfo() {
        mViewModel.getModel().setNameOfBusiness(txtBussName.getText().toString().trim());
        mViewModel.getModel().setBusinessAddress(txtBussAdds.getText().toString().trim());
//        mViewModel.getModel().setProvince(txtProvnc.getText().toString().trim());
//        mViewModel.getModel().setTown(txtTownxx.getText().toString().trim());

        if (txtLnghtSrv.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setLengthOfService(0);
        }else {
            mViewModel.getModel().setLengthOfService(Double.parseDouble(txtLnghtSrv.getText().toString().trim()));
        }

        if (txtMnthlyIn.getText().toString().trim().isEmpty()){
            mViewModel.getModel().setMonthlyIncome(0);
        }else {
            mViewModel.getModel().setMonthlyIncome(Long.parseLong(txtMnthlyIn.getText().toString()));
        }

        if (txtMnthlyEx.getText().toString().isEmpty()){
            mViewModel.getModel().setMonthlyExpense(0);
        }else{
            mViewModel.getModel().setMonthlyExpense(Long.parseLong(txtMnthlyEx.getText().toString()));
        }

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_SelfEmployedInfo.this, Activity_Finance.class);
                loIntent.putExtra("sTransNox", args);
                startActivity(loIntent);
                overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
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
        toolbar = findViewById(R.id.toolbar_SelfEmployedInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Self Employed Info");

        spnBussNtr = findViewById(R.id.spn_businessNature);
        spnBussTyp = findViewById(R.id.spn_businessType);
        spnBussSze = findViewById(R.id.spn_businessSize);
        spnLngSrvc = findViewById(R.id.spn_lenghtSrvc);

        txtBussName = findViewById(R.id.txt_businessName);
        txtBussAdds = findViewById(R.id.txt_businessAddress);
        txtLnghtSrv = findViewById(R.id.txt_lenghtService);
        txtMnthlyIn = findViewById(R.id.txt_monthlyInc);
        txtMnthlyEx = findViewById(R.id.txt_monthlyExp);
//        txtProvnc = findViewById(R.id.txt_province);
        txtTownxx = findViewById(R.id.txt_town);

        btnPrvs = findViewById(R.id.btn_creditAppPrvs);
        btnNext = findViewById(R.id.btn_creditAppNext);

//        txtMnthlyIn.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyIn));
//        txtMnthlyEx.addTextChangedListener(new FormatUIText.CurrencyFormat(txtMnthlyEx));

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        getViewModelStore().clear();
        super.onDestroy();
    }
}