package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import static org.rmj.g3appdriver.etc.AppConstants.SETTINGS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Settings;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home_Engineering extends Fragment {

    private VMHomeEngineering mViewModel;
    private MaterialTextView lblFullNme,
            lblEmail,
            lblUserLvl,
            lblDept,
            lblAreaNme,
            lblSyncStat;
    private String photoPath;
    private double latitude, longitude;
    private List<NewsEventsModel> newsList;
    private NewsEventsAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOpening;
    private MessageBox loMessage;
    private ConstraintLayout cardMenu;
    private MaterialCardView lblSettings,lblLogout;
    private MaterialButton logout, settings;

    public static Fragment_Home_Engineering newInstance() {
        return new Fragment_Home_Engineering();
    }

    @SuppressLint("NonConstantResourceId")

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        mViewModel = new ViewModelProvider(this).get(VMHomeEngineering.class);
        View view = inflater.inflate(R.layout.fragment_home_engineering, container, false);
        newsList = new ArrayList<>();
        loMessage = new MessageBox(getActivity());
        adapter = new NewsEventsAdapter(getContext(), newsList) ;
        lblFullNme = view.findViewById(R.id.lbl_EngrNme);
        lblDept = view.findViewById(R.id.lbl_userDepartment);
        lblSyncStat = view.findViewById(R.id.lblDate);
        lblSettings = view.findViewById(R.id.cardSettings);
        lblLogout = view.findViewById(R.id.cardLogout);
        cardMenu = view.findViewById(R.id.cardmenu);
        logout = view.findViewById(R.id.btnLogout);
        settings = view.findViewById(R.id.btnSettings);
        initButton();
        initUserInfo();
        return view;
    }
    private void initUserInfo(){
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                //lblEmail.setText(eEmployeeInfo.getEmailAdd());
//                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
                mViewModel.setIntUserLvl(4);

            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void showDialog(){
        loMessage.initDialog();
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.setPositiveButton("Yes", (view, dialog) -> {
            dialog.dismiss();
            requireActivity().finish();
            new EmployeeMaster(requireActivity().getApplication()).LogoutUserSession();
            AppConfigPreference.getInstance(getActivity()).setIsAppFirstLaunch(false);
            startActivity(new Intent(getActivity(), Activity_SplashScreen.class));
        });
        loMessage.setTitle("GhostRider Session");
        loMessage.setMessage("Are you sure you want to end session/logout?");
        loMessage.show();
    }
    public void initButton(){

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_Settings.class);
                startActivityForResult(loIntent, SETTINGS);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHomeEngineering.class);
        // TODO: Use the ViewModel
    }

}