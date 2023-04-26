package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Notifications.data.SampleData;
import org.rmj.g3appdriver.lib.PetManager.OnCheckEmployeeApplicationListener;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformanceMonitoring;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMHomeBH;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.AdapterAnnouncements;
import org.rmj.guanzongroup.petmanager.Adapter.EmployeeApplicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home_BH extends Fragment {
    private static final String TAG = Fragment_Home_AH.class.getSimpleName();
    private MaterialTextView lblFullNme;
    private MaterialTextView lblDept;
    private String lblBranchCD,lblBranchNM;
    private double latitude, longitude;
    private List<NewsEventsModel> newsList;
    private RecyclerView rvCompnyAnouncemnt, rvLeaveApp, rvBusTripApp;
    private MessageBox loMessage;
    private CircularProgressIndicator mcIndicator,spIndicator,joIndicator;
    private MaterialTextView mcGoalPerc,mcGoalFraction,spGoalPerc,spGoalFraction,joGoalPerc,joGoalFraction;
    private VMHomeBH mViewModel;
    private MaterialCardView btnPerformance;
//    private RecyclerView rvCompnyAnouncemnt;

    public static Fragment_Home_BH newInstance() {
        return new Fragment_Home_BH();
    }

    @SuppressLint("NonConstantResourceId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        mViewModel = new ViewModelProvider(this).get(VMHomeBH.class);
        View view = inflater.inflate(R.layout.fragment_home_bh, container, false);
        newsList = new ArrayList<>();
        loMessage = new MessageBox(getActivity());
        lblFullNme = view.findViewById(R.id.bhName);
        lblDept = view.findViewById(R.id.bhPosition);

        mcIndicator = view.findViewById(R.id.cpi_mc_sales);
        mcGoalPerc = view.findViewById(R.id.lblMCGoal);
        mcGoalFraction = view.findViewById(R.id.lblMCGoalPercent);

        spIndicator = view.findViewById(R.id.cpi_sp_sales);
        spGoalPerc = view.findViewById(R.id.lblSPGoal);
        spGoalFraction = view.findViewById(R.id.lblSPGoalPercent);

        joIndicator = view.findViewById(R.id.cpi_job_order);
        joGoalPerc = view.findViewById(R.id.lblJobOrder);
        joGoalFraction = view.findViewById(R.id.lblJobOrderPercent);

        rvCompnyAnouncemnt= view.findViewById(R.id.rvCompnyAnouncemnt);
        btnPerformance = view.findViewById(R.id.cb_performance);

        rvLeaveApp = view.findViewById(R.id.rvLeaveApp);
        rvBusTripApp = view.findViewById(R.id.rvBusTripApp);

        initUserInfo();
        initGoals();
        initButton();
        initCompanyNotice();
        return view;
    }

    private void initGoals(){
        mViewModel.GetCurrentMCSalesPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mc_goal) {
                try {
                    if(mc_goal == null){
                        return;
                    }
                    mcGoalPerc.setText(mc_goal);
                    if (mc_goal.contains("/")) {
                        String[] rat = mc_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            mcGoalFraction.setText("0%");
                            mcIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            mcGoalFraction.setText(String.valueOf(Math.round(ratio)) + "%");
                            mcIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sp_goal) {
                try {
                    if(sp_goal == null){
                        return;
                    }
                    spGoalPerc.setText(sp_goal);
                    if (sp_goal.contains("/")) {
                        String[] rat = sp_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            spGoalFraction.setText("0%");
                            spIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            spGoalFraction.setText(String.valueOf(Math.round(ratio)) + "%");
                            spIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String jo_goal) {
                try {
                    if(jo_goal == null){
                        return;
                    }
                    joGoalPerc.setText(jo_goal);
                    if (jo_goal.contains("/")) {
                        String[] rat = jo_goal.split("/");
                        if ((Double.parseDouble(rat[0]) == 0) || (Double.parseDouble(rat[1]) == 0)) {
                            joGoalFraction.setText("0%");
                            joIndicator.setProgress(0);
                        } else {
                            double ratio = Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                            joGoalFraction.setText(String.valueOf(Math.round(ratio)) + "%");
                            joIndicator.setProgress((int) (Math.round(ratio)));
                        }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void initUserInfo(){
        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                lblFullNme.setText(eEmployeeInfo.getUserName());
                lblDept.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblBranchCD = eEmployeeInfo.getBranchCD();
                lblBranchNM = eEmployeeInfo.getBranchNm();
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
        btnPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_BranchPerformanceMonitoring.class);
                loIntent.putExtra("brnCD", lblBranchCD);
                loIntent.putExtra("brnNM", lblBranchNM);
                Log.e("papaso ko na branch",lblBranchNM);
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMHomeBH.class);
        // TODO: Use the ViewModel
    }
    private void initCompanyNotice(){
        AdapterAnnouncements loAdapter = new AdapterAnnouncements(SampleData.GetAnnouncementList(), new AdapterAnnouncements.OnItemClickListener() {
            @Override
            public void OnClick(String args) {

            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        rvCompnyAnouncemnt.setLayoutManager(loManager);
        rvCompnyAnouncemnt.setAdapter(loAdapter);
    }
    private void initEmployeeApp(){
        mViewModel.CheckApplicationsForApproval(new OnCheckEmployeeApplicationListener() {
            @Override
            public void OnCheck() {
                Log.d(TAG, "Checking employee leave and business trip applications...");
            }

            @Override
            public void OnSuccess() {
                Log.d(TAG, "Leave and business trip applications checked!");
            }

            @Override
            public void OnFailed(String message) {
                Log.e(TAG, message);
            }
        });

        mViewModel.GetLeaveForApproval().observe(requireActivity(), new Observer<List<EEmployeeLeave>>() {
            @Override
            public void onChanged(List<EEmployeeLeave> app) {
                try{
                    if(app == null){
                        return;
                    }

                    if(app.size() == 0){
                        return;
                    }

                    EmployeeApplicationAdapter loAdapter = new EmployeeApplicationAdapter(app, false, new EmployeeApplicationAdapter.OnLeaveItemClickListener() {
                        @Override
                        public void OnClick(String TransNox) {

                        }
                    });

                    LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
                    loManager.setOrientation(RecyclerView.VERTICAL);
                    rvLeaveApp.setLayoutManager(loManager);
                    rvLeaveApp.setAdapter(loAdapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetOBForApproval().observe(requireActivity(), new Observer<List<EEmployeeBusinessTrip>>() {
            @Override
            public void onChanged(List<EEmployeeBusinessTrip> app) {
                try{
                    if(app == null){
                        return;
                    }

                    if(app.size() == 0){
                        return;
                    }

                    EmployeeApplicationAdapter loAdapter = new EmployeeApplicationAdapter(app, new EmployeeApplicationAdapter.OnOBItemClickListener() {
                        @Override
                        public void OnClick(String TransNox) {

                        }
                    });

                    LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
                    loManager.setOrientation(RecyclerView.VERTICAL);
                    rvBusTripApp.setLayoutManager(loManager);
                    rvBusTripApp.setAdapter(loAdapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}