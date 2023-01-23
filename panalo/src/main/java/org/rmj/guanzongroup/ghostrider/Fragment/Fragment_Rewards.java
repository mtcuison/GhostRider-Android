package org.rmj.guanzongroup.ghostrider.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.rmj.guanzongroup.ghostrider.Adapter.AdapterRewards;
import org.rmj.guanzongroup.ghostrider.Dialog.DialogPanaloRedeem;
import org.rmj.guanzongroup.ghostrider.Model.PanaloReward;
import org.rmj.guanzongroup.ghostrider.R;
import org.rmj.guanzongroup.ghostrider.ViewModel.VMPanaloRewards;

import java.util.List;
import java.util.zip.Inflater;

public class Fragment_Rewards extends Fragment  {
    private VMPanaloRewards mViewModel;
    private RecyclerView recyclerView;
    private TextView lblFirstNme,
            lblLastNme,
            lblMiddleNme;

    public static Fragment_Rewards newInstance() {
        return new Fragment_Rewards();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPanaloRewards.class);
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        initWidgets(view);
        return  view;
    }

    private void initWidgets(View v) {
        recyclerView = v.findViewById(R.id.recyclerView);
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);

        List<PanaloReward> loList = mViewModel.getList();
        AdapterRewards loAdapter = new AdapterRewards(loList, new AdapterRewards.OnClickListener() {
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
        recyclerView.setAdapter(loAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPanaloRewards.class);
        // TODO: Use the ViewModel
    }

}