package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSelfieLogin;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import static android.app.Activity.RESULT_OK;

public class Fragment_SelfieLogin extends Fragment {
    private static final String TAG = Fragment_SelfieLogin.class.getSimpleName();

    private VMSelfieLogin mViewModel;

    private TextView lblUserNme, lblUserPstn;
    private ImageView lblImage;
    private MaterialButton btnCamera;

    private EEmployeeInfo poUser;
    private EImageInfo poImage;

    private String photPath;

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

        poImage = new EImageInfo();
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

        btnCamera.setOnClickListener(view -> {
            ImageFileCreator loImage = new ImageFileCreator(getActivity(),"SelfieLogin","LOG","");
            loImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                this.photPath = photPath;
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
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                poImage.setMD5Hashx(WebFileServer.createMD5Hash(photPath));
                poImage.setCaptured(AppConstants.DATE_MODIFIED);
                mViewModel.insertImageInfo(poImage);
            }
        }
    }
}