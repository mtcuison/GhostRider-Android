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

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.etc.GToast;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.TimeLogAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog.DialogBranchSelection;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.OnInitializeCameraCallback;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSelfieLog;

import java.util.List;
import java.util.Map;

public class Fragment_SelfieLog extends Fragment {
    private static final String TAG = Fragment_SelfieLog.class.getSimpleName();

    private VMSelfieLog mViewModel;

    private TextView lblUsername, lblPosition, lblBranch, lblNotice;
    private MaterialButton btnCamera, btnBranch;
    private RecyclerView recyclerView;

    private ESelfieLog poLog;

    private final SelfieLog.SelfieLogDetail poSelfie = new SelfieLog.SelfieLogDetail();

    private LoadDialog poLoad;
    private MessageBox poMessage;

    private static boolean isDialogShown;

    private String psEmpLvl;

    private String lsCompx;

    private long mLastClickTime = 0;

    private final ActivityResultLauncher<Intent> poCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            try{
                int resultCode = result.getResultCode();
                if(resultCode == RESULT_OK){
                    mViewModel.TimeIn(poSelfie, new VMSelfieLog.OnLoginTimekeeperListener() {
                        @Override
                        public void OnLogin() {
                            poLoad.initDialog("Selfie Log", "Sending your time in. Please wait...", false);
                            poLoad.show();
                        }

                        @Override
                        public void OnSuccess(String args) {
                            poLoad.dismiss();
                            ValidateCashCount();
                        }

                        @Override
                        public void OnFailed(String message) {
                            poLoad.dismiss();
                            poMessage.initDialog();
                            poMessage.setTitle("Selfie Log");
                            poMessage.setMessage(message);
                            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                            poMessage.show();
                        }
                    });
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    });

    private final ActivityResultLauncher<String[]> poRequest = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Boolean fineLoct = result.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false);
                Boolean coarseL = result.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION, false);
                Boolean camerax = result.getOrDefault(
                        Manifest.permission.CAMERA, false);
                if(Boolean.FALSE.equals(camerax)){
                    Toast.makeText(requireActivity(), "Please allow camera permission to proceed.", Toast.LENGTH_SHORT).show();
                } else if(Boolean.FALSE.equals(fineLoct)){
                    Toast.makeText(requireActivity(), "Please allow permission to get device location.", Toast.LENGTH_SHORT).show();
                } else if(Boolean.FALSE.equals(coarseL)){
                    Toast.makeText(requireActivity(), "Please allow permission to get device location.", Toast.LENGTH_SHORT).show();
                } else {
                    initCamera();
                }
            } else {
                if(checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireActivity(), "Please allow camera permission to proceed.", Toast.LENGTH_SHORT).show();
                } else if(checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireActivity(), "Please allow permission to get device location.", Toast.LENGTH_SHORT).show();
                } else if(checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(requireActivity(), "Please allow permission to get device location.", Toast.LENGTH_SHORT).show();
                } else {
                    initCamera();
                }
            }
        }
    });

    private final ActivityResultLauncher<Intent> poSettings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){

            }
        }
    });

    public static Fragment_SelfieLog newInstance() {
        return new Fragment_SelfieLog();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMSelfieLog.class);
        View view =  inflater.inflate(R.layout.fragment_selfie_log, container, false);
        initWidgets(view);

        lsCompx = android.os.Build.MANUFACTURER;
        if (!lsCompx.toLowerCase().equalsIgnoreCase("huawei")) {

        } else {

        }

        psEmpLvl = mViewModel.getEmployeeLevel();

        lblNotice.setVisibility(View.GONE);
        btnBranch.setVisibility(View.VISIBLE);
        if (psEmpLvl.equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))) {
            SetupDialogForBranchList();
        }

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblUsername.setText(eEmployeeInfo.getUserName());
                lblPosition.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserBranchInfo().observe(getViewLifecycleOwner(), eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                poSelfie.setBranchCode(eBranchInfo.getBranchCd());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mViewModel.getAllEmployeeTimeLog().observe(getViewLifecycleOwner(), eLog_selfies -> {
            TimeLogAdapter logAdapter = new TimeLogAdapter(eLog_selfies, sTransNox -> GToast.CreateMessage(requireActivity(), "Feature not yet implemented", GToast.INFORMATION).show());
            LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(logAdapter);

        });

        btnBranch.setOnClickListener(v -> mViewModel.CheckBranchList(new VMSelfieLog.OnBranchCheckListener() {
            @Override
            public void OnCheck(List<EBranchInfo> area, List<EBranchInfo> all) {
                new DialogBranchSelection(requireActivity(), area, all).initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
                    @Override
                    public void OnSelect(String BranchCode, AlertDialog dialog) {
                        mViewModel.checkIfAlreadyLog(BranchCode, new VMSelfieLog.OnBranchSelectedCallback() {
                            @Override
                            public void OnLoad() {
                                poLoad.initDialog("Selfie Log", "Validating branch. Please wait...", false);
                                poLoad.show();
                            }

                            @Override
                            public void OnSuccess() {
                                poLoad.dismiss();
                                poSelfie.setBranchCode(BranchCode);
                                mViewModel.getBranchInfo(BranchCode).observe(getViewLifecycleOwner(), eBranchInfo -> {
                                    try{
                                        lblBranch.setText(eBranchInfo.getBranchNm());
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
                    }
                });
            }

            @Override
            public void OnFailed(String message) {

            }
        }));

        btnCamera.setOnClickListener(v -> {
            if(checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                poRequest.launch(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                });
            } else {
                initCamera();
            }
        });
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

        poLoad = new LoadDialog(requireActivity());
        poMessage = new MessageBox(requireActivity());
    }

    @SuppressLint("MissingPermission")
    private void initCamera(){
        try {
            if(checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                poRequest.launch(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION});
            } else {
                mViewModel.InitCameraLaunch(requireActivity(), new OnInitializeCameraCallback() {
                    @Override
                    public void OnInit() {
                        poLoad.initDialog("Selfie Log", "Initializing camera. Please wait...", false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess(Intent intent, String[] args) {
                        poLoad.dismiss();
                        poSelfie.setLocation(args[0]);
                        poSelfie.setFileName(args[1]);
                        poSelfie.setLatitude(args[2]);
                        poSelfie.setLongitude(args[3]);
                        poCamera.launch(intent);
                    }

                    @Override
                    public void OnFailed(String message, Intent intent, String[] args) {
                        poLoad.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Selfie Login");
                        poMessage.setMessage(message + "\n Proceed taking selfie log?");
                        poMessage.setPositiveButton("Continue", (view, dialog) -> {
                            dialog.dismiss();
                            poSelfie.setLocation(args[0]);
                            poSelfie.setFileName(args[1]);
                            poSelfie.setLatitude(args[2]);
                            poSelfie.setLongitude(args[3]);
                            poCamera.launch(intent);
                        });
                        poMessage.setNegativeButton("Cancel", (view1, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }
                });
            }
        } catch(RuntimeException e) {
            e.printStackTrace();
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
            Intent loIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            poSettings.launch(loIntent);
            dialog.dismiss();
            isDialogShown = false;
        });
        poMessage.show();
        isDialogShown = true;
    }

    public void ValidateCashCount(){
        mViewModel.ValidateCashCount(poLog.getBranchCd(), new VMSelfieLog.OnValidateCashCount() {
            @Override
            public void OnValidate() {
                poLoad.initDialog("Selfie Log", "Checking cash count entries. Please wait...", false);
                poLoad.show();
            }

            @Override
            public void OnProceed(String args) {
                poLoad.dismiss();
                Intent loIntent = new Intent(requireActivity(), Activity_CashCounter.class);
                loIntent.putExtra("BranchCd", poLog.getBranchCd());
                loIntent.putExtra("cancelable", false);
                requireActivity().startActivity(loIntent);
                requireActivity().finish();
            }

            @Override
            public void OnWarning(String message) {
                poLoad.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Selfie Login");
                poMessage.setMessage("A Cash count entry for current branch already exist on local device. Create another entry?");
                poMessage.setPositiveButton("Create", (view, dialog) -> {
                    Intent loIntent = new Intent(requireActivity(), Activity_CashCounter.class);
                    loIntent.putExtra("BranchCd", poLog.getBranchCd());
                    loIntent.putExtra("cancelable", false);
                    requireActivity().startActivity(loIntent);
                    requireActivity().finish();
                });
                poMessage.setNegativeButton("Exit", (view, dialog) -> requireActivity().finish());
                poMessage.show();
            }

            @Override
            public void OnFailed(String message) {
                poLoad.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Selfie Login");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                poMessage.show();
            }

            @Override
            public void OnUnauthorize(String message) {
                poLoad.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Selfie Log");
                poMessage.setMessage("Selfie log save.");
                poMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                poMessage.show();
            }
        });
    }

    private void SetupDialogForBranchList(){
        mViewModel.CheckBranchList(new VMSelfieLog.OnBranchCheckListener() {
            @Override
            public void OnCheck(List<EBranchInfo> area, List<EBranchInfo> all) {
                new DialogBranchSelection(requireActivity(), area, all).initDialog(true, new DialogBranchSelection.OnBranchSelectedCallback() {
                    @Override
                    public void OnSelect(String BranchCode, AlertDialog dialog) {
                        mViewModel.checkIfAlreadyLog(BranchCode, new VMSelfieLog.OnBranchSelectedCallback() {
                            @Override
                            public void OnLoad() {
                                poLoad.initDialog("Selfie Log", "Validating branch. Please wait...", false);
                                poLoad.show();
                            }

                            @Override
                            public void OnSuccess() {
                                poLoad.dismiss();
                                poSelfie.setBranchCode(BranchCode);
                                mViewModel.getBranchInfo(BranchCode).observe(getViewLifecycleOwner(), eBranchInfo -> {
                                    try{
                                        lblBranch.setText(eBranchInfo.getBranchNm());
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

                    }
                });
            }

            @Override
            public void OnFailed(String message) {

            }
        });
    }
}