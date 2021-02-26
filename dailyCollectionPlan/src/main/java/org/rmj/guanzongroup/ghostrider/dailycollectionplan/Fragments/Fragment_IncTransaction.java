package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMIncompleteTransaction;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import static android.app.Activity.RESULT_OK;

public class Fragment_IncTransaction extends Fragment {
    private static final String TAG = Fragment_IncTransaction.class.getSimpleName();
    private TextView lblFullNme, lblAccount, lblTransact;
    private MaterialButton btnPost;
    private EImageInfo poImageInfo;
    private ImageFileCreator poImage;

    private VMIncompleteTransaction mViewModel;

    public static Fragment_IncTransaction newInstance() {
        return new Fragment_IncTransaction();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inc_transaction, container, false);

        initWidgets(view);

        return view;
    }

    private void initWidgets(View v){
        poImage = new ImageFileCreator(getActivity(), ImageFileCreator.FILE_CODE.DCP);
        poImageInfo = new EImageInfo();
        lblFullNme = v.findViewById(R.id.lbl_customerName);
        lblAccount = v.findViewById(R.id.lbl_AccountNo);
        lblTransact = v.findViewById(R.id.lbl_transaction);

        btnPost = v.findViewById(R.id.btn_postTransaction);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMIncompleteTransaction.class);

        String TransNox = Activity_Transaction.getInstance().getTransNox();
        String EntryNox = Activity_Transaction.getInstance().getEntryNox();
        mViewModel.setParameter(TransNox, EntryNox);
        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                lblFullNme.setText(collectionDetail.getFullName());
                lblAccount.setText(collectionDetail.getAcctNmbr());
                lblTransact.setText(Activity_Transaction.getInstance().getRemarksCode());
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        poImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
            poImageInfo.setSourceNo(TransNox);
            poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(photPath));
            poImageInfo.setSourceCD("DCPa");
            poImageInfo.setDtlSrcNo("");
            poImageInfo.setImageNme(FileName);
            poImageInfo.setFileCode("UNKN");
            poImageInfo.setLatitude(String.valueOf(latitude));
            poImageInfo.setLongitud(String.valueOf(longitude));
            startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                mViewModel.saveImageInfo(poImageInfo);
                Log.e(TAG, "Image Info Save");
            } else {
                getActivity().finish();
            }
        }
    }
}