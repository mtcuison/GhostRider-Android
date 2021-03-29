package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.LoanAppDetailReviewAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.Data.UploadCreditApp;
import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMReviewLoanApp;

import java.util.ArrayList;
import java.util.List;

public class Fragment_ReviewLoanApp extends Fragment implements UploadCreditApp.OnUploadLoanApplication {

    private VMReviewLoanApp mViewModel;
    private String TransNox;
    private TextView lblClientNm;
    private RecyclerView recyclerView;
    private ImageView imgClient;
    private ImageButton btnCamera;
    private MaterialButton btnSave, btnPrvs;

    private List<ReviewAppDetail> plDetail;
    private ECreditApplicantInfo poInfo;
    private ImageFileCreator poCamera;
    private EImageInfo poImage;

    public static Fragment_ReviewLoanApp newInstance() {
        return new Fragment_ReviewLoanApp();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_review_loan_app, container, false);
        TransNox = Activity_CreditApplication.getInstance().getTransNox();
        initWidgets(v);
        return v;
    }

    private void initWidgets(View v){
        lblClientNm = v.findViewById(R.id.lbl_clientNme);
        recyclerView = v.findViewById(R.id.recyclerview_applicationInfo);
        imgClient = v.findViewById(R.id.img_loanApplicant);
        btnCamera = v.findViewById(R.id.btn_camera);
        btnSave = v.findViewById(R.id.btn_loanAppSave);
        btnPrvs = v.findViewById(R.id.btn_creditAppPrvs);

        plDetail = new ArrayList<>();
        String lsImageNme = TransNox + "_" + "20_0029";
        poCamera = new ImageFileCreator(getActivity(), AppConstants.SUB_FOLDER_CREDIT_APP, lsImageNme);
        poImage = new EImageInfo();
        poImage.setImageNme(lsImageNme);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMReviewLoanApp.class);

        btnCamera.setOnClickListener(v -> poCamera.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
            poImage.setFileLoct(photPath);
            poImage.setFileCode("0029");
            poImage.setLatitude(String.valueOf(latitude));
            poImage.setLongitud(String.valueOf(longitude));
            poImage.setSourceNo(TransNox);
            poImage.setMD5Hashx(WebFileServer.createMD5Hash(photPath));
            getActivity().startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
        }));

        btnSave.setOnClickListener(v -> mViewModel.SaveCreditOnlineApplication(Fragment_ReviewLoanApp.this));

        btnPrvs.setOnClickListener(v -> {
            if(poInfo.getCmResidx() != null) {
                Activity_CreditApplication.getInstance().moveToPageNumber(17);
            } else {
                Activity_CreditApplication.getInstance().moveToPageNumber(16);
            }
        });
        mViewModel.setTransNox(TransNox);
        mViewModel.getApplicantInfo().observe(getViewLifecycleOwner(), eCreditApplicantInfo -> {
            try{
                poInfo = eCreditApplicantInfo;
                mViewModel.setCreditAppInfo(eCreditApplicantInfo);
                lblClientNm.setText(poInfo.getClientNm());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getAppDetail().observe(getViewLifecycleOwner(), reviewAppDetails -> {
            LoanAppDetailReviewAdapter loAdapter = new LoanAppDetailReviewAdapter(reviewAppDetails, new LoanAppDetailReviewAdapter.OnActionButtonClickListener() {
                @Override
                public void onInfoClickListener() {

                }
            });
            LinearLayoutManager loManager = new LinearLayoutManager(getActivity());
            loManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(loManager);
            recyclerView.setAdapter(loAdapter);
        });
    }

    @Override
    public void OnUpload() {

    }

    @Override
    public void OnSuccess(String ClientName) {

    }

    @Override
    public void OnFailed(String message) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.saveImageFile(poImage);
    }
}