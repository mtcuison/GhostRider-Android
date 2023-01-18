package org.rmj.guanzongroup.ghostrider.Fragment;

import androidx.appcompat.app.AppCompatActivity;
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

import org.rmj.guanzongroup.ghostrider.R;
import org.rmj.guanzongroup.ghostrider.ViewModel.VMPanaloRewards;

public class Fragment_Rewards extends Fragment  {
    private RecyclerView recyclerView;
    private VMPanaloRewards mViewModel;


    public static Fragment_Rewards newInstance() {
        return new Fragment_Rewards();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_rewards, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMPanaloRewards.class);
        // TODO: Use the ViewModel
    }

}