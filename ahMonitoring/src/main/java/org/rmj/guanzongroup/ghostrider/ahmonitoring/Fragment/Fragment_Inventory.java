package org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Inventory;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.ItemAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMInventory;

public class Fragment_Inventory extends Fragment {

    private VMInventory mViewModel;

    private RecyclerView recyclerView;

    public static Fragment_Inventory newInstance() {
        return new Fragment_Inventory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);

        recyclerView = v.findViewById(R.id.recyclerview_inventory);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMInventory.class);
        mViewModel.getRandomItemList().observe(getViewLifecycleOwner(), randomItems -> {
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new ItemAdapter(randomItems, (TransNox, ItemCode, Description) -> {
                Intent loIntent = new Intent(getActivity(), Activity_Inventory.class);
                loIntent.putExtra("transno", TransNox);
                loIntent.putExtra("code", ItemCode);
                loIntent.putExtra("desc", Description);
                startActivity(loIntent);
            }));
        });
    }

}