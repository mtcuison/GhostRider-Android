/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.samsungKnox
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.samsungknox.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.samsungknox.Activity_Knox;
import org.rmj.guanzongroup.ghostrider.samsungknox.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Knox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Knox extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Knox() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Knox.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Knox newInstance(String param1, String param2) {
        Fragment_Knox fragment = new Fragment_Knox();
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
        View v = inflater.inflate(R.layout.fragment_knox, container, false);
        CardView btnUpload = v.findViewById(R.id.btn_knoxUpload);
        CardView btnActivate = v.findViewById(R.id.btn_knoxActivate);
        CardView btnGetPin = v.findViewById(R.id.btn_knoxGetPin);
        CardView btnOffline = v.findViewById(R.id.btn_knoxOfflinePIN);
        CardView btnGetStats = v.findViewById(R.id.btn_knoxCheckStatus);
        CardView btnUnlock = v.findViewById(R.id.btn_knoxUnlock);

        btnActivate.setOnClickListener(new OnButtonClickListener());
        btnGetPin.setOnClickListener(new OnButtonClickListener());
        btnOffline.setOnClickListener(new OnButtonClickListener());
        btnGetStats.setOnClickListener(new OnButtonClickListener());
        btnUnlock.setOnClickListener(new OnButtonClickListener());
        return v;
    }

    private class OnButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent loIntentx = new Intent(getActivity(), Activity_Knox.class);
            int id = view.getId();
            if (id == R.id.btn_knoxActivate) {
                loIntentx.putExtra("knox", 1);
            }
            if(id == R.id.btn_knoxUnlock){
                loIntentx.putExtra("knox", 2);
            }
            if(id == R.id.btn_knoxGetPin){
                loIntentx.putExtra("knox", 3);
            }
            if(id == R.id.btn_knoxOfflinePIN){
                loIntentx.putExtra("knox", 4);
            }
            if(id == R.id.btn_knoxCheckStatus){
                loIntentx.putExtra("knox", 5);
            }
            startActivity(loIntentx);
        }
    }
}