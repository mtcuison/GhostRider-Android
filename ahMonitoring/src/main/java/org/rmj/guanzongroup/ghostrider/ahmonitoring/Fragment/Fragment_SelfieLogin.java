package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMSelfieLogin;

public class Fragment_SelfieLogin extends Fragment {
    private static final String TAG = Fragment_SelfieLogin.class.getSimpleName();

    private VMSelfieLogin mViewModel;

    private TextView lblUserNme, lblUserPstn;
    private ImageView lblImage;
    private MaterialButton btnCamera;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMSelfieLogin.class);

        mViewModel.getUserInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            lblUserNme.setText(eEmployeeInfo.getUserName());
            lblUserPstn.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}