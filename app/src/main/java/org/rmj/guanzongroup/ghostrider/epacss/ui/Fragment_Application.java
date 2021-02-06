package org.rmj.guanzongroup.ghostrider.epacss.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.epacss.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Application#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Application extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Application() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Application.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Application newInstance(String param1, String param2) {
        Fragment_Application fragment = new Fragment_Application();
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
        View v =  inflater.inflate(R.layout.fragment_application, container, false);

        MaterialButton btnLeaveApp = v.findViewById(R.id.btn_applyLeave);
        MaterialButton btnObApp = v.findViewById(R.id.btn_applyOB);

        Intent loIntent = new Intent(getActivity(), Activity_Application.class);

        btnLeaveApp.setOnClickListener(view -> {
            loIntent.putExtra("app", AppConstants.INTENT_LEAVE_APPLICATION);
            startActivity(loIntent);
        });

        btnObApp.setOnClickListener(view -> {
            loIntent.putExtra("app", AppConstants.INTENT_OB_APPLICATION);
            startActivity(loIntent);
        });
        return v;
    }
}