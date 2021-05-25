/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/25/21 1:10 PM
 * project file last modified : 5/25/21 1:10 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CISpecialInfoAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.CreditEvaluationHistoryInfoAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCISpecialInfo;

public class Fragment_CISpecialInfo extends Fragment {
    private static final String TAG = Fragment_CISpecialInfo.class.getSimpleName();
    private VMCISpecialInfo mViewModel;
    private LinearLayoutManager poLayout;
    private CISpecialInfoAdapter poAdapter;
    private RecyclerView recyclerView;
    private TextView sTransNox, sCompnyNm, dTransact, sModelNm, nTerm, nMobile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ci_special_info, container, false);
        initWidgets(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initClientInfo();
        mViewModel = ViewModelProviders.of(Fragment_CISpecialInfo.this)
                .get(VMCISpecialInfo.class);

        // TODO: Display Dynamic CI / Special CI
        //try {
        //   poAdapter = new CISpecialInfoAdapter();
        //   poLayout.setOrientation(RecyclerView.VERTICAL);
        //  recyclerView.setLayoutManager(poLayout);
        //  recyclerView.setAdapter(poAdapter);
        //} catch(Exception e) {
        //   e.printStackTrace();
        //}

    }

    private void initWidgets(View v) {
        poLayout = new LinearLayoutManager(getActivity());
        recyclerView = v.findViewById(R.id.rv_ci_special);

        sCompnyNm = v.findViewById(R.id.lbl_ci_applicantName);
        dTransact = v.findViewById(R.id.lbl_ci_applicationDate);
        sModelNm = v.findViewById(R.id.lbl_ci_modelName);
        nTerm = v.findViewById(R.id.lbl_ci_accntTerm);
        nMobile = v.findViewById(R.id.lbl_ci_mobileNo);
        sTransNox = v.findViewById(R.id.lbl_ci_transNox);
    }

    private void initClientInfo(){
        sTransNox.setText("Transaction No.: " + Activity_CIApplication.getInstance().getTransNox());
        sCompnyNm.setText(Activity_CIApplication.getInstance().getsCompnyNm());
        dTransact.setText(Activity_CIApplication.getInstance().getdTransact());
        sModelNm.setText(Activity_CIApplication.getInstance().getsModelNm());
        nTerm.setText(Activity_CIApplication.getInstance().getnTerm() + " Month/s");
        nMobile.setText(Activity_CIApplication.getInstance().getnMobile());
    }
}
