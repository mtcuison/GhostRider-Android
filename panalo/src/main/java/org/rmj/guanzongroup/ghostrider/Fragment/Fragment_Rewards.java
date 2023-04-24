package org.rmj.guanzongroup.ghostrider.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Panalo.Obj.GPanalo;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.Adapter.AdapterRewards;
import org.rmj.guanzongroup.ghostrider.Dialog.DialogPanaloRedeem;
import org.rmj.guanzongroup.ghostrider.Model.PanaloReward;
import org.rmj.guanzongroup.ghostrider.R;
import org.rmj.guanzongroup.ghostrider.ViewModel.VMPanaloRewards;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Fragment_Rewards extends Fragment  {
    private VMPanaloRewards mViewModel;
    private MessageBox loMessage;
    private LinearLayout lnEarned, lnClaimed;
    private RecyclerView rvEarned, rvClaimed;
    private MaterialTextView lblNoRewards;
    private CircularProgressIndicator progress;

    public static Fragment_Rewards newInstance() {
        return new Fragment_Rewards();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPanaloRewards.class);
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        initWidgets(view);
        mViewModel.GetRewards(0, new VMPanaloRewards.OnRetrieveRewardsListener() {
            @Override
            public void OnLoad(String title, String message) {
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void OnSuccess(List<PanaloRewards> args) {
                progress.setVisibility(View.GONE);
                if (args.size() == 0){
                    lblNoRewards.setVisibility(View.VISIBLE);
                    return;
                }

                initRewardsClaimed(args);
            }

            @Override
            public void OnFailed(String message) {
                progress.setVisibility(View.GONE);
                lblNoRewards.setVisibility(View.VISIBLE);
            }
        });
        return  view;
    }

    private void initWidgets(View v) {
        lnEarned = v.findViewById(R.id.linearEarned);
        lnClaimed = v.findViewById(R.id.linearClaimed);
        rvEarned = v.findViewById(R.id.rvEarned);
        rvClaimed = v.findViewById(R.id.rvClaimed);
        lblNoRewards = v.findViewById(R.id.lblNoRewards);
        progress = v.findViewById(R.id.progress_circular);
    }

    private void initRewardsEarned(List<PanaloRewards> args){
        AdapterRewards loAdapter = new AdapterRewards(args, new AdapterRewards.OnClickListener() {
            @Override
            public void OnClick(String args) {
                //to display dialog here
                DialogPanaloRedeem dialogPanaloRedeem = new DialogPanaloRedeem(getActivity());
                dialogPanaloRedeem.show();
                Toast.makeText(requireActivity(), args, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnUseButtonClick(String args) {
                //to display dialog here
                DialogPanaloRedeem dialogPanaloRedeem = new DialogPanaloRedeem(getActivity());
                dialogPanaloRedeem.show();
                Toast.makeText(requireActivity(), args, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        rvEarned.setLayoutManager(loManager);
        rvEarned.setAdapter(loAdapter);
        lnEarned.setVisibility(View.VISIBLE);
    }

    private void initRewardsClaimed(List<PanaloRewards> args){
        AdapterRewards loAdapter = new AdapterRewards(args, new AdapterRewards.OnClickListener() {
            @Override
            public void OnClick(String args) {
                //to display dialog here
                DialogPanaloRedeem dialogPanaloRedeem = new DialogPanaloRedeem(getActivity());
                dialogPanaloRedeem.show();
                Toast.makeText(requireActivity(), args, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void OnUseButtonClick(String args) {
                //to display dialog here
                DialogPanaloRedeem dialogPanaloRedeem = new DialogPanaloRedeem(getActivity());
                dialogPanaloRedeem.show();
            }
        });
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        rvClaimed.setLayoutManager(loManager);
        rvClaimed.setAdapter(loAdapter);
        lnClaimed.setVisibility(View.VISIBLE);
    }
}