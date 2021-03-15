package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_TransactionLog;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMPromiseToPay_Log;

public class Fragment_PromiseToPay_Log extends Fragment {
    private VMPromiseToPay_Log mViewModel;
    private TextView txtAcctNo, txtClientName, txtClientAddress;
    private TextView txtPTPDate, txtBranchName, txtRemarks;
    private ImageView ivTransImage;
    private LinearLayout lnBranchName;

    public Fragment_PromiseToPay_Log() { }

    public static Fragment_PromiseToPay_Log newInstance() {
        return new Fragment_PromiseToPay_Log();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promise_to_pay_log, container, false);
        initWidgets(v);
        mViewModel = ViewModelProviders.of(this).get(VMPromiseToPay_Log.class);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Do the coding here
        txtAcctNo.setText(Activity_TransactionLog.acctNox);
        txtClientName.setText(Activity_TransactionLog.fullNme);
        txtClientAddress.setText(Activity_TransactionLog.clientAddress);
        mViewModel.setParameters(Activity_TransactionLog.transNox,
                Activity_TransactionLog.acctNox,
                Activity_TransactionLog.remCodex);

        mViewModel.getImageLocation(Activity_TransactionLog.acctNox, Activity_TransactionLog.imgNme)
                .observe(getViewLifecycleOwner(), eImageInfo -> setPic(eImageInfo.getFileLoct()));

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