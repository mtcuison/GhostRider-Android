/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseInfo;

import java.util.List;
import java.util.Objects;

public class Fragment_SpouseInfo extends Fragment implements ViewModelCallBack {
    private static final String TAG = Fragment_SpouseInfo.class.getSimpleName();
    private SpouseInfoModel infoModel;
    private VMSpouseInfo mViewModel;
    private AutoCompleteTextView txtProvince, txtTownxx, txtCitizenx;
    private TextInputEditText txtLastName,
            txtFirstName,
            txtSuffix,
            txtMiddName,
            txtNickName,
            txtBDate,
            txtPrimeCntc,
            txtPrimeCntcYr,
            txtSecCntct,
            txtSecCntctYr,
            txtThirCntct,
            txtThirCntctYr,
            txtTelNox,
            txtEmailAdd,
            txtFbAcct,
            txtViberAcct,
            txtMobileYr1,
            txtMobileYr2,
            txtMobileYr3;

    private String transnox;

    private TextInputLayout tilMobileYr1 ,tilMobileYr2 ,tilMobileYr3;
    private AutoCompleteTextView spnMobile1, spnMobile2, spnMobile3;

    private String spnMobile1Position = "-1";
    private String spnMobile2Position = "-1";
    private String spnMobile3Position = "-1";
    private Button btnNext, btnPrvs;

    public static Fragment_SpouseInfo newInstance() {
        return new Fragment_SpouseInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spouse_info, container, false);
        infoModel = new SpouseInfoModel();
        transnox = Activity_CreditApplication.getInstance().getTransNox();
        txtProvince = view.findViewById(R.id.txt_bpProvince);
        txtTownxx = view.findViewById(R.id.txt_bpTown);

        txtLastName = view.findViewById(R.id.tie_last_name);
        txtFirstName = view.findViewById(R.id.tie_first_name);
        txtSuffix = view.findViewById(R.id.tie_suffix);
        txtMiddName = view.findViewById(R.id.tie_midd_name);
        txtNickName = view.findViewById(R.id.tie_nick_name);
        txtBDate = view.findViewById(R.id.tie_birth_date);
        txtCitizenx = view.findViewById(R.id.tie_citizenship);

        txtPrimeCntc = view.findViewById(R.id.txt_mobileNo1);
        txtPrimeCntcYr = view.findViewById(R.id.txt_mobileNo1Year);

        txtSecCntct = view.findViewById(R.id.txt_mobileNo2);
        txtSecCntctYr = view.findViewById(R.id.txt_mobileNo2Year);

        txtThirCntct = view.findViewById(R.id.txt_mobileNo3);
        txtThirCntctYr = view.findViewById(R.id.txt_mobileNo3Year);

        txtTelNox = view.findViewById(R.id.tie_tel_no);
        txtEmailAdd = view.findViewById(R.id.tie_emailAdd);
        txtFbAcct = view.findViewById(R.id.tie_fbAcct);
        txtViberAcct = view.findViewById(R.id.tie_viberAcct);

        spnMobile1 = view.findViewById(R.id.spn_mobile1Type);
        spnMobile2 = view.findViewById(R.id.spn_mobile2Type);
        spnMobile3 = view.findViewById(R.id.spn_mobile3Type);

        txtMobileYr1 = view.findViewById(R.id.txt_mobileNo1Year);
        tilMobileYr1 = view.findViewById(R.id.til_mobileNo1Year);
        txtMobileYr2 = view.findViewById(R.id.txt_mobileNo2Year);
        tilMobileYr2 = view.findViewById(R.id.til_mobileNo2Year);
        txtMobileYr3 = view.findViewById(R.id.txt_mobileNo3Year);
        tilMobileYr3 = view.findViewById(R.id.til_mobileNo3Year);

        btnNext = view.findViewById(R.id.btn_creditAppNext);
        btnPrvs = view.findViewById(R.id.btn_creditAppPrvs);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(VMSpouseInfo.class);

        // Get transNox first from the holding activity
        String TransNox = Activity_CreditApplication.getInstance().getTransNox();

        // SetTransNox of ViewModel
        mViewModel.setTransNox(TransNox);


        // Set DetailInfo to goCas
        mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setDetailInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch(NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Set province list in txtProvince
        mViewModel.getProvinceNames().observe(getViewLifecycleOwner(), new Observer<String[]>() {
            @Override
            public void onChanged(String[] strings) {
                try{
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    txtProvince.setAdapter(adapter);
                    txtProvince.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        //Getting ID of the Province
        txtProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getProvinceInfos().observe(getViewLifecycleOwner(), new Observer<List<EProvinceInfo>>() {
                    @Override
                    public void onChanged(List<EProvinceInfo> eProvinceInfos) {
                        for(int x = 0; x < eProvinceInfos.size(); x++){
                            if(txtProvince.getText().toString().equalsIgnoreCase(eProvinceInfos.get(x).getProvName())){
                                mViewModel.setProvinceID(eProvinceInfos.get(x).getProvIDxx());
                                break;
                            }
                        }

                        mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), new Observer<String[]>() {
                            @Override
                            public void onChanged(String[] strings) {
                                try{
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                                    txtTownxx.setAdapter(adapter);
                                    txtTownxx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
                                } catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });

        txtTownxx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.getAllTownInfo().observe(getViewLifecycleOwner(), new Observer<List<ETownInfo>>() {
                    @Override
                    public void onChanged(List<ETownInfo> eTownInfos) {
                        for(int x = 0; x < eTownInfos.size(); x++){
                            if(txtTownxx.getText().toString().equalsIgnoreCase(eTownInfos.get(x).getTownName())){ //from id to town name
                                mViewModel.setTownID(eTownInfos.get(x).getTownIDxx());
                                break;
                            }
                        }
                    }
                });
            }
        });

        mViewModel.getAllCountryCitizenNames().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtCitizenx.setAdapter(adapter);
            txtCitizenx.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        txtCitizenx.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getCountryInfoList().observe(getViewLifecycleOwner(), eCountryInfos -> {
            for(int x = 0; x < eCountryInfos.size(); x++){
                if(txtCitizenx.getText().toString().equalsIgnoreCase(eCountryInfos.get(x).getNational())){
                    mViewModel.setCitizenship(eCountryInfos.get(x).getCntryCde());
                    break;
                }
            }
        }));

        mViewModel.getMobileNoType().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnMobile1.setAdapter(stringArrayAdapter);
            spnMobile2.setAdapter(stringArrayAdapter);
            spnMobile3.setAdapter(stringArrayAdapter);
            spnMobile1.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnMobile2.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnMobile3.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        mViewModel.getMobileNo1().observe(getViewLifecycleOwner(), s -> {
            spnMobile1.setSelection(s.length());
            spnMobile1Position = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getMobileNo2().observe(getViewLifecycleOwner(), s -> {
            spnMobile2.setSelection(s.length());
            spnMobile2Position = s;
            Log.e("Mobile 2", s);
        });
        mViewModel.getMobileNo3().observe(getViewLifecycleOwner(), s -> {
            spnMobile3.setSelection(s.length());
            spnMobile3Position = s;
            Log.e("Mobile 3 ", s);
        });

        mViewModel.getMobileNo1Year().observe(getViewLifecycleOwner(), integer -> tilMobileYr1.setVisibility(integer));
        mViewModel.getMobileNo2Year().observe(getViewLifecycleOwner(), integer -> tilMobileYr2.setVisibility(integer));
        mViewModel.getMobileNo3Year().observe(getViewLifecycleOwner(), integer -> tilMobileYr3.setVisibility(integer));

        spnMobile1.setOnItemClickListener(new OnItemClickListener(spnMobile1));
        spnMobile2.setOnItemClickListener(new OnItemClickListener(spnMobile2));
        spnMobile3.setOnItemClickListener(new OnItemClickListener(spnMobile3));



        txtBDate.addTextChangedListener(new OnDateSetListener(txtBDate));
        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(6));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set the model for validation
                infoModel.setLastName(txtLastName.getText().toString());
                infoModel.setFrstName(txtFirstName.getText().toString());
                infoModel.setMiddName(txtMiddName.getText().toString());
                infoModel.setSuffix(txtSuffix.getText().toString());
                infoModel.setNickName(txtNickName.getText().toString());
                infoModel.setBirthDate(txtBDate.getText().toString());
                infoModel.setProvNme(txtProvince.getText().toString());
                infoModel.setTownNme(txtTownxx.getText().toString());
                infoModel.setCitizenx(txtCitizenx.getText().toString());

                if(!Objects.requireNonNull(txtPrimeCntc.getText()).toString().trim().isEmpty()) {
                    if(Integer.parseInt(spnMobile1Position) == 1) {
                        infoModel.setMobileNo(txtPrimeCntc.getText().toString(),spnMobile1Position, Integer.parseInt(Objects.requireNonNull(txtMobileYr1.getText()).toString()));
                    } else {
                        infoModel.setMobileNo(txtPrimeCntc.getText().toString(), spnMobile1Position, 0);
                    }
                }
                if(!Objects.requireNonNull(txtSecCntct.getText()).toString().trim().isEmpty()) {
                    if(Integer.parseInt(spnMobile2Position) == 1) {
                        infoModel.setMobileNo(txtSecCntct.getText().toString(), spnMobile2Position, Integer.parseInt(Objects.requireNonNull(txtMobileYr2.getText()).toString()));
                    } else {
                        infoModel.setMobileNo(txtSecCntct.getText().toString(), spnMobile2Position, 0);
                    }
                }
                if(!Objects.requireNonNull(txtThirCntct.getText()).toString().trim().isEmpty()) {
                    if(Integer.parseInt(spnMobile3Position) == 1) {
                        infoModel.setMobileNo(txtThirCntct.getText().toString(), spnMobile3Position, Integer.parseInt(Objects.requireNonNull(txtMobileYr3.getText()).toString()));
                    } else {
                        infoModel.setMobileNo(txtThirCntct.getText().toString(), spnMobile3Position, 0);
                    }
                }

                infoModel.setPhoneNo(txtTelNox.getText().toString());
                infoModel.setEmailAdd(txtEmailAdd.getText().toString());
                infoModel.setFBacct(txtFbAcct.getText().toString());
                infoModel.setVbrAcct(txtViberAcct.getText().toString());

                // Trigger save() with SpouseInfoModel instance with data set.
                mViewModel.Save(infoModel, Fragment_SpouseInfo.this);
            }
        });

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(8);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

    // TODO: Value Setters
    private void setFieldValues(ECreditApplicantInfo foCredtAp) {
        if(foCredtAp.getSpousexx() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredtAp.getSpousexx());

                txtLastName.setText( (!"".equalsIgnoreCase(loJson.getString("sLastName"))) ? loJson.getString("sLastName") : "" );
                txtFirstName.setText( (!"".equalsIgnoreCase(loJson.getString("sFrstName"))) ? loJson.getString("sFrstName") : "" );
                txtSuffix.setText( (!"".equalsIgnoreCase(loJson.getString("sSuffixNm"))) ? loJson.getString("sSuffixNm") : "" );
                txtMiddName.setText( (!"".equalsIgnoreCase(loJson.getString("sMiddName"))) ? loJson.getString("sMiddName") : "" );
                txtNickName.setText( (!"".equalsIgnoreCase(loJson.getString("sNickName"))) ? loJson.getString("sNickName") : "" );
                txtBDate.setText( (!"".equalsIgnoreCase(loJson.getString("dBirthDte"))) ? loJson.getString("dBirthDte") : "" );

                if(!"".equalsIgnoreCase(loJson.getString("sBirthPlc"))) {
                    mViewModel.getTownProvinceByTownID(loJson.getString("sBirthPlc")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                        try {
                            txtProvince.setText(townProvinceInfo.sProvName);
                            txtTownxx.setText(townProvinceInfo.sTownName);
                            mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                            mViewModel.setProvinceID(townProvinceInfo.sProvIDxx);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                if(!"".equalsIgnoreCase(loJson.getString("sCitizenx"))) {
                    mViewModel.getClientCitizenship(loJson.getString("sCitizenx")).observe(getViewLifecycleOwner(), citizen -> {
                        try {
                            txtCitizenx.setText(citizen);
                            mViewModel.setCitizenship(loJson.getString("sCitizenx"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }

                //Mobile number
                JSONArray loCPArry = loJson.getJSONArray("mobile_number");
                for(int x = 0; x < loCPArry.length(); x++) {
                    if(x == 0) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            txtPrimeCntc.setText(loJsonCp.getString("sMobileNo"));
                            spnMobile1.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))]);
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                txtPrimeCntcYr.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    } else if(x == 1) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            txtSecCntct.setText(loJsonCp.getString("sMobileNo"));
                            spnMobile2.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))]);
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                txtSecCntctYr.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    } else if(x == 2) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            txtThirCntct.setText(loJsonCp.getString("sMobileNo"));
                            spnMobile3.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))]);
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                txtThirCntctYr.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    }
                }

                JSONArray loTelArr = loJson.getJSONArray("landline");
                JSONObject loJsonTel = loTelArr.getJSONObject(0);
                txtTelNox.setText( (!"".equalsIgnoreCase(loJsonTel.getString("sPhoneNox"))) ? loJsonTel.getString("sPhoneNox") : "" );

                JSONArray loEmail = loJson.getJSONArray("email_address");
                JSONObject loJsonEml =  loEmail.getJSONObject(0);
                txtEmailAdd.setText( (!"".equalsIgnoreCase(loJsonEml.getString("sEmailAdd"))) ? loJsonEml.getString("sEmailAdd") : "" );

//
                JSONObject loJsonFb = loJson.getJSONObject("facebook");
                txtFbAcct.setText( (!"".equalsIgnoreCase(loJsonFb.getString("sFBAcctxx"))) ? loJsonFb.getString("sFBAcctxx") : "" );

                txtViberAcct.setText( (!"".equalsIgnoreCase(loJson.getString("sVibeAcct"))) ? loJson.getString("sVibeAcct") : "" );


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // clear input fields
            txtLastName.getText().clear();
            txtFirstName.getText().clear();
            txtSuffix.getText().clear();
            txtMiddName.getText().clear();
            txtNickName.getText().clear();
            txtBDate.getText().clear();
            txtProvince.getText().clear();
            txtTownxx.getText().clear();
            txtCitizenx.getText().clear();
            txtPrimeCntc.getText().clear();
            txtSecCntct.getText().clear();
            txtThirCntct.getText().clear();

            spnMobile1.getText().clear();
            spnMobile2.getText().clear();
            spnMobile3.getText().clear();

            txtPrimeCntcYr.getText().clear();
            txtSecCntctYr.getText().clear();
            txtThirCntctYr.getText().clear();
            txtTelNox.getText().clear();
            txtEmailAdd.getText().clear();
            txtFbAcct.getText().clear();
            txtViberAcct.getText().clear();
        }
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;
        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnMobile1.equals(poView)) {
                mViewModel.setLsMobile1(String.valueOf(i));
            }
            if (spnMobile2.equals(poView)) {
                mViewModel.setLsMobile2(String.valueOf(i));
            }
            if (spnMobile3.equals(poView)) {
                mViewModel.setLsMobile3(String.valueOf(i));

            }

        }
    }
}