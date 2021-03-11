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
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_TransactionLog;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMClientDetl_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround_Log;

public class Fragment_ClientDtl_Log extends Fragment {

    private VMClientDetl_Log mViewModel;
    private ImageView ivTransImage;
    private TextView txtAcctNo, txtClientName, txtClientAddress, txtFullname,
    txtAddress,
    txtGender,
    txtCivilStat,
    txtBDate,
    txtBPlace,
    txtTelNox,
    txtMobileNox,
    txtEmail,
    txtRemarks;
    private String[] civilStatus = DCP_Constants.CIVIL_STATUS;
    private String[] gender = {"Male", "Female", "LGBT"};

    public Fragment_ClientDtl_Log() { }

    public static Fragment_ClientDtl_Log newInstance() {
        return new Fragment_ClientDtl_Log();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_client_dtl_log, container, false);
        initWidgets(v);
        mViewModel = ViewModelProviders.of(this).get(VMClientDetl_Log.class);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtAcctNo.setText(Activity_TransactionLog.acctNox);
        txtClientName.setText(Activity_TransactionLog.fullNme);
        txtClientAddress.setText(Activity_TransactionLog.clientAddress);

        mViewModel.getImageLocation(Activity_TransactionLog.acctNox, Activity_TransactionLog.imgNme)
                .observe(getViewLifecycleOwner(), eImageInfo -> {
                    setPic(eImageInfo.getFileLoct());
                });

        mViewModel.getClientUpdateInfo(Activity_TransactionLog.acctNox).observe(getViewLifecycleOwner(), clientDetl -> {
            txtFullname.setText(clientDetl.getLastName() + ", "
                    + clientDetl .getFrstName() + ", "
                    + clientDetl.getMiddName() + " "
                    + clientDetl.getSuffixNm());

            // Set Address
            mViewModel.getBrgyTownProvinceInfo(clientDetl.getBarangay()).observe(getViewLifecycleOwner(), eTownInfo -> {
                txtAddress.setText(clientDetl.getHouseNox() + " " + clientDetl.getAddressx() + ", " + eTownInfo.sBrgyName + ", " + eTownInfo.sTownName +", " + eTownInfo.sProvName);
                txtBPlace.setText(eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });
            mViewModel.getTownProvinceInfo(clientDetl.getTownIDxx()).observe(getViewLifecycleOwner(), eTownInfo -> {
                txtBPlace.setText(eTownInfo.sTownName +", " + eTownInfo.sProvName);
            });

            txtGender.setText(gender[Integer.parseInt(clientDetl.getGenderxx())]);
            txtCivilStat.setText(civilStatus[Integer.parseInt(clientDetl.getCivlStat())]);
            txtBDate.setText(clientDetl.getBirthDte());
            txtBPlace.setText(clientDetl.getBirthPlc());
            txtTelNox.setText(clientDetl.getLandline());
            txtMobileNox.setText(clientDetl.getMobileNo());
            txtEmail.setText(clientDetl.getEmailAdd());
//            txtRemarks.setText(clientDetl.getRemakr);

        });

    }

    private void initWidgets(View v) {
        txtAcctNo = v.findViewById(R.id.txt_acctNo);
        txtClientName = v.findViewById(R.id.txt_clientName);
        txtClientAddress = v.findViewById(R.id.txt_client_address);
        txtFullname = v.findViewById(R.id.txt_fullname);
        txtAddress = v.findViewById(R.id.txt_address);
        txtGender = v.findViewById(R.id.txt_gender);
        txtCivilStat = v.findViewById(R.id.txt_civil_stat);
        txtBDate = v.findViewById(R.id.txt_bdate);
        txtBPlace = v.findViewById(R.id.txt_bplace);
        txtTelNox = v.findViewById(R.id.txt_telNox);
        txtMobileNox = v.findViewById(R.id.txt_mobileNox);
        txtEmail = v.findViewById(R.id.txt_email);
        txtRemarks = v.findViewById(R.id.txt_remarks);
        ivTransImage = v.findViewById(R.id.iv_transaction_img);
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