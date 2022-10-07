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

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpousePensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpousePensionInfo;

import java.util.Objects;

public class Fragment_SpousePensionInfo extends Fragment implements ViewModelCallBack {

    private VMSpousePensionInfo mViewModel;
    private SpousePensionInfoModel infoModel;
    private RadioGroup rgPensionSector;
    private RadioButton rbGovt, rbPrivate;
    private TextInputEditText txtPensionAmt, txtRetirementYr, txtOtherSrc, txtOtherSrcInc;
    private Button btnNext, btnPrvs;

    public static Fragment_SpousePensionInfo newInstance() {
        return new Fragment_SpousePensionInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spouse_pension_info, container, false);
        infoModel = new SpousePensionInfoModel();
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMSpousePensionInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            mViewModel.setDetailInfo(eCreditApplicantInfo);
            setFieldValues(eCreditApplicantInfo);
        });
    }

    private void initWidgets(View v) {
        rgPensionSector = v.findViewById(R.id.rg_sector);
        rbGovt = v.findViewById(R.id.rb_government);
        rbPrivate = v.findViewById(R.id.rb_private);
        txtPensionAmt = v.findViewById(R.id.txt_pension_amt);
        txtRetirementYr = v.findViewById(R.id.txt_retirement_yr);
        txtOtherSrc = v.findViewById(R.id.txt_other_source);
        txtOtherSrcInc = v.findViewById(R.id.txt_other_source_income);
        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);

        txtPensionAmt.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtPensionAmt));
        txtOtherSrcInc.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(txtOtherSrcInc));
        rgPensionSector.setOnCheckedChangeListener((group, checkedId) -> {
           if(checkedId == rbGovt.getId()) {
               mViewModel.setPensionSec("0");
           }
           else if(checkedId == rbPrivate.getId()) {
               mViewModel.setPensionSec("1");
           }
        });

        btnNext.setOnClickListener(view -> save());
        btnPrvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_CreditApplication.getInstance().moveToPageNumber(10);
            }
        });
    }

    private void save() {
        infoModel.setsPensionAmt(Objects.requireNonNull(txtPensionAmt.getText().toString()));
        infoModel.setsRetirementYr(Objects.requireNonNull(txtRetirementYr.getText().toString()));
        infoModel.setsOtherSrc(Objects.requireNonNull(txtOtherSrc.getText().toString()));
        infoModel.setsOtherSrcIncx(Objects.requireNonNull(txtOtherSrcInc.getText().toString()));
        mViewModel.Save(infoModel, Fragment_SpousePensionInfo.this);
    }

    private void setFieldValues(ECreditApplicantInfo foCredtAp) {
        if(foCredtAp.getSpsPensn() != null) {
            try {
                JSONObject loJson = new JSONObject(foCredtAp.getSpsPensn());
                JSONObject loPension = loJson.getJSONObject("pensioner");
                JSONObject loIncomex = loJson.getJSONObject("other_income");

                if(!"".equalsIgnoreCase(loPension.getString("cPenTypex"))) {
                    if("0".equalsIgnoreCase(loPension.getString("cPenTypex"))) {
                        rgPensionSector.check(R.id.rb_government);
                    } else if("1".equalsIgnoreCase(loPension.getString("cPenTypex"))) {
                        rgPensionSector.check(R.id.rb_private);
                    }
                    mViewModel.setPensionSec(loPension.getString("cPenTypex"));
                }

                txtPensionAmt.setText( (!"".equalsIgnoreCase(loPension.getString("nPensionx"))) ? loPension.getString("nPensionx") : "" );
                txtRetirementYr.setText( (!"".equalsIgnoreCase(loPension.getString("nRetrYear"))) ? loPension.getString("nRetrYear") : "" );

                if(loIncomex != null) {
                    txtOtherSrcInc.setText( (!"".equalsIgnoreCase(loIncomex.getString("sOthrIncm"))) ? loIncomex.getString("sOthrIncm") : "" );
                }

            } catch(JSONException e) {
                e.printStackTrace();
            }
        } else {
            rgPensionSector.clearCheck();
            txtPensionAmt.setText("");
            txtRetirementYr.setText("");
            txtOtherSrc.setText("");
            txtOtherSrcInc.setText("");

        }
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(12);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }
}