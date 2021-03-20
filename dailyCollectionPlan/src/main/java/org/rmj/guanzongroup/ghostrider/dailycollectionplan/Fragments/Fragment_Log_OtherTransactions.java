package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLogOtherTransactions;

public class Fragment_Log_OtherTransactions extends Fragment {
    private static final String TAG = Fragment_Log_OtherTransactions.class.getSimpleName();
    private final String IMAGE_NAME = Activity_LogTransaction.imgNme;
    private VMLogOtherTransactions mViewModel;
    private TextView txtAcctNo, txtClientName, txtClientAddress, txtRemarks;
    private ImageView ivTransImage;
    private View divDivider;

    public static Fragment_Log_OtherTransactions newInstance(String param1, String param2) {
        return new Fragment_Log_OtherTransactions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__other_transactions_log, container, false);
        mViewModel = ViewModelProviders.of(this).get(VMLogOtherTransactions.class);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtAcctNo.setText(Activity_LogTransaction.acctNox);
        txtClientName.setText(Activity_LogTransaction.fullNme);
        txtClientAddress.setText(Activity_LogTransaction.clientAddress);
        txtRemarks.setText(Activity_LogTransaction.remarks);
        //Image Location
        if(IMAGE_NAME != null){
            mViewModel.getImageLocation(Activity_LogTransaction.acctNox, IMAGE_NAME)
                    .observe(getViewLifecycleOwner(), eImageInfo -> {
                        // TODO: Display Image
                        setPic(eImageInfo.getFileLoct());
                    });
        } else {
            ivTransImage.setVisibility(View.GONE);
            divDivider.setVisibility(View.INVISIBLE);
        }

    }

    private void initWidgets(View v) {
        ivTransImage = v.findViewById(R.id.iv_transaction_img);
        txtAcctNo = v.findViewById(R.id.txt_acctNo);
        txtClientName = v.findViewById(R.id.txt_clientName);
        txtClientAddress = v.findViewById(R.id.txt_client_address);
        txtRemarks = v.findViewById(R.id.tv_remarks);
        divDivider = v.findViewById(R.id.divider2);
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

//        Bitmap bOutput;
//        float degrees = 90;//rotation degree
//        Matrix matrix = new Matrix();
//        matrix.setRotate(degrees);
//        bOutput = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        ivTransImage.setImageBitmap(bitmap);
    }

}