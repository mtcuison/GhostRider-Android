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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Inventory;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Monitoring;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.NewsEventsModel;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Home_AH extends Fragment {

    private VMHomeAH mViewModel;
    private MaterialTextView lblFullNme ,lblDept;
    private MaterialTextView mcGoalPerc,mcGoalFraction,spGoalPerc,spGoalFraction,joGoalPerc,joGoalFraction;
    private MaterialCardView CashCount,Inventory,btnPerformance;
    private List<NewsEventsModel> newsList;
    private CircularProgressIndicator mcIndicator,spIndicator,joIndicator;
    private NewsEventsAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewOpening;
    private MessageBox loMessage;

    public static Fragment_Home_AH newInstance() {
        return new Fragment_Home_AH();
    }

    @SuppressLint({"NonConstantResourceId", "MissingInflatedId"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMHomeAH.class);
        View view = inflater.inflate(R.layout.fragment_home_ah, container, false);


         newsList = new ArrayList<>();
          loMessage = new MessageBox(getActivity());
          adapter = new NewsEventsAdapter(getContext(), newsList) ;
          lblFullNme = view.findViewById(R.id.lblEmpNme);
          lblDept = view.findViewById(R.id.lblEmpPosition);
          CashCount = view.findViewById(R.id.cv_cashCount);
          Inventory = view.findViewById(R.id.cv_inventory);

          mcIndicator = view.findViewById(R.id.cpi_mc_sales);
          mcGoalPerc = view.findViewById(R.id.lblMCGoal);
          mcGoalFraction = view.findViewById(R.id.lblMCGoalPercent);

          spIndicator = view.findViewById(R.id.cpi_sp_sales);
          spGoalPerc = view.findViewById(R.id.lblSPGoal);
          spGoalFraction = view.findViewById(R.id.lblSPGoalPercent);

          joIndicator = view.findViewById(R.id.cpi_job_order);
          joGoalPerc = view.findViewById(R.id.lblJobOrderPercent);
          joGoalFraction = view.findViewById(R.id.lblJobOrder);

          btnPerformance = view.findViewById(R.id.cb_performance);

        initButton();
        initUserInfo();
        initGoals();
        return view;
    }

    public void initButton(){
        btnPerformance.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_Monitoring.class);
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }
        });
        CashCount.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_CashCounter.class);
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }

        });
        Inventory.setOnClickListener(new View.OnClickListener() {
            Intent loIntent;
            @Override
            public void onClick(View view) {
                loIntent = new Intent(getActivity(), Activity_Inventory.class);
                startActivity(loIntent);
                requireActivity().overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
            }

        });
    }
    private void initGoals(){
        mViewModel.GetCurrentMCSalesPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String mc_goal) {
                try {
                    mcGoalPerc.setText(mc_goal);
                    if (mc_goal.contains("/")){
                        String[] rat = mc_goal.split("/");
                        double ratio =Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                        mcGoalFraction.setText(String.valueOf(Math.round(ratio)) + "%");
                        mcIndicator.setProgress((int) (Math.round(ratio)));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String sp_goal) {
                try {
                    spGoalPerc.setText(sp_goal);
                    if (sp_goal.contains("/")){
                        String[] rat = sp_goal.split("/");
                        double ratio =Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                        spGoalFraction.setText(String.valueOf(Math.round(ratio)) + "%");
                        spIndicator.setProgress((int) (Math.round(ratio)));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewModel.GetJobOrderPerformance().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String jo_goal) {
                try {
                    joGoalFraction.setText(jo_goal);
                    if (jo_goal.contains("/")){
                        String[] rat = jo_goal.split("/");
                        double ratio =Double.parseDouble(rat[0]) / Double.parseDouble(rat[1]) * 100;
                        joGoalPerc.setText(String.valueOf(Math.round(ratio)) + "%");
                        joIndicator.setProgress((int) (Math.round(ratio)));
                    }

                }catch (Exception e) {
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
                lblDept.setText(DeptCode.parseUserLevel(eEmployeeInfo.getEmpLevID()));
                mViewModel.setIntUserLvl(4);

            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

}