package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_TransactionLog;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMOtherTransactions_Log;

public class Fragment_OtherTransactions_Log extends Fragment {
    private static final String TAG = Fragment_OtherTransactions_Log.class.getSimpleName();
    private static final String IMAGE_NAME = Activity_TransactionLog.imgNme;
    private VMOtherTransactions_Log mViewModel;
    private TextView txtAcctNo, txtClientName, txtClientAddress, txtRemarks;
    private ImageView ivTransImage;
    private View divDivider;

    public static Fragment_OtherTransactions_Log newInstance(String param1, String param2) {
        return new Fragment_OtherTransactions_Log();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__other_transactions_log, container, false);
        mViewModel = ViewModelProviders.of(this).get(VMOtherTransactions_Log.class);
        initWidgets(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtAcctNo.setText(Activity_TransactionLog.acctNox);
        txtClientName.setText(Activity_TransactionLog.fullNme);
        txtClientAddress.setText(Activity_TransactionLog.clientAddress);
        txtRemarks.setText(Activity_TransactionLog.remarks);
        //Image Location
        mViewModel.getImageLocation(Activity_TransactionLog.acctNox, IMAGE_NAME)
                .observe(getViewLifecycleOwner(), eImageInfo -> {
                    // TODO: Display Image
                    setPic(eImageInfo.getFileLoct());
                });
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