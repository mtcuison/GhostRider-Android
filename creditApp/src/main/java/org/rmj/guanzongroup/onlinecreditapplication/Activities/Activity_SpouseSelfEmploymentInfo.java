package org.rmj.guanzongroup.onlinecreditapplication.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.integsys.CreditApp.OnSaveInfoListener;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.Business;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.SpouseBusiness;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.OnParseListener;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseBusiness;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_SpouseSelfEmploymentInfo extends AppCompatActivity {

    private VMSpouseBusiness mViewModel;
    private MessageBox poMessage;
    private TextInputEditText txtBizName, txtBizAddrss, txtBizLength, txtMonthlyInc, txtMonthlyExp;
    private AppCompatAutoCompleteTextView spnBizIndustry, spnMonthOrYr, txtProvince, txtTown,
            spnBizType, spnBizSize;
    private Button btnNext, btnPrvs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_SpouseSelfEmploymentInfo.this).get(VMSpouseBusiness.class);
        poMessage = new MessageBox(Activity_SpouseSelfEmploymentInfo.this);
        setContentView(R.layout.activity_spouse_self_employment_info);
        initWidgets();
        mViewModel.InitializeApplication(getIntent());
        mViewModel.GetApplication().observe(Activity_SpouseSelfEmploymentInfo.this, new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo app) {
                try {
                    mViewModel.getModel().setTransNox(app.getTransNox());
                    mViewModel.ParseData(app, new OnParseListener() {
                        @Override
                        public void OnParse(Object args) {
                            SpouseBusiness loDetail = (SpouseBusiness) args;
                            try {
                                setUpFieldsFromLocalDB(loDetail);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetTownProvinceList().observe(Activity_SpouseSelfEmploymentInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> loList) {
                try {
                    ArrayList<String> string = new ArrayList<>();
                    for (int x = 0; x < loList.size(); x++) {
                        String lsTown = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
//                        String lsTown =  loList.get(x).sProvName ;
                        string.add(lsTown);


                    }

                    ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
                    txtTown.setAdapter(adapters);
                    txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                    txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            for (int x = 0; x < loList.size(); x++) {
                                String lsLabel = loList.get(x).sTownName + ", " + loList.get(x).sProvName;
                                String lsSlctd = txtTown.getText().toString().trim();
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

//        mViewModel.GetTownProvinceList().observe(Activity_SpouseSelfEmploymentInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
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
//                        strings.removeIf((String i) -> !set.add(i));
//
//                    }
//
//                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this, android.R.layout.simple_spinner_dropdown_item, strings.toArray(new String[0]));
//                    txtProvince.setAdapter(adapter);
//                    txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                    txtProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            for (int x = 0; x < provList.size(); x++) {
//                                String lsLabel = provList.get(x).sProvName;
//                                String lsSlctd = txtProvince.getText().toString().trim();
//                                if (lsSlctd.equalsIgnoreCase(lsLabel)) {
//                                    mViewModel.getModel().setProvince(provList.get(x).sProvIDxx);
//                                    mViewModel.getModel().setProvince(lsLabel);
//                                    break;
//                                }
//                            }
//
//
//                            mViewModel.GetTownProvinceList().observe(Activity_SpouseSelfEmploymentInfo.this, new Observer<List<DTownInfo.TownProvinceInfo>>() {
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
//
//                                        }
//
//                                        ArrayAdapter<String> adapters = new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this, android.R.layout.simple_spinner_dropdown_item, string.toArray(new String[0]));
//                                        txtTown.setAdapter(adapters);
//                                        txtTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
//                                        txtTown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                            @Override
//                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                                for (int x = 0; x < townList.size(); x++) {
//                                                    String lsLabel = townList.get(x).sTownName;
//                                                    String lsSlctd = txtTown.getText().toString().trim();
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


        spnBizIndustry.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_NATURE));
        spnBizIndustry.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBizIndustry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setNatureOfBusiness(String.valueOf(position));
            }
        });

        spnBizType.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_TYPE));
        spnBizType.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBizType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setTypeOfBusiness(String.valueOf(position));
            }
        });

        spnBizSize.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.BUSINESS_SIZE));
        spnBizSize.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnBizSize.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setSizeOfBusiness(String.valueOf(position));
            }
        });

        spnMonthOrYr.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnMonthOrYr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        spnMonthOrYr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getModel().setIsYear(String.valueOf(position));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSpouseSelfEmploymentInfo();
            }
        });

        btnPrvs.setOnClickListener(v -> finish());
    }

    private void SaveSpouseSelfEmploymentInfo() {
        mViewModel.getModel().setNameOfBusiness(txtBizName.getText().toString().trim());
        mViewModel.getModel().setBusinessAddress(txtBizAddrss.getText().toString().trim());
//        mViewModel.getModel().setProvince(txtProvince.getText().toString().trim());
//        mViewModel.getModel().setTown(txtTown.getText().toString().trim());

        if (txtBizLength.getText().toString().trim().isEmpty()) {
            mViewModel.getModel().setLengthOfService(0);
        } else {
            mViewModel.getModel().setLengthOfService(Double.parseDouble(txtBizLength.getText().toString().trim()));
        }

        if (txtMonthlyInc.getText().toString().trim().isEmpty()) {
            mViewModel.getModel().setMonthlyIncome(0);
        } else {
            mViewModel.getModel().setMonthlyIncome(Long.parseLong(txtMonthlyInc.getText().toString().trim()));
        }

        if (txtMonthlyExp.getText().toString().trim().isEmpty()) {
            mViewModel.getModel().setMonthlyExpense(0);
        } else {
            mViewModel.getModel().setMonthlyExpense(Long.parseLong(txtMonthlyExp.getText().toString().trim()));
        }

        mViewModel.SaveData(new OnSaveInfoListener() {
            @Override
            public void OnSave(String args) {
                Intent loIntent = new Intent(Activity_SpouseSelfEmploymentInfo.this, Activity_SpousePensionInfo.class);
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
        toolbar = findViewById(R.id.toolbar_SpouseSelfEmploymentInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Spouse Self Employment Info");

        spnBizIndustry = findViewById(R.id.spn_bizIndustry);
        spnMonthOrYr = findViewById(R.id.spn_monthOrYr);
        spnBizType = findViewById(R.id.spn_bizType);
        spnBizSize = findViewById(R.id.spn_bizSize);

        txtBizName = findViewById(R.id.txt_bizName);
        txtBizAddrss = findViewById(R.id.txt_bizAddress);
        txtBizLength = findViewById(R.id.txt_bizLength);
        txtMonthlyInc = findViewById(R.id.txt_monthlyInc);
        txtMonthlyExp = findViewById(R.id.txt_monthlyExp);

//        txtProvince = findViewById(R.id.txt_province);
        txtTown = findViewById(R.id.txt_town);
        btnNext = findViewById(R.id.btn_creditAppNext);
        btnPrvs = findViewById(R.id.btn_creditAppPrvs);

//        txtMonthlyInc.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtMonthlyInc));
//        txtMonthlyExp.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtMonthlyExp));


        spnMonthOrYr.setAdapter(new ArrayAdapter<>(Activity_SpouseSelfEmploymentInfo.this,
                android.R.layout.simple_list_item_1, CreditAppConstants.LENGTH_OF_STAY));
        spnMonthOrYr.setDropDownBackgroundResource(R.drawable.bg_gradient_light);

    }


    @SuppressLint("NewApi")
    public void setUpFieldsFromLocalDB(SpouseBusiness infoModel) throws JSONException {
        if (infoModel != null){

            if(!"".equalsIgnoreCase(infoModel.getNatureOfBusiness())) {
                spnBizIndustry.setText(CreditAppConstants.BUSINESS_NATURE[Integer.parseInt(infoModel.getNatureOfBusiness())], false);
                spnBizIndustry.setSelection(Integer.parseInt(infoModel.getNatureOfBusiness()));
                mViewModel.getModel().setNatureOfBusiness(infoModel.getNatureOfBusiness());
            }

            if(!"".equalsIgnoreCase(infoModel.getTypeOfBusiness())) {
                spnBizType.setText(CreditAppConstants.BUSINESS_TYPE[Integer.parseInt(infoModel.getTypeOfBusiness())], false);
                spnBizType.setSelection(Integer.parseInt(infoModel.getTypeOfBusiness()));
                mViewModel.getModel().setTypeOfBusiness(infoModel.getTypeOfBusiness());
            }


            if(!"".equalsIgnoreCase(infoModel.getSizeOfBusiness())) {
                spnBizSize.setText(CreditAppConstants.BUSINESS_SIZE[Integer.parseInt(infoModel.getSizeOfBusiness())], false);
                spnBizType.setSelection(Integer.parseInt(infoModel.getSizeOfBusiness()));
                mViewModel.getModel().setSizeOfBusiness(infoModel.getSizeOfBusiness());
            }
            int nlength = (int)(infoModel.getLenghtOfService() * 12);
            if (nlength < 12){
                txtBizLength.setText(String.valueOf(nlength));
                spnMonthOrYr.setText(CreditAppConstants.LENGTH_OF_STAY[0], false);
                mViewModel.getModel().setIsYear(String.valueOf(0));
                mViewModel.getModel().setLengthOfService(nlength);
            }else{
                txtBizLength.setText(String.valueOf(infoModel.getLenghtOfService()));
                spnMonthOrYr.setText(CreditAppConstants.LENGTH_OF_STAY[1], false);
                mViewModel.getModel().setIsYear(String.valueOf(1));
                mViewModel.getModel().setLengthOfService(infoModel.getLenghtOfService());
            }

            if(!"".equalsIgnoreCase(infoModel.getBusinessAddress())) {
                txtBizAddrss.setText(infoModel.getBusinessAddress());
                mViewModel.getModel().setBusinessAddress(infoModel.getBusinessAddress());
            }

            if(!"".equalsIgnoreCase(infoModel.getTown())) {
                txtTown.setText(infoModel.getBusinessAddress());
                mViewModel.getModel().setBusinessAddress(infoModel.getBusinessAddress());
                mViewModel.getModel().setTown(infoModel.getTown());
            }
            txtBizName.setText(!"".equalsIgnoreCase(String.valueOf(infoModel.getNameOfBusiness())) ? String.valueOf(infoModel.getNameOfBusiness()) : "");
            txtMonthlyInc.setText( !"".equalsIgnoreCase(String.valueOf(infoModel.getMonthlyIncome())) ? String.valueOf(infoModel.getMonthlyIncome()) : "");
            txtMonthlyExp.setText( !"".equalsIgnoreCase(String.valueOf(infoModel.getMonthlyExpense())) ? String.valueOf(infoModel.getMonthlyExpense()) : "");

//            infoModel.setIsYear(String.valueOf(i));
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
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