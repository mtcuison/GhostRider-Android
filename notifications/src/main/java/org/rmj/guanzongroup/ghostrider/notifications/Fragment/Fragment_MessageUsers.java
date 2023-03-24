/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Activity.Activity_UserMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.MessageUsersAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMMessageUsers;

import java.util.List;

public class Fragment_MessageUsers extends Fragment {

    private VMMessageUsers mViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewEmp;
    private MaterialButton btnSearch;
    private TextInputEditText txtEmployee;
    private RelativeLayout rlMessage;
    private LinearLayout lnEmpty;

    public static Fragment_MessageUsers newInstance() {
        return new Fragment_MessageUsers();
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMMessageUsers.class);
        View view = inflater.inflate(R.layout.fragment_message_users, container, false);
        setWidgets(view);

        mViewModel.GetUsersMessages().observe(requireActivity(), new Observer<List<DMessages.MessageUsers>>() {
            @Override
            public void onChanged(List<DMessages.MessageUsers> users) {
                try{
                    if(users == null){
                        lnEmpty.setVisibility(View.VISIBLE);
                        return;
                    }

                    if(users.size() == 0){
                        lnEmpty.setVisibility(View.VISIBLE);
                        return;
                    }

                    recyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    manager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new MessageUsersAdapter(users, (Title, Message, SenderID) -> {
                        Intent loIntent = new Intent(getActivity(), Activity_UserMessages.class);
                        loIntent.putExtra("sUserIDxx", SenderID);
                        startActivity(loIntent);
                    }));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void setWidgets(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
//        recyclerViewEmp = view.findViewById(R.id.recyclerview_employee);
//        btnSearch = view.findViewById(R.id.btnSearch);
//        txtEmployee = view.findViewById(R.id.txt_employeeSearch);
        lnEmpty = view.findViewById(R.id.ln_empty);
    }
}