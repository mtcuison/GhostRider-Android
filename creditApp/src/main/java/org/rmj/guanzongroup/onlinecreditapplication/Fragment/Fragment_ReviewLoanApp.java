package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.Data.GoCasBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMReviewLoanApp;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ReviewLoanApp extends Fragment {

    private VMReviewLoanApp mViewModel;

    private TextView lblClientNm;
    private RecyclerView recyclerView;
    private ImageView imgClient;
    private ImageButton btnCamera;

    private List<ReviewAppDetail> plDetail;

    public static Fragment_ReviewLoanApp newInstance() {
        return new Fragment_ReviewLoanApp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_loan_app, container, false);

        initWidgets(v);
        return v;
    }

    private void initWidgets(View v){
        lblClientNm = v.findViewById(R.id.lbl_clientNme);
        recyclerView = v.findViewById(R.id.recyclerview_applicationInfo);
        imgClient = v.findViewById(R.id.img_loanApplicant);
        btnCamera = v.findViewById(R.id.btn_camera);

        plDetail = new ArrayList<>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMReviewLoanApp.class);

        mViewModel.getApplicantInfo().observe(getViewLifecycleOwner(), new Observer<ECreditApplicantInfo>() {
            @Override
            public void onChanged(ECreditApplicantInfo eCreditApplicantInfo) {
                try{
                    mViewModel.setCreditAppInfo(eCreditApplicantInfo);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}