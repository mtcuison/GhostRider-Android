/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalCode;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalSelection;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ApprovalMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ApprovalMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_ApprovalMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ApprovalMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ApprovalMenu newInstance(String param1, String param2) {
        Fragment_ApprovalMenu fragment = new Fragment_ApprovalMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_approval_menu, container, false);

        MaterialButton btnDay = v.findViewById(R.id.btn_dayToDay);
        MaterialButton btnLog = v.findViewById(R.id.btn_manualLog);
        MaterialButton btnRef = v.findViewById(R.id.btn_byReference);
        MaterialButton btnNme = v.findViewById(R.id.btn_byName);
        btnDay.setOnClickListener(new OnButtonClickListener());
        btnLog.setOnClickListener(new OnButtonClickListener());
        btnRef.setOnClickListener(new OnButtonClickListener());
        btnNme.setOnClickListener(new OnButtonClickListener());
        return v;
    }

    private class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btn_dayToDay){
                Toast.makeText(getActivity(), "Under develop", Toast.LENGTH_SHORT).show();
            }
            if(view.getId() == R.id.btn_manualLog){
                Intent intent = new Intent(getActivity(), Activity_ApprovalCode.class);
                intent.putExtra("sysCode", "2");
                startActivity(intent);
            }
            if(view.getId() == R.id.btn_byReference){
                Intent intent = new Intent(getActivity(), Activity_ApprovalSelection.class);
                intent.putExtra("syscode", "1");
                startActivity(intent);
            }
            if(view.getId() == R.id.btn_byName){
                Intent intent = new Intent(getActivity(), Activity_ApprovalSelection.class);
                intent.putExtra("syscode", "2");
                startActivity(intent);
            }
        }
    }
}