package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Notifications.data.SampleData;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_BranchPerformanceMonitoring;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.AdapterAnnouncements;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home_BH extends Fragment {

    private MaterialTextView lblFullNme,
            lblEmail,
            lblUserLvl,
            lblDept,
            lblAreaNme,
            lblSyncStat;
    private String photoPath, branchCd, branchNm;
    private double latitude, longitude;
    private List<NewsEventsModel> newsList;

    private MessageBox loMessage;
    private CircularProgressIndicator mcIndicator,spIndicator,joIndicator;
    private MaterialTextView mcGoalPerc,mcGoalFraction,spGoalPerc,spGoalFraction,joGoalPerc,joGoalFraction;
    private VMHomeBH mViewModel;
    private MaterialCardView btnPerformance;
    private RecyclerView rvCompnyAnouncemnt, rvLeaveApp, rvBusTripApp;

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
                //lblEmail.setText(eEmployeeInfo.getEmailAdd());
//                lblUserLvl.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                lblFullNme.setText(eEmployeeInfo.getUserName());
                branchCd = eEmployeeInfo.getBranchCD();
                branchNm = eEmployeeInfo.getBranchNm();
                lblDept.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
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
        btnPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_BranchPerformanceMonitoring.class);
                loIntent.putExtra("index","0");
                loIntent.putExtra("brnCD",branchCd);
                loIntent.putExtra("brnNM",branchNm);
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

}