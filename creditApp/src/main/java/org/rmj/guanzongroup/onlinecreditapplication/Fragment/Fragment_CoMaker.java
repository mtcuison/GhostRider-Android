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

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Data.UploadCreditApp;
import org.rmj.g3appdriver.etc.OnDateSetListener;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;

import java.util.Objects;

public class Fragment_CoMaker extends Fragment implements ViewModelCallBack {
    private VMCoMaker mViewModel;
    private static final String TAG = Fragment_CoMaker.class.getSimpleName();
    private String TransNox;
    private String spnIncomePosition = "-1";
    private String spnCoRelationPosition = "-1";
    private String spnPrmryCntctPosition = "-1";
    private String spnScndCntctPosition = "-1";
    private String spnTrtCntctPosition = "-1";

    private TextInputEditText tieLastname;
    private TextInputEditText tieFrstname;
    private TextInputEditText tieMiddname;
    private AutoCompleteTextView tieSuffixxx;
    private TextInputEditText tieNickname;
    private TextInputEditText tieBrthDate;
    private AutoCompleteTextView tieBrthProv;
    private AutoCompleteTextView tieBrthTown;
    private TextInputEditText tiePrmCntct;
    private TextInputEditText tieScnCntct;
    private TextInputEditText tieTrtCntct;
    private TextInputLayout tilPrmCntctPlan;
    private TextInputLayout tilScnCntctPlan;
    private TextInputLayout tilTrtCntctPlan;
    private TextInputEditText tiePrmCntctPlan;
    private TextInputEditText tieScnCntctPlan;
    private TextInputEditText tieTrtCntctPlan;
    private TextInputEditText tieFbAcctxx;
    //    private Spinner spnBrwrRltn;
    private AutoCompleteTextView spnPrmCntct;
    private AutoCompleteTextView spnScnCntct;
    private AutoCompleteTextView spnTrtCntct;
    private AutoCompleteTextView spnIncmSrce;
    private AutoCompleteTextView spnBrwrRltn;
    private Button btnPrvs;
    private Button btnNext;
    private CoMakerModel infoModel;
    private LoadDialog poDialogx;
    private MessageBox poMessage;
    private ImageFileCreator poImageFile;

    public static Fragment_CoMaker newInstance() {
        return new Fragment_CoMaker();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comaker, container, false);
        infoModel = new CoMakerModel();
        poDialogx = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());
        setupWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMCoMaker.class);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        mViewModel.setTransNox(TransNox);
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try {
                mViewModel.setCreditApplicantInfo(eCreditApplicantInfo);
                setFieldValues(eCreditApplicantInfo);
            } catch(NullPointerException e) {
                e.printStackTrace();
            }
        });
        mViewModel.getSpnCMakerRelation().observe(getViewLifecycleOwner(), stringArrayAdapter ->{
            spnBrwrRltn.setAdapter(stringArrayAdapter);
            spnBrwrRltn.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getSpnCMakerIncomeSource().observe(getViewLifecycleOwner(), stringArrayAdapter -> {
            spnIncmSrce.setAdapter(stringArrayAdapter);
            spnIncmSrce.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getCmrPrimaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilPrmCntctPlan.setVisibility(integer));
        mViewModel.getCmrSecondaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilScnCntctPlan.setVisibility(integer));
        mViewModel.getCmrTertiaryCntctPlan().observe(getViewLifecycleOwner(), integer -> tilTrtCntctPlan.setVisibility(integer));
        mViewModel.getTertiaryContact().observe(getViewLifecycleOwner(), s -> {
            spnTrtCntct.setSelection(s.length());
            spnTrtCntctPosition = s;
            Log.e("Mobile 1", s);
        });

        mViewModel.getSecondaryContact().observe(getViewLifecycleOwner(), s -> {
            spnScnCntct.setSelection(s.length());
            spnScndCntctPosition = s;
            Log.e("Mobile 2", s);
        });
        mViewModel.getPrimaryContact().observe(getViewLifecycleOwner(), s -> {
            spnPrmCntct.setSelection(s.length());
            spnPrmryCntctPosition = s;
            Log.e("Mobile 3 ", s);
        });
        mViewModel.getCMakeIncomeSource().observe(getViewLifecycleOwner(), s -> {
            spnIncmSrce.setSelection(s.length());
            spnIncomePosition = s;
            Log.e("Mobile 1", s);
        });
        mViewModel.getCMakerRelation().observe(getViewLifecycleOwner(), s -> {
            spnBrwrRltn.setSelection(s.length());
            spnCoRelationPosition = s;
            Log.e("Mobile 1", s);
        });
        mViewModel.getMobileNoType().observe(getViewLifecycleOwner(),(stringArrayAdapter) -> {
            spnPrmCntct.setAdapter(stringArrayAdapter);
            spnScnCntct.setAdapter(stringArrayAdapter);
            spnTrtCntct.setAdapter(stringArrayAdapter);
            spnPrmCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnScnCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            spnTrtCntct.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });
        mViewModel.getProvinceNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            tieBrthProv.setAdapter(adapter);
            tieBrthProv.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
        });

        tieBrthProv.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getProvinceInfoList().observe(getViewLifecycleOwner(), provinceInfos -> {
            for(int x = 0; x < provinceInfos.size(); x++){
                if(tieBrthProv.getText().toString().equalsIgnoreCase(provinceInfos.get(x).getProvName())){
                    mViewModel.setProvID(provinceInfos.get(x).getProvIDxx());
                    break;
                }
            }

            mViewModel.getAllTownNames().observe(getViewLifecycleOwner(), strings -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                tieBrthTown.setAdapter(adapter);
                tieBrthTown.setDropDownBackgroundResource(R.drawable.bg_gradient_light);
            });
        }));

        tieBrthTown.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getTownInfoList().observe(getViewLifecycleOwner(), townInfoList -> {
            for(int x = 0; x < townInfoList.size(); x++){
                if(tieBrthTown.getText().toString().equalsIgnoreCase(townInfoList.get(x).getTownName())){
                    mViewModel.setTownID(townInfoList.get(x).getTownIDxx());
                    infoModel.setcoBrthPlce(townInfoList.get(x).getTownIDxx());
                    break;
                }
            }
        }));
        spnPrmCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnPrmCntct));
        spnScnCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnScnCntct));
        spnTrtCntct.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnTrtCntct));
        spnIncmSrce.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnIncmSrce));
        spnBrwrRltn.setOnItemClickListener(new Fragment_CoMaker.OnItemClickListener(spnBrwrRltn));

    }

    private void setupWidgets(View v){
        tieLastname = v.findViewById(R.id.tie_cap_cmrLastname);
        tieFrstname = v.findViewById(R.id.tie_cap_cmrFirstname);
        tieMiddname = v.findViewById(R.id.tie_cap_cmrMiddname);
        tieSuffixxx = v.findViewById(R.id.tie_cap_cmrSuffix);
        tieNickname = v.findViewById(R.id.tie_cap_cmrNickname);
        tieBrthDate = v.findViewById(R.id.tie_cap_cmrBirthdate);
        tieBrthProv = v.findViewById(R.id.tie_cap_cmrBirthProv);
        tieBrthTown = v.findViewById(R.id.tie_cap_cmrBirthTown);
        tiePrmCntct = v.findViewById(R.id.tie_cap_cmrPrimaryContactNo);
        tieScnCntct = v.findViewById(R.id.tie_cap_cmrSecondaryContactNo);
        tieTrtCntct = v.findViewById(R.id.tie_cap_cmrTertiaryContactNo);
        tilPrmCntctPlan = v.findViewById(R.id.til_cap_cmrPrimaryCntctPlan);
        tilScnCntctPlan = v.findViewById(R.id.til_cap_cmrSecondaryCntctPlan);
        tilTrtCntctPlan = v.findViewById(R.id.til_cap_cmrTertiaryCntctPlan);
        tiePrmCntctPlan = v.findViewById(R.id.tie_cap_cmrPrimaryCntctPlan);
        tieScnCntctPlan = v.findViewById(R.id.tie_cap_cmrSecondaryCntctPlan);
        tieTrtCntctPlan = v.findViewById(R.id.tie_cap_cmrTertiaryCntctPlan);
        tieFbAcctxx = v.findViewById(R.id.tie_cap_cmrFacebookacc);
        spnIncmSrce = v.findViewById(R.id.spinner_cap_cmrIncomeSrc);
        spnBrwrRltn = v.findViewById(R.id.spinner_cap_cmrBarrowerRelation);
        spnPrmCntct = v.findViewById(R.id.spinner_cap_cmrPrimaryCntctStats);
        spnScnCntct = v.findViewById(R.id.spinner_cap_cmrSecondaryCntctStats);
        spnTrtCntct = v.findViewById(R.id.spinner_cap_cmrTertiaryCntctStats);

        tieBrthDate.addTextChangedListener(new OnDateSetListener(tieBrthDate));
        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);
        btnPrvs.setOnClickListener(v12 -> Activity_CreditApplication.getInstance().moveToPageNumber(15));
        btnNext.setOnClickListener(v1 -> {
            infoModel = new CoMakerModel(
                    Objects.requireNonNull(Objects.requireNonNull(tieLastname.getText()).toString()),
                    Objects.requireNonNull(Objects.requireNonNull(tieFrstname.getText()).toString()),
                    Objects.requireNonNull(Objects.requireNonNull(tieMiddname.getText()).toString()),
                    Objects.requireNonNull(tieSuffixxx.getText().toString()),
                    Objects.requireNonNull(Objects.requireNonNull(tieNickname.getText()).toString()),
                    Objects.requireNonNull(Objects.requireNonNull(tieBrthDate.getText()).toString()),
                    Objects.requireNonNull(tieBrthTown.getText().toString()),
                    Objects.requireNonNull(tieFbAcctxx.getText().toString()),
                    Objects.requireNonNull(spnIncomePosition),
                    Objects.requireNonNull(spnCoRelationPosition)
            );
            if(!Objects.requireNonNull(tiePrmCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnPrmryCntctPosition) == 1) {
                    infoModel.setCoMobileNo(tiePrmCntct.getText().toString(), spnPrmryCntctPosition, Integer.parseInt(Objects.requireNonNull(tiePrmCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tiePrmCntct.getText().toString(), spnPrmryCntctPosition, 0);
                }
            }
            if(!Objects.requireNonNull(tieScnCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnScndCntctPosition) == 1) {
                    infoModel.setCoMobileNo(tieScnCntct.getText().toString(), spnScndCntctPosition, Integer.parseInt(Objects.requireNonNull(tieScnCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tieScnCntct.getText().toString(), spnScndCntctPosition, 0);
                }
            }
            if(!Objects.requireNonNull(tieTrtCntct.getText()).toString().trim().isEmpty()) {
                if(Integer.parseInt(spnTrtCntctPosition)  == 1) {
                    infoModel.setCoMobileNo(tieTrtCntct.getText().toString(), spnTrtCntctPosition, Integer.parseInt(Objects.requireNonNull(tieTrtCntctPlan.getText()).toString()));
                } else {
                    infoModel.setCoMobileNo(tieTrtCntct.getText().toString(), spnTrtCntctPosition, 0);
                }
            }
            mViewModel.SubmitComaker(infoModel, Fragment_CoMaker.this);
        });
    }

    @SuppressLint("NewApi")
    private void setFieldValues(ECreditApplicantInfo foCredApp) {
        if(foCredApp.getComakerx() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredApp.getComakerx());
                Log.e(TAG + " jsonCon", loJson.toString());
                // Value setter goes here
                spnBrwrRltn.setText(CreditAppConstants.CO_MAKER_RELATIONSHIP[Integer.parseInt(loJson.getString("sReltnCde"))], false);

                mViewModel.setSpnCMakerRelation(loJson.getString("sReltnCde"));
                tieLastname.setText( (!loJson.getString("sLastName").equalsIgnoreCase("")) ?  loJson.getString("sLastName") : "");
                tieFrstname.setText( (!loJson.getString("sFrstName").equalsIgnoreCase("")) ?  loJson.getString("sFrstName") : "");
                tieMiddname.setText( (!loJson.getString("sMiddName").equalsIgnoreCase("")) ?  loJson.getString("sMiddName") : "");
                tieSuffixxx.setText( (!loJson.getString("sSuffixNm").equalsIgnoreCase("")) ?  loJson.getString("sSuffixNm") : "");
                tieNickname.setText( (!loJson.getString("sNickName").equalsIgnoreCase("")) ?  loJson.getString("sNickName") : "");
                tieBrthDate.setText( (!loJson.getString("dBirthDte").equalsIgnoreCase("")) ?  loJson.getString("dBirthDte") : "");

                if(!loJson.getString("sBirthPlc").equalsIgnoreCase("")) {
                    mViewModel.getTownProvinceByTownName(loJson.getString("sBirthPlc")).observe(getViewLifecycleOwner(), townProvinceInfo -> {
                        tieBrthTown.setText(townProvinceInfo.sTownName);
                        tieBrthProv.setText(townProvinceInfo.sProvName);
                        mViewModel.setTownID(townProvinceInfo.sTownIDxx);
                        mViewModel.setProvID(townProvinceInfo.sProvIDxx);
                    });
                }
//                spnIncmSrce
                spnIncmSrce.setText(CreditAppConstants.CO_MAKER_INCOME_SOURCE[Integer.parseInt(loJson.getString("cIncmeSrc"))], false);
                mViewModel.setSpnCMakeIncomeSource(loJson.getString("cIncmeSrc"));
                JSONArray loCPArry = loJson.getJSONArray("mobile_number");
                for(int x = 0; x < loCPArry.length(); x++) {

                    if(x == 0) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            tiePrmCntct.setText(loJsonCp.getString("sMobileNo"));
                            spnPrmCntct.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))], false);
                            mViewModel.setPrimaryContact(loJsonCp.getString("cPostPaid"));
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                tiePrmCntctPlan.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    } else if(x == 1) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            tieScnCntct.setText(loJsonCp.getString("sMobileNo"));
                            spnScnCntct.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))], false);
                            mViewModel.setSecondaryContact(loJsonCp.getString("cPostPaid"));
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                tieScnCntctPlan.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    } else if(x == 2) {
                        JSONObject loJsonCp = loCPArry.getJSONObject(x);
                        if( !"".equalsIgnoreCase(loJsonCp.getString("sMobileNo")) &&
                                !"".equalsIgnoreCase(loJsonCp.getString("cPostPaid")) ) {
                            tieTrtCntct.setText(loJsonCp.getString("sMobileNo"));
                            spnTrtCntct.setText(CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(loJsonCp.getString("cPostPaid"))], false);
                            mViewModel.setTertiaryContact(loJsonCp.getString("cPostPaid"));
                            if("1".equalsIgnoreCase(loJsonCp.getString("cPostPaid"))) {
                                tieTrtCntctPlan.setText(String.valueOf(loJsonCp.getInt("nPostYear")));
                            }
                        }
                    }

                }

                tieFbAcctxx.setText( (!loJson.getString("sFBAcctxx").equalsIgnoreCase("")) ?  loJson.getString("sFBAcctxx") : "");

            } catch(JSONException e) {
                e.printStackTrace();
            }
        } else {
            tieLastname.getText().clear();
            tieFrstname.getText().clear();
            tieMiddname.getText().clear();
            tieSuffixxx.getText().clear();
            tieNickname.getText().clear();
            tieBrthDate.getText().clear();

            tieBrthTown.getText().clear();
            tieBrthProv.getText().clear();

            spnIncmSrce.getText().clear();;


            tiePrmCntct.getText().clear();
            spnPrmCntct.getText().clear();
            tiePrmCntctPlan.getText().clear();

            tieScnCntct.getText().clear();
            spnScnCntct.getText().clear();
            tieScnCntctPlan.getText().clear();

            tieTrtCntct.getText().clear();
            spnTrtCntct.getText().clear();
            tieTrtCntctPlan.getText().clear();

            tieFbAcctxx.getText().clear();;

        }
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(17);
    }

    @Override
    public void onFailedResult(String message) {
        if(!message.equalsIgnoreCase("no_comaker")) {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        } else {
            poMessage.initDialog();
            poMessage.setTitle("Credit Application");
            poMessage.setMessage("Send loan application without co-maker info?");
            poMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                Activity_CreditApplication.getInstance().moveToPageNumber(18);
            });
            poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
            poMessage.show();
        }
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnPrmCntct.equals(poView)) {
                mViewModel.setPrimaryContact(String.valueOf(i));
            }
            if (spnScnCntct.equals(poView)) {
                mViewModel.setSecondaryContact(String.valueOf(i));
            }
            if (spnTrtCntct.equals(poView)) {
                mViewModel.setTertiaryContact(String.valueOf(i));
            }
            if (spnIncmSrce.equals(poView)) {
                mViewModel.setSpnCMakeIncomeSource(String.valueOf(i));
            }
            if (spnBrwrRltn.equals(poView)) {
                mViewModel.setSpnCMakerRelation(String.valueOf(i));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}