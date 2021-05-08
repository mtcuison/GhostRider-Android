/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_TransactionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog.DialogDisplayImage;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLogPromiseToPay;

public class Fragment_Log_PromiseToPay extends Fragment {
    private VMLogPromiseToPay mViewModel;
    private DialogDisplayImage poDialogx;
    private TextView txtAcctNo, txtClientName, txtClientAddress, txtCoord;
    private TextView txtPTPDate, txtBranchName, txtRemarks, txtTransTp;
    private ImageView ivTransImage;
    private LinearLayout lnBranchName;

    public Fragment_Log_PromiseToPay() { }

    public static Fragment_Log_PromiseToPay newInstance() {
        return new Fragment_Log_PromiseToPay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promise_to_pay_log, container, false);
        initWidgets(v);
        mViewModel = ViewModelProviders.of(this).get(VMLogPromiseToPay.class);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Do the coding here
        txtAcctNo.setText(Activity_TransactionDetail.acctNox);
        txtClientName.setText(Activity_TransactionDetail.fullNme);
        txtClientAddress.setText(Activity_TransactionDetail.clientAddress);
        txtCoord.setText(Activity_TransactionDetail.psLocate);
        txtTransTp.setText(Activity_TransactionDetail.psTransTp);
        mViewModel.setParameters(Activity_TransactionDetail.transNox,
                Activity_TransactionDetail.acctNox,
                Activity_TransactionDetail.remCodex);

        mViewModel.getImageLocation(Activity_TransactionDetail.acctNox, Activity_TransactionDetail.imgNme)
                .observe(getViewLifecycleOwner(), eImageInfo -> {
                    setPic(eImageInfo.getFileLoct());
                    ivTransImage.setOnClickListener(view -> {
                        poDialogx = new DialogDisplayImage(getActivity(),
                                Activity_TransactionDetail.acctNox, eImageInfo.getFileLoct());
                        poDialogx.initDialog(dialog -> {
                            dialog.dismiss();
                        });
                        poDialogx.show();
                    });
                });

        mViewModel.getPostedCollectionDetail().observe(getViewLifecycleOwner(), collectDetl -> {
            txtPTPDate.setText(collectDetl.getPromised());
            txtRemarks.setText(collectDetl.getRemarksx());
            if(!collectDetl.getBranchCd().trim().isEmpty()) {
                lnBranchName.setVisibility(View.VISIBLE);
                mViewModel.getBranchName(collectDetl.getBranchCd())
                        .observe(getViewLifecycleOwner(), branch -> txtBranchName.setText(branch));
            }
        });
    }

    private void initWidgets(View v) {
        // TODO: Initialize Widgets
        ivTransImage = v.findViewById(R.id.iv_transaction_img);
        txtAcctNo = v.findViewById(R.id.txt_acctNo);
        txtClientName = v.findViewById(R.id.txt_clientName);
        txtClientAddress = v.findViewById(R.id.txt_client_address);
        txtCoord = v.findViewById(R.id.lbl_coordinates);
        txtTransTp = v.findViewById(R.id.lbl_list_header);
        txtPTPDate = v.findViewById(R.id.txt_ptp_date);
        txtBranchName = v.findViewById(R.id.txt_branch_name);
        txtRemarks = v.findViewById(R.id.txt_remarks);
        lnBranchName = v.findViewById(R.id.ln_branch_name);
    }

    private void setPic(String photoPath) {
        // Get the dimensions of the View
        int targetW = ivTransImage.getWidth();
        int targetH = ivTransImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(photoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);

        Bitmap bOutput;
        float degrees = 90;//rotation degree
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);
        bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        ivTransImage.setImageBitmap(bOutput);
    }
}