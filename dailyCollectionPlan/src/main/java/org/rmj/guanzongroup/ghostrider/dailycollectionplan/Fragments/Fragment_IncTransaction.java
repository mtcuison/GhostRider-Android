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

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
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
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMIncompleteTransaction;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Fragment_IncTransaction extends Fragment {
    private static final String TAG = Fragment_IncTransaction.class.getSimpleName();
    private TextView lblFullNme, lblAccount, lblTransact;
    private TextInputEditText txtRemarks;
    private MaterialButton btnPost;
    private EImageInfo poImageInfo;
    private ImageFileCreator poImage;

    private String TransNox;
    private int EntryNox;
    private String AccntNox;
    private String Remarksx;

    private String psPhotox;

    private VMIncompleteTransaction mViewModel;

    private MessageBox loMessage;

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
        loMessage = new MessageBox(getActivity());

        poImageInfo = new EImageInfo();
        lblFullNme = v.findViewById(R.id.lbl_customerName);
        lblAccount = v.findViewById(R.id.lbl_AccountNo);
        lblTransact = v.findViewById(R.id.lbl_transaction);

        txtRemarks = v.findViewById(R.id.txt_dcpRemarks);

        btnPost = v.findViewById(R.id.btn_dcpConfirm);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMIncompleteTransaction.class);

        TransNox = Activity_Transaction.getInstance().getTransNox();
        EntryNox = Activity_Transaction.getInstance().getEntryNox();
        AccntNox = Activity_Transaction.getInstance().getAccntNox();
        Remarksx = Activity_Transaction.getInstance().getRemarksCode();

        poImage = new ImageFileCreator(getActivity(), AppConstants.SUB_FOLDER_DCP, TransNox);

        mViewModel.setParameter(TransNox, EntryNox, Remarksx);
        mViewModel.getCollectionDetail().observe(getViewLifecycleOwner(), collectionDetail -> {
            try {
                mViewModel.setAccountNo(collectionDetail.getAcctNmbr());
                lblFullNme.setText(collectionDetail.getFullName());
                lblAccount.setText(collectionDetail.getAcctNmbr());
                lblTransact.setText(Activity_Transaction.getInstance().getRemarksCode());
                mViewModel.setCollectioNDetail(collectionDetail);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnPost.setOnClickListener(v -> {
            if(!Objects.requireNonNull(txtRemarks.getText()).toString().trim().isEmpty()) {
                mViewModel.setRemarks(txtRemarks.getText().toString());
                loMessage.initDialog();
                loMessage.setTitle(Remarksx);
                if (Remarksx.equalsIgnoreCase("Not Visited")) {
                    loMessage.setMessage("Please take a selfie on your current place if customer is not visited.");
                } else {
                    loMessage.setMessage("Please take a selfie in customer's place in order to confirm transaction.");
                }
                loMessage.setPositiveButton("Okay", (view, dialog) -> {
                    dialog.dismiss();
                    poImage.CreateFile((openCamera, camUsage, photPath, FileName, latitude, longitude) -> {
                        psPhotox = photPath;
                        poImageInfo.setSourceNo(TransNox);
                        poImageInfo.setSourceCD("DCPa");
                        poImageInfo.setImageNme(FileName);
                        poImageInfo.setFileLoct(photPath);
                        poImageInfo.setFileCode("0020");
                        poImageInfo.setLatitude(String.valueOf(latitude));
                        poImageInfo.setLongitud(String.valueOf(longitude));
                        mViewModel.setImagePath(photPath);
                        startActivityForResult(openCamera, ImageFileCreator.GCAMERA);
                    });
                });
                loMessage.setNegativeButton("Cancel", (view, dialog) -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                loMessage.show();
            } else {
                GToast.CreateMessage(getActivity(), "Please enter remarks", GToast.ERROR).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                if(!Remarksx.equalsIgnoreCase("")) {
                    try {
                        poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(psPhotox));
                        mViewModel.saveImageInfo(poImageInfo);
                        mViewModel.updateCollectionDetail();
                        loMessage.initDialog();
                        loMessage.setTitle(Remarksx);
                        loMessage.setMessage("Transaction has been save!");
                        loMessage.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                            requireActivity().finish();
                        });
                    } catch(NullPointerException e) {
                        e.printStackTrace();
                    }

                } else {
                    loMessage.initDialog();
                    loMessage.setTitle(Remarksx);
                    loMessage.setMessage("Transaction has been save!");
                    loMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        requireActivity().finish();
                    });
                    loMessage.setNegativeButton("Retry", (view, dialog) -> {
                        poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(psPhotox));
                        mViewModel.saveImageInfo(poImageInfo);
                        mViewModel.updateCollectionDetail();
                    });
                }
                loMessage.show();
            } else {
                requireActivity().finish();
            }
        }
    }
}