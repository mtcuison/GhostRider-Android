package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.TimeLogAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSelfieLogin;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Fragment_SelfieLogin extends Fragment {
    private static final String TAG = Fragment_SelfieLogin.class.getSimpleName();

    private VMSelfieLogin mViewModel;

    private TextView lblUserNme, lblUserPstn;
    private ImageView lblImage;
    private MaterialButton btnCamera;
    private RecyclerView recyclerView;

    private EEmployeeInfo poUser;
    private EImageInfo poImage;
    private ELog_Selfie poLog;

    private LoadDialog poLoad;
    private MessageBox poMessage;

    private String photPath;

    private List<ELog_Selfie> currentDateLog;

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
        lblUserNme = view.findViewById(R.id.lbl_employeeName);
        lblUserPstn = view.findViewById(R.id.lbl_employeePosition);
        lblImage = view.findViewById(R.id.img_userSelfie);
        btnCamera = view.findViewById(R.id.btn_takeSelfie);
        recyclerView = view.findViewById(R.id.recyclerview_timeLog);

        poImage = new EImageInfo();
        poLog = new ELog_Selfie();
        poLoad = new LoadDialog(getActivity());
        poMessage = new MessageBox(getActivity());

        currentDateLog = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMSelfieLogin.class);

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                poUser = eEmployeeInfo;
                lblUserNme.setText(eEmployeeInfo.getUserName());
                lblUserPstn.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getCurrentLogTimeIfExist().observe(getViewLifecycleOwner(), eImageInfos -> {
            if(currentDateLog.size() > 0){
                //lblTimeLog.setText(currentDateLog.get(0).getLogTimex());
            }
            currentDateLog = eImageInfos;
        });

        mViewModel.getAllEmployeeTimeLog().observe(getViewLifecycleOwner(), eLog_selfies -> {
            TimeLogAdapter logAdapter = new TimeLogAdapter(eLog_selfies, sTransNox -> {
                GToast.CreateMessage(getActivity(), "Feature not yet implemented", GToast.INFORMATION).show();
            });
            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(logAdapter);
        });

        btnCamera.setOnClickListener(view -> {
            if (currentDateLog.size() > 0) {
                poMessage.initDialog();
                poMessage.setTitle("Selfie Login");
                poMessage.setMessage("You already login today.");
                poMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                poMessage.show();
            } else if (!GLocationManager.isLocationEnabled(Objects.requireNonNull(getActivity()))) {
                requestLocationEnabled();
            }else {
                initCamera();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                poImage.setMD5Hashx(WebFileServer.createMD5Hash(photPath));
                poImage.setCaptured(AppConstants.DATE_MODIFIED);
                mViewModel.loginTimeKeeper(poLog, poImage, new VMSelfieLogin.OnLoginTimekeeperListener() {
                    @Override
                    public void OnLogin() {
                        poLoad.initDialog("Selfie Login", "Uploading your login time. Please wait...", false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess(String args) {
                        poLoad.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Selfie Login");
                        poMessage.setMessage(args);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poLoad.dismiss();
                        poMessage.initDialog();
                        poMessage.setTitle("Selfie Login");
                        poMessage.setMessage(message);
                        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        poMessage.show();
                    }
                });
            }
        } else if(requestCode == GLocationManager.GLocationResCode){
            boolean isEnabled = GLocationManager.isLocationEnabled(Objects.requireNonNull(getActivity()));
            if(isEnabled){
                initCamera();
            } else {
                requestLocationEnabled();
            }
        }
    }

    private void initCamera(){
        ImageFileCreator loImage = new ImageFileCreator(getActivity(), "SelfieLogin", "LOG", "");
        loImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
            this.photPath = photPath;
            poLog.setEmployID(poUser.getClientID());
            poLog.setLogTimex(AppConstants.DATE_MODIFIED);
            poLog.setLatitude(String.valueOf(latitude));
            poLog.setLongitud(String.valueOf(longitude));
            poLog.setSendStat("0");

            poImage.setFileCode("0021");
            poImage.setSourceNo(poUser.getClientID());
            poImage.setDtlSrcNo(poUser.getUserIDxx());
            poImage.setSourceCD("LOGa");
            poImage.setImageNme(FileName);
            poImage.setFileLoct(photPath);
            poImage.setLatitude(String.valueOf(latitude));
            poImage.setLongitud(String.valueOf(longitude));
            startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
        });
    }

    private void requestLocationEnabled(){
        poMessage.initDialog();
        poMessage.setTitle("Selfie Login");
        poMessage.setMessage("Please enable your device service location.");
        poMessage.setNegativeButton("Cancel", (view12, dialog) -> dialog.dismiss());
        poMessage.setPositiveButton("Go to Settings", (view, dialog) -> {
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GLocationManager.GLocationResCode);
            dialog.dismiss();
        });
        poMessage.show();
    }
}