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

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.DialogPrimaryIncome;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeansInfoSelection;

public class Fragment_MeansInfoSelection extends Fragment implements ViewModelCallBack {

    private VMMeansInfoSelection.MeansInfo infoModel;
    private VMMeansInfoSelection mViewModel;

    private CheckBox cbEmployed;
    private CheckBox cbSEmployd;
    private CheckBox cbFinancex;
    private CheckBox cbPensionx;

    private Button btnNext;
    private Button btnPrvs;

    private String TransNox;

    public static Fragment_MeansInfoSelection newInstance() {
        return new Fragment_MeansInfoSelection();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_means_info_selection, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        initWidgets(view);

        return view;
    }

    private void initWidgets(View v){
        cbEmployed = v.findViewById(R.id.cb_employed);
        cbSEmployd = v.findViewById(R.id.cb_sEmployed);
        cbFinancex = v.findViewById(R.id.cb_finance);
        cbPensionx = v.findViewById(R.id.cb_pension);

        btnNext = v.findViewById(R.id.btn_creditAppNext);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);

        btnPrvs.setOnClickListener(view -> Activity_CreditApplication.getInstance().moveToPageNumber(1));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMeansInfoSelection.class);
        mViewModel.setTransNox(TransNox);
        
        mViewModel.getCreditApplicantInfo(TransNox).observe(getViewLifecycleOwner(), eCreditApplicantInfo -> mViewModel.setGOCasDetailInfo(eCreditApplicantInfo));
        btnNext.setOnClickListener(view -> {
            infoModel = new VMMeansInfoSelection.MeansInfo();
            infoModel.setEmployed(cbEmployed.isChecked()? "1" : "0");
            infoModel.setSelfEmployed(cbSEmployd.isChecked()? "1" : "0");
            infoModel.setFinance(cbFinancex.isChecked()? "1" : "0");
            infoModel.setPension(cbPensionx.isChecked()? "1" : "0");
            mViewModel.SaveMeansInfo(infoModel, Fragment_MeansInfoSelection.this);
        });
    }

    @Override
    public void onSaveSuccessResult(String args) {
        Activity_CreditApplication.getInstance().moveToPageNumber(Integer.parseInt(args));
    }

    @Override
    public void onFailedResult(String message) {
        if (message.equalsIgnoreCase("means_info")) {
            new DialogPrimaryIncome(getActivity(), infoModel.getSourcesOfIncome(), (dialog, primary) -> {
                dialog.dismiss();
                infoModel = new VMMeansInfoSelection.MeansInfo();
                infoModel.setPrimaryx(primary);
                infoModel.setEmployed(cbEmployed.isChecked()? "1" : "0");
                infoModel.setSelfEmployed(cbSEmployd.isChecked()? "1" : "0");
                infoModel.setFinance(cbFinancex.isChecked()? "1" : "0");
                infoModel.setPension(cbPensionx.isChecked()? "1" : "0");
                mViewModel.SaveMeansInfo(infoModel, Fragment_MeansInfoSelection.this);
            }).show();
        } else {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        }
    }
}