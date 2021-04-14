package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIResidenceInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import static android.app.Activity.RESULT_OK;
import static org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants.SUB_FOLDER_DCP;
import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.APP_SYNC_DATA;
//import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;

public class Fragment_CIResidenceInfo extends Fragment implements ViewModelCallBack {

    private VMCIResidenceInfo mViewModel;
    private TextInputEditText tiwLandmark;
    private RadioGroup rgHouseOwnership,rgHouseType,rgHouseHolds,rgGarage;
    private MaterialButton btnNext;
    private CIResidenceInfoModel residenceInfo;
    private MessageBox poMessage;
    private ImageFileCreator poFilexx;
    private EImageInfo poImageInfo;
    public static Fragment_CIResidenceInfo newInstance() {
        return new Fragment_CIResidenceInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_residence_info, container, false);
        residenceInfo = new CIResidenceInfoModel();
        poFilexx = new ImageFileCreator(getActivity(), SUB_FOLDER_DCP, Activity_CIApplication.getInstance().getTransNox());
        poFilexx.setTransNox(Activity_CIApplication.getInstance().getTransNox());
//        poFilexx = new ImageFileCreator(Fragment_CIResidenceInfo.this , CIConstants.APP_PUBLIC_FOLDER, CIConstants.SUB_FOLDER_DCP, fileCodeDetails.get(position).sFileCode,fileCodeDetails.get(position).nEntryNox, TransNox);

        poMessage = new MessageBox(getContext());
        initWidgets(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCIResidenceInfo.class);
        mViewModel.setsTransNox(Activity_CIApplication.getInstance().getTransNox());
        mViewModel.setnLatitude("0.0");
        mViewModel.setnLogitude("0.0");
        mViewModel.getCIByTransNox(Activity_CIApplication.getInstance().getTransNox()).observe(getViewLifecycleOwner(), eciEvaluation -> {
            if (eciEvaluation != null){
                mViewModel.setCurrentCIDetail(eciEvaluation);
                tiwLandmark.setText(eciEvaluation.getLandMark());
//                House Ownership
                if (eciEvaluation.getOwnershp() != null){
//                    Loop for radio button not clickable
                    for(int i = 0; i < rgHouseOwnership.getChildCount(); i++){
                        ((RadioButton)rgHouseOwnership.getChildAt(i)).setClickable(false);
                    }
                    if(eciEvaluation.getOwnershp().equalsIgnoreCase("0")){
                        rgHouseOwnership.check(R.id.rb_ci_owner);
                    }
                    else if(eciEvaluation.getOwnershp().equalsIgnoreCase("1")){
                        rgHouseOwnership.check(R.id.rb_ci_rent);
                    }
                    else if(eciEvaluation.getOwnershp().equalsIgnoreCase("2")){
                        rgHouseOwnership.check(R.id.rb_ci_careTaker);
                    }

                }
//                HouseHolds
                if(eciEvaluation.getOwnOther() != null){
                    for(int i = 0; i < rgHouseHolds.getChildCount(); i++){
                        ((RadioButton)rgHouseHolds.getChildAt(i)).setClickable(false);
                    }
                    if(eciEvaluation.getOwnOther().equalsIgnoreCase("0")){
                        rgHouseHolds.check(R.id.rb_ci_family);
                    }
                    else if(eciEvaluation.getOwnOther().equalsIgnoreCase("1")){
                        rgHouseHolds.check(R.id.rb_ci_familySiblings);
                    }
                    else if(eciEvaluation.getOwnOther().equalsIgnoreCase("2")){
                        rgHouseHolds.check(R.id.rb_ci_relatives);
                    }
                }
//                House Type
                if (eciEvaluation.getHouseTyp() != null){
                    for(int i = 0; i < rgHouseType.getChildCount(); i++){
                        ((RadioButton)rgHouseType.getChildAt(i)).setClickable(false);
                    }
                    if(eciEvaluation.getHouseTyp().equalsIgnoreCase("0")){
                        rgHouseType.check(R.id.rb_ci_houseConcrete);
                    }
                    else if(eciEvaluation.getHouseTyp().equalsIgnoreCase("1")){
                        rgHouseType.check(R.id.rb_ci_houseCombine);
                    }
                    else if(eciEvaluation.getHouseTyp().equalsIgnoreCase("2")){
                        rgHouseType.check(R.id.rb_ci_houseWood);
                    }
                }
//                Has Garage
               if (eciEvaluation.getGaragexx() != null){
                   for(int i = 0; i < rgGarage.getChildCount(); i++){
                       ((RadioButton)rgGarage.getChildAt(i)).setClickable(false);
                   }
                   if(eciEvaluation.getGaragexx().equalsIgnoreCase("0")){
                       rgGarage.check(R.id.rb_ci_yes);
                   }else if(eciEvaluation.getGaragexx().equalsIgnoreCase("1")){
                       rgGarage.check(R.id.rb_ci_no);
                   }
               }
                if(eciEvaluation.getLatitude() != null){
                    residenceInfo.setLatitude(eciEvaluation.getLatitude());
                }
                if(eciEvaluation.getLongitud() != null){
                    residenceInfo.setLongitud(eciEvaluation.getLongitud());
                    btnNext.setText("Next");
                }

            }else {
                ECIEvaluation eciEvaluation1 = new ECIEvaluation();
                eciEvaluation1.setTransNox(Activity_CIApplication.getInstance().getTransNox());
                mViewModel.insertNewCiApplication(eciEvaluation1);
            }
        });
        rgHouseOwnership.setOnCheckedChangeListener(new OnCIHouseSelectionListener(rgHouseOwnership,mViewModel));
        rgHouseType.setOnCheckedChangeListener(new OnCIHouseSelectionListener(rgHouseType,mViewModel));
        rgHouseHolds.setOnCheckedChangeListener(new OnCIHouseSelectionListener(rgHouseHolds,mViewModel));
        rgGarage.setOnCheckedChangeListener(new OnCIHouseSelectionListener(rgGarage,mViewModel));
        btnNext.setOnClickListener(v -> {
            submitResidence();
        });
    }
    public void  initWidgets(View view){
        tiwLandmark = view.findViewById(R.id.tie_landmark);
        rgHouseHolds = view.findViewById(R.id.rg_ci_houseHold);
        rgHouseOwnership = view.findViewById(R.id.rg_ci_ownership);
        rgHouseType = view.findViewById(R.id.rg_ci_houseType);
        rgGarage = view.findViewById(R.id.rg_ci_garage);
        btnNext = view.findViewById(R.id.btn_ciAppNext);

    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onSaveSuccessResult(String args) {

        Activity_CIApplication.getInstance().moveToPageNumber(1);
    }

    @Override
    public void onFailedResult(String message) {
        if (message.trim().equalsIgnoreCase("empty"))
        {
            showDialogImg();
        }else {
            GToast.CreateMessage(getActivity(), message, GToast.ERROR).show();
        }

    }
    public void showDialogImg(){
        poMessage.initDialog();
        poMessage.setTitle("Residence Info");
        poMessage.setMessage("Please take applicant residence picture. \n");
        poMessage.setPositiveButton("Okay", (view, dialog) -> {
            dialog.dismiss();
            poFilexx.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                residenceInfo.setLatitude(String.valueOf(latitude));
                residenceInfo.setLongitud(String.valueOf(longitude));
                poImageInfo = new EImageInfo();
                poImageInfo.setDtlSrcNo(Activity_CIApplication.getInstance().getTransNox());
                poImageInfo.setSourceNo(Activity_CIApplication.getInstance().getTransNox());
                poImageInfo.setSourceCD("CI");
                poImageInfo.setImageNme(FileName);
                poImageInfo.setFileLoct(photPath);
                poImageInfo.setFileCode("0102");
                poImageInfo.setLatitude(String.valueOf(latitude));
                poImageInfo.setLongitud(String.valueOf(longitude));
                startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
            });
        });
        poMessage.setNegativeButton("Cancel", (view, dialog) -> {
            dialog.dismiss();
        });
        poMessage.show();
    }
    class OnCIHouseSelectionListener implements RadioGroup.OnCheckedChangeListener{

        View rbView;
        VMCIResidenceInfo mViewModel;
        OnCIHouseSelectionListener(View view, VMCIResidenceInfo viewModel)
        {
            this.rbView = view;
            this.mViewModel = viewModel;
        }


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(rbView.getId() == R.id.rg_ci_ownership){
                if(checkedId == R.id.rb_ci_owner) {
                    residenceInfo.setOwnershp("0");
                }
                else if(checkedId == R.id.rb_ci_rent) {
                    residenceInfo.setOwnershp("1");
                }else if(checkedId == R.id.rb_ci_careTaker) {
                    residenceInfo.setOwnershp("2");
                }

            }
            if(rbView.getId() == R.id.rg_ci_houseHold){
                if(checkedId == R.id.rb_ci_family) {
                    residenceInfo.setOwnOther("0");
                }
                else if(checkedId == R.id.rb_ci_familySiblings) {
                    residenceInfo.setOwnOther("1");
                }else if(checkedId == R.id.rb_ci_relatives) {
                    residenceInfo.setOwnOther("2");
                }
            }
            if(rbView.getId() == R.id.rg_ci_houseType){
                if(checkedId == R.id.rb_ci_houseConcrete) {
                    residenceInfo.setHouseTyp("0");
                }
                else if(checkedId == R.id.rb_ci_houseCombine) {
                    residenceInfo.setHouseTyp("1");
                }else if(checkedId == R.id.rb_ci_houseWood) {
                    residenceInfo.setHouseTyp("2");
                }
            }
            if(rbView.getId() == R.id.rg_ci_garage){
                if(checkedId == R.id.rb_ci_yes) {
                    residenceInfo.setGaragexx("0");
                }
                else if(checkedId == R.id.rb_ci_no) {
                    residenceInfo.setGaragexx("1");
                }
            }
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("REQUEST CODE", String.valueOf(requestCode));
        Log.e("RESULT CODE", String.valueOf(resultCode));
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK) {
                try {
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(poImageInfo.getFileLoct()));
                    mViewModel.saveResidenceImageInfo(poImageInfo);
                    submitResidence();
                    mViewModel.PostResidenceDetail(poImageInfo, new VMCIResidenceInfo.OnImportCallBack() {
                        @Override
                        public void onPostResidenceInfo() {

                            GNotifBuilder.createNotification(getActivity(), "CI Evaluation", "Residence Info Picture posting...",APP_SYNC_DATA).show();
                        }

                        @Override
                        public void onSuccessResidenceInfo() {
                            GNotifBuilder.createNotification(getActivity(), "CI Evaluation", "Residence Info Post Successfulluy",APP_SYNC_DATA).show();
                        }

                        @Override
                        public void onFailedResidenceInfo(String message) {
                            GNotifBuilder.createNotification(getActivity(), "CI Evaluation", message,APP_SYNC_DATA).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                residenceInfo.setLongitud("");
                residenceInfo.setLatitude("");
            }
        }
    }
public void submitResidence(){
    residenceInfo.setLandMark(tiwLandmark.getText().toString());
    mViewModel.saveCIResidence(residenceInfo, Fragment_CIResidenceInfo.this);
}
}