/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.TimeLogAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog.DialogBranchSelection;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSelfieLogin;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import static android.app.Activity.RESULT_OK;

import java.util.ArrayList;
import java.util.List;

public class Fragment_SelfieLogin extends Fragment {
    private static final String TAG = Fragment_SelfieLogin.class.getSimpleName();

    private VMSelfieLogin mViewModel;

    private TextView lblUsername, lblPosition, lblBranch, lblNotice;
    private MaterialButton btnCamera, btnBranch;
    private RecyclerView recyclerView;

    private static EEmployeeInfo poUser;
    private EImageInfo poImage;
    private ELog_Selfie poLog;
    private GLocationManager loLocation;

    private LoadDialog poLoad;
    private MessageBox poMessage;

    private static String sSlectBranch = "";
    private static String psPhotoPath = "";
    private static String pnLatittude = "";
    private static String pnLongitude = "";
    private static String psFileNamex = "";

    private static boolean isDialogShown;

    private List<EBranchInfo> paBranch = new ArrayList<>();
    private String psEmpLvl;

    public static Fragment_SelfieLogin newInstance() {
        return new Fragment_SelfieLogin();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_selfie_login, container, false);
        initWidgets(view);
        return view;
    }

    private void initWidgets(View view){
        lblUsername = view.findViewById(R.id.lbl_username);
        lblPosition = view.findViewById(R.id.lbl_userPosition);
        lblBranch = view.findViewById(R.id.lbl_userBranch);
        lblNotice = view.findViewById(R.id.lbl_notice);
        btnCamera = view.findViewById(R.id.btn_takeSelfie);
        btnBranch = view.findViewById(R.id.btn_selectBranch);
        recyclerView = view.findViewById(R.id.recyclerview_timeLog);

        poImage = new EImageInfo();
        poLog = new ELog_Selfie();
        poLoad = new LoadDialog(requireActivity());
        poMessage = new MessageBox(requireActivity());
        loLocation = new GLocationManager(requireActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMSelfieLogin.class);
        psEmpLvl = mViewModel.getEmployeeLevel();

        mViewModel.getAreaBranchList().observe(getViewLifecycleOwner(), eBranchInfos -> {
            try {
                paBranch = eBranchInfos;
                lblNotice.setVisibility(View.GONE);
                btnBranch.setVisibility(View.VISIBLE);
                if (psEmpLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))) {
                    new DialogBranchSelection(requireActivity(), paBranch).initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
                        @Override
                        public void OnSelect(String BranchCode, AlertDialog dialog) {
                            mViewModel.checkIfAlreadyLog(BranchCode, new VMSelfieLogin.OnBranchSelectedCallback() {
                                @Override
                                public void OnLoad() {

                                }

                                @Override
                                public void OnSuccess() {
                                    sSlectBranch = BranchCode;
                                    mViewModel.getBranchInfo(BranchCode).observe(getViewLifecycleOwner(), eBranchInfo -> {
                                        try{
                                            lblBranch.setText(eBranchInfo.getBranchNm());
                                            mViewModel.importInventoryTask(BranchCode);
                                        } catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    });
                                }

                                @Override
                                public void OnFailed(String message) {
                                    poMessage.initDialog();
                                    poMessage.setTitle("Selfie Login");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                                    poMessage.show();
                                }
                            });
                            dialog.dismiss();
                        }

                        @Override
                        public void OnCancel() {
//                                requireActivity().finish();
                        }
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                poUser = eEmployeeInfo;
                lblUsername.setText(eEmployeeInfo.getUserName());
                lblPosition.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                sSlectBranch = eBranchInfo.getBranchCd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getAllEmployeeTimeLog().observe(getViewLifecycleOwner(), eLog_selfies -> {
            TimeLogAdapter logAdapter = new TimeLogAdapter(eLog_selfies, sTransNox -> {
                GToast.CreateMessage(requireActivity(), "Feature not yet implemented", GToast.INFORMATION).show();
            });
            LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(logAdapter);

        });

        btnBranch.setOnClickListener(v -> new DialogBranchSelection(requireActivity(), paBranch).initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
            @Override
            public void OnSelect(String BranchCode, AlertDialog dialog) {
                sSlectBranch = BranchCode;
                mViewModel.checkIfAlreadyLog(BranchCode, new VMSelfieLogin.OnBranchSelectedCallback() {
                    @Override
                    public void OnLoad() {
                        poLoad.initDialog("Selfie Log", "Validating branch. Please wait...", false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess() {
                        poLoad.dismiss();
                        sSlectBranch = BranchCode;
                        mViewModel.getBranchInfo(BranchCode).observe(getViewLifecycleOwner(), eBranchInfo -> {
                            try{
                                lblBranch.setText(eBranchInfo.getBranchNm());
                                mViewModel.importInventoryTask(BranchCode);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void OnFailed(String message) {
                        poLoad.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Selfie Login");
                        poMessage.setMessage(message);
                        poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }
                });
                dialog.dismiss();
            }

            @Override
            public void OnCancel() {
//                        requireActivity().finish();
            }
        }));

        btnCamera.setOnClickListener(view -> {
//            if(psEmpLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))){
//                if(sSlectBranch.isEmpty()){
//                    if (psEmpLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))
//                            && sSlectBranch.isEmpty()) {
//                        lblNotice.setVisibility(View.GONE);
//                        new DialogBranchSelection(requireActivity(), paBranch).initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
//                            @Override
//                            public void OnSelect(String BranchCode, AlertDialog dialog) {
//                                mViewModel.checkIfAlreadyLog(BranchCode, new VMSelfieLogin.OnBranchSelectedCallback() {
//                                    @Override
//                                    public void OnLoad() {
//
//                                    }
//
//                                    @Override
//                                    public void OnSuccess() {
//                                        sSlectBranch = BranchCode;
//                                        mViewModel.getBranchInfo(BranchCode).observe(getViewLifecycleOwner(), eBranchInfo -> {
//                                            try{
//                                                lblBranch.setText(eBranchInfo.getBranchNm());
//                                                mViewModel.importInventoryTask(BranchCode);
//                                            } catch (Exception e){
//                                                e.printStackTrace();
//                                            }
//                                        });
//                                        mViewModel.isPermissionsGranted().observe(getViewLifecycleOwner(), isGranted -> {
//                                            if (!isGranted) {
//                                                mViewModel.getPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(requireActivity(), strings, AppConstants.PERMISION_REQUEST_CODE));
//                                            } else {
//                                                initCamera();
//                                            }
//                                        });
//                                        dialog.dismiss();
//                                    }
//
//                                    @Override
//                                    public void OnFailed(String message) {
//                                        poMessage.initDialog();
//                                        poMessage.setTitle("Selfie Login");
//                                        poMessage.setMessage(message);
//                                        poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
//                                        poMessage.show();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void OnCancel() {
////                                requireActivity().finish();
//                            }
//                        });
//                    } else {
//                        lblNotice.setVisibility(View.VISIBLE);
//                    }
//                } else {
//                    mViewModel.isPermissionsGranted().observe(getViewLifecycleOwner(), isGranted -> {
//                        if (!isGranted) {
//                            mViewModel.getPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(requireActivity(), strings, AppConstants.PERMISION_REQUEST_CODE));
//                        } else {
//                            initCamera();
//                        }
//                    });
//                }
//            } else {
            mViewModel.isPermissionsGranted().observe(getViewLifecycleOwner(), isGranted -> {
                if (!isGranted) {
                    mViewModel.getPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(requireActivity(), strings, AppConstants.PERMISION_REQUEST_CODE));
                } else {
                    initCamera();
                }
            });
//            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                poLog.setTransact(AppConstants.CURRENT_DATE);
                poLog.setBranchCd(sSlectBranch);
                poLog.setEmployID(poUser.getEmployID());
                poLog.setLogTimex(new AppConstants().DATE_MODIFIED);
                poLog.setLatitude(pnLatittude);
                poLog.setLongitud(pnLongitude);
                poLog.setSendStat("0");

                poImage.setFileCode("0021");
                poImage.setSourceNo(poUser.getClientID());
                poImage.setDtlSrcNo(poUser.getUserIDxx());
                poImage.setSourceCD("LOGa");
                poImage.setImageNme(psFileNamex);
                poImage.setFileLoct(psPhotoPath);
                poImage.setLatitude(pnLatittude);
                poImage.setLongitud(pnLongitude);
                poImage.setMD5Hashx(WebFileServer.createMD5Hash(psPhotoPath));
                poImage.setCaptured(new AppConstants().DATE_MODIFIED);
                mViewModel.loginTimeKeeper(poLog, poImage, new VMSelfieLogin.OnLoginTimekeeperListener() {
                    @Override
                    public void OnLogin() {
                        poLoad.initDialog("Selfie Log", "Sending your time in. Please wait...", false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess(String args) {
                        try {
                            poLoad.dismiss();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        psPhotoPath = "";
                        pnLatittude = "";
                        pnLongitude = "";
                        psFileNamex = "";
                        showMessageDialog("Your time in has been save to server.");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void OnFailed(String message) {
                        try {
                            poLoad.dismiss();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        showMessageDialog("Your time in has been save.");
                    }
                });
            }
        } else if(requestCode == GLocationManager.GLocationResCode){
            boolean isEnabled = loLocation.isLocationEnabled();
            if(isEnabled){
                initCamera();
            } else {
                requestLocationEnabled();
            }
        }
    }

    private void initCamera(){
        try {
            ImageFileCreator loImage = new ImageFileCreator(requireActivity(), AppConstants.SUB_FOLDER_SELFIE_LOG, poUser.getUserIDxx());
            loImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                new LocationRetriever(requireActivity(), requireActivity()).getLocation((message, latitude1, longitude1) -> {
                    psPhotoPath = photPath;
                    psFileNamex = FileName;
                    pnLatittude = String.valueOf(latitude1);
                    pnLongitude = String.valueOf(longitude1);
                    startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                });
            });
        } catch(NullPointerException e) {
            e.printStackTrace();
        } catch(RuntimeException r) {
            r.printStackTrace();
        }
    }

    private void requestLocationEnabled(){
        if(isDialogShown) {
            return;
        }
        poMessage.initDialog();
        poMessage.setTitle("Selfie Login");
        poMessage.setMessage("Please enable your device service location.");
        poMessage.setNegativeButton("Cancel", (view12, dialog) -> {
            dialog.dismiss();
            isDialogShown = false;
        });
        poMessage.setPositiveButton("Go to Settings", (view, dialog) -> {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GLocationManager.GLocationResCode);
            dialog.dismiss();
            isDialogShown = false;
        });
        poMessage.show();
        isDialogShown = true;
    }

    private void showMessageDialog(String message){
        poMessage.initDialog();
        poMessage.setTitle("Selfie Log");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) ->{
            checkEmployeeLevel();
            dialog.dismiss();
        });
        poMessage.show();
    }

    private void checkEmployeeLevel(){
        if(psEmpLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))){
            Intent loIntent = new Intent(requireActivity(), Activity_CashCounter.class);
            loIntent.putExtra("BranchCd", poLog.getBranchCd());
            loIntent.putExtra("cancelable", false);
            requireActivity().startActivity(loIntent);
            requireActivity().finish();
        }
    }
}