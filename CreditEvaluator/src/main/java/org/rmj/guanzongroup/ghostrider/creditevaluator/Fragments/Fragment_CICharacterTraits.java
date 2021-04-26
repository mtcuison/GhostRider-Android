/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Dialog.DialogCIReason;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CharacterTraitsInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCICharacteristics;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.APP_SYNC_DATA;

public class Fragment_CICharacterTraits extends Fragment implements ViewModelCallBack {

    private static final String TAG = Fragment_CICharacterTraits.class.getSimpleName();
    private VMCICharacteristics mViewModel;
    private CheckBox cbGambler, cbWomanizer, cbHeavyBrrw, cbQuarrel, cbRepo, cbMortage, cbArrogance, cbOthers;
    private TextInputEditText cRemarks;
    private TextInputLayout tilRemarks;
    private MaterialButton btnNext, btnPrevious;

    private TextView sCompnyNm;
    private TextView dTransact;
    private TextView sModelNm;
    private TextView nTerm;
    private TextView nMobile;
    private TextView sTransNox;

    private CharacterTraitsInfoModel infoModel;
    private MessageBox poMessage;
    public static Fragment_CICharacterTraits newInstance() {
        return new Fragment_CICharacterTraits();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_character_traits, container, false);
        poMessage = new MessageBox(getContext());
        initWidgets(root);
        initClientInfo();
        return root;
    }
    public void initWidgets(View view){
        cbGambler = view.findViewById(R.id.cb_ci_gambler);
        cbWomanizer = view.findViewById(R.id.cb_ci_womanizer);
        cbHeavyBrrw = view.findViewById(R.id.cb_ci_heavyBrwr);
//        cbQuarrel = view.findViewById(R.id.cb_ci_quarrel);
        cbRepo = view.findViewById(R.id.cb_ci_repo);
        cbMortage = view.findViewById(R.id.cb_ci_mort);
        cbArrogance = view.findViewById(R.id.cb_ci_arrogance);
        cbOthers = view.findViewById(R.id.cb_ci_others);
        cRemarks = view.findViewById(R.id.tie_ci_cRemarks);
        tilRemarks = view.findViewById(R.id.til_ci_cRemarks);

        btnNext = view.findViewById(R.id.btn_ciAppNext);
        btnPrevious = view.findViewById(R.id.btn_ciAppPrvs);

        sCompnyNm = view.findViewById(R.id.lbl_ci_applicantName);
        dTransact = view.findViewById(R.id.lbl_ci_applicationDate);
        sModelNm = view.findViewById(R.id.lbl_ci_modelName);
        nTerm = view.findViewById(R.id.lbl_ci_accntTerm);
        nMobile = view.findViewById(R.id.lbl_ci_mobileNo);
        sTransNox = view.findViewById(R.id.lbl_ci_transNox);

    }

    public void initClientInfo(){
        sTransNox.setText(Activity_CIApplication.getInstance().getTransNox());
        sCompnyNm.setText(Activity_CIApplication.getInstance().getsCompnyNm());
        dTransact.setText(Activity_CIApplication.getInstance().getdTransact());
        sModelNm.setText(Activity_CIApplication.getInstance().getsModelNm());
        nTerm.setText(Activity_CIApplication.getInstance().getnTerm());
        nMobile.setText(Activity_CIApplication.getInstance().getnMobile());
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCICharacteristics.class);
        mViewModel.setsTransNox(Activity_CIApplication.getInstance().getTransNox());
        mViewModel.getCIByTransNox().observe(getViewLifecycleOwner(), eciEvaluation -> {
            mViewModel.setCurrentCIDetail(eciEvaluation);
            initData(eciEvaluation);
        });

        cbGambler.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbGambler));
        cbWomanizer.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbWomanizer));
        cbHeavyBrrw.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbHeavyBrrw));
        cbRepo.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbRepo));
        cbMortage.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbMortage));
        cbArrogance.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbArrogance));
        cbOthers.setOnCheckedChangeListener(new OnCharacterTraitSelectionListener(cbOthers));
        btnNext.setOnClickListener(v ->  {
            if (Integer.parseInt(infoModel.getcTranstat()) > 0 || infoModel.getsRemarks() != null){
                mViewModel.saveCICHaracterTraits(infoModel, Fragment_CICharacterTraits.this);
            }else{
                initDialog();
            }
        });
        btnPrevious.setOnClickListener(v -> Activity_CIApplication.getInstance().moveToPageNumber(2));
        // TODO: Use the ViewModel

    }
    public void initData(ECIEvaluation eciEvaluation){
        cbGambler.setChecked(false);
        cbWomanizer.setChecked(false);
        cbHeavyBrrw.setChecked(false);
//        cbQuarrel.setChecked(false);
        cbRepo.setChecked(false);
        cbMortage.setChecked(false);
        cbArrogance.setChecked(false);
        cbOthers.setChecked(false);
        infoModel = new CharacterTraitsInfoModel();
        if (eciEvaluation.getGamblerx() != null && eciEvaluation.getGamblerx().equalsIgnoreCase("1")){
            infoModel.setCbGambler(eciEvaluation.getGamblerx());
            cbGambler.setChecked(true);
        }

        if (eciEvaluation.getWomanizr() != null && eciEvaluation.getWomanizr().equalsIgnoreCase("1")){
            infoModel.setCbWomanizer(eciEvaluation.getWomanizr());
            cbWomanizer.setChecked(true);}

        if (eciEvaluation.getHvyBrwer() != null  && eciEvaluation.getHvyBrwer().equalsIgnoreCase("1")){
            infoModel.setCbHeavyBrrw(eciEvaluation.getHvyBrwer());
            cbHeavyBrrw.setChecked(true);}

        if (eciEvaluation.getWithRepo() != null && eciEvaluation.getWithRepo().equalsIgnoreCase("1")) {
            infoModel.setCbRepo(eciEvaluation.getWithRepo());
            cbRepo.setChecked(true);
        }

        if (eciEvaluation.getWithMort() != null && eciEvaluation.getWithMort().equalsIgnoreCase("1")) {
            infoModel.setCbMortage(eciEvaluation.getWithMort());
            cbMortage.setChecked(true);
        }

        if (eciEvaluation.getArrogant() != null && eciEvaluation.getArrogant().equalsIgnoreCase("1")) {
            infoModel.setCbArrogance(eciEvaluation.getArrogant());
            cbArrogance.setChecked(true);
        }
        if (eciEvaluation.getOtherBad() != null && eciEvaluation.getOtherBad().equalsIgnoreCase("1")) {
            infoModel.setCbOthers(eciEvaluation.getOtherBad());
            cbOthers.setChecked(true);
        }
        if (eciEvaluation.getTranStat() != null) {
            infoModel.setcTranstat(eciEvaluation.getTranStat());
        }
        if (eciEvaluation.getRemarksx() != null) {
            infoModel.setsRemarks(eciEvaluation.getRemarksx());
        }
    };
    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {
        GNotifBuilder.createNotification(getActivity(), "CI Evaluation", args,APP_SYNC_DATA).show();
        getActivity().finish();
    }

    @Override
    public void onFailedResult(String message) {
        poMessage.initDialog();
        poMessage.setTitle("CI Evaluation");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();
    }
    class OnCharacterTraitSelectionListener implements CheckBox.OnCheckedChangeListener{

        View cbView;

        OnCharacterTraitSelectionListener(View view){
            this.cbView = view;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(cbView.getId() == R.id.cb_ci_gambler){
                if(buttonView.isChecked()) {
                    infoModel.setCbGambler("1");
                } else {
                    infoModel.setCbGambler("0");
                }
            } else if(cbView.getId() == R.id.cb_ci_womanizer){
                if(buttonView.isChecked()){
                    infoModel.setCbWomanizer("1");
                } else {
                    infoModel.setCbWomanizer("0");
                }
            } else if(cbView.getId() == R.id.cb_ci_heavyBrwr){
                if(buttonView.isChecked()){
                    infoModel.setCbHeavyBrrw("1");
                } else {
                    infoModel.setCbHeavyBrrw("0");
                }
            }
            else if(cbView.getId() == R.id.cb_ci_repo){
                if(buttonView.isChecked()){
                    infoModel.setCbRepo("1");
                } else {
                    infoModel.setCbRepo("0");
                }
            }
            else if(cbView.getId() == R.id.cb_ci_arrogance){
                if(buttonView.isChecked()){
                    infoModel.setCbArrogance("1");
                } else {
                    infoModel.setCbArrogance("0");
                }
            }
            else if(cbView.getId() == R.id.cb_ci_others){
                if(buttonView.isChecked()){
                    infoModel.setCbOthers("1");
                } else {
                    infoModel.setCbOthers("0");
                }
            }
        }
    }
    public void initDialog(){
        DialogCIReason loDialog = new DialogCIReason(getActivity());
        loDialog.initDialogCIReason((dialog, transtat, reason) -> {
            infoModel.setcTranstat(transtat);
            infoModel.setsRemarks(reason);
            dialog.dismiss();
            mViewModel.saveCICHaracterTraits(infoModel, Fragment_CICharacterTraits.this);

        });
        loDialog.show();
    }

}