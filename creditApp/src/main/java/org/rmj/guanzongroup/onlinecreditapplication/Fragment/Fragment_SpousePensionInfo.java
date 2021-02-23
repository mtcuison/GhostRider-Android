package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
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
    private MaterialButton btnNext;

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
        mViewModel.getActiveGOCasApplication().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setDetailInfo(eCreditApplicantInfo.getDetlInfo()));
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

        rgPensionSector.setOnCheckedChangeListener((group, checkedId) -> {
           if(checkedId == rbGovt.getId()) {
               mViewModel.setPensionSec("0");
           }
           else if(checkedId == rbPrivate.getId()) {
               mViewModel.setPensionSec("1");
           }
        });

        btnNext.setOnClickListener(view -> save());
    }

    private void save() {
        infoModel.setsPensionAmt(Objects.requireNonNull(txtPensionAmt.getText().toString()));
        infoModel.setsRetirementYr(Objects.requireNonNull(txtRetirementYr.getText().toString()));
        infoModel.setsOtherSrc(Objects.requireNonNull(txtOtherSrc.getText().toString()));
        infoModel.setsOtherSrcIncx(Objects.requireNonNull(txtOtherSrcInc.getText().toString()));
        mViewModel.Save(infoModel, Fragment_SpousePensionInfo.this);
    }

    @Override
    public void onSaveSuccessResult(String args) {
        GToast.CreateMessage(getActivity(), "S U x X e S s", GToast.INFORMATION).show();
    }

    @Override
    public void onFailedResult(String message) {
        GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
    }
}