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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDisbursementInfo;

import java.util.Objects;

public class Fragment_DisbursementInfo extends Fragment implements ViewModelCallBack {

    private VMDisbursementInfo mViewModel;
    private DisbursementInfoModel infoModel;
    private static final String TAG = Fragment_DisbursementInfo.class.getSimpleName();
    private String TransNox;
    private View v;

    private AutoCompleteTextView spnTypex;
    private String typeX = "-1";
    private TextInputEditText tieElctx;
    private TextInputEditText tieWater;
    private TextInputEditText tieFoodx;
    private TextInputEditText tieLoans;
    private TextInputEditText tieBankN;
    private TextInputEditText tieCCBnk;
    private TextInputEditText tieLimit;
    private TextInputEditText tieYearS;

    private Button btnPrev;
    private Button btnNext;

    public static Fragment_DisbursementInfo newInstance() {
        return new Fragment_DisbursementInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_disbursement, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        infoModel = new DisbursementInfoModel();
        setupWidgets(v);
        return v;
    }
    private void setupWidgets(View view){

        tieElctx = view.findViewById(R.id.tie_cap_dbmElectricity);
        tieWater = view.findViewById(R.id.tie_cap_dbmWater);
        tieFoodx = view.findViewById(R.id.tie_cap_dbmFood);
        tieLoans = view.findViewById(R.id.tie_cap_dbmLoans);
        tieBankN = view.findViewById(R.id.tie_cap_dbmBankName);
        spnTypex = view.findViewById(R.id.spinner_cap_dbmAccountType);
        tieCCBnk = view.findViewById(R.id.tie_cap_dbmBankNameCC);
        tieLimit = view.findViewById(R.id.tie_cap_dbmCreditLimit);
        tieYearS = view.findViewById(R.id.tie_cap_dbmYearStarted);

        tieElctx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieElctx));
        tieWater.addTextChangedListener(new FormatUIText.CurrencyFormat(tieWater));
        tieFoodx.addTextChangedListener(new FormatUIText.CurrencyFormat(tieFoodx));
        tieLoans.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLoans));
        tieLimit.addTextChangedListener(new FormatUIText.CurrencyFormat(tieLimit));

        btnNext = view.findViewById(R.id.btn_creditAppNext);
        btnPrev = view.findViewById(R.id.btn_creditAppPrvs);

        btnPrev.setOnClickListener(v -> {
            try {
                Activity_CreditApplication.getInstance().moveToPageNumber(mViewModel.getPreviousPage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMDisbursementInfo.class);
        mViewModel.setTransNox(Activity_CreditApplication.getInstance().getTransNox());
        mViewModel.getCreditApplicationInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setCreditApplicantInfo(eCreditApplicantInfo));

        mViewModel.getAccountType().observe(getViewLifecycleOwner(), stringArrayAdapter -> spnTypex.setAdapter(stringArrayAdapter));

        tieElctx.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieElctx));
        tieWater.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieWater));
        tieFoodx.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieFoodx));
        tieLoans.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieLoans));
        tieLimit.addTextChangedListener(new TextFormatter.OnTextChangedCurrencyFormatter(tieLimit));
        spnTypex.setOnItemClickListener((parent, view, position, id) -> {
            typeX = String.valueOf(position);
            mViewModel.setType(typeX);
        });
        btnNext.setOnClickListener(view -> {
            infoModel.setStypeX(typeX);
            infoModel.setElctX(Objects.requireNonNull(tieElctx.getText()).toString().replace(",", ""));
            infoModel.setFoodX(Objects.requireNonNull(tieFoodx.getText().toString().replace(",", "")));
            infoModel.setWaterX(Objects.requireNonNull(tieWater.getText().toString().replace(",", "")));
            infoModel.setLoans(Objects.requireNonNull(tieLoans.getText().toString().replace(",", "")));
            infoModel.setBankN(tieBankN.getText().toString());
            infoModel.setCcBnk(tieCCBnk.getText().toString());
            infoModel.setLimitCC(tieLimit.getText().toString().replace(",", ""));
            infoModel.setYearS(tieYearS.getText().toString());
            mViewModel.SubmitApplicationInfo(infoModel, Fragment_DisbursementInfo.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(13);
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }

}