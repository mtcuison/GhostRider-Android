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

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
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

        poImage = new ImageFileCreator(getActivity(), DCP_Constants.FOLDER_NAME, ImageFileCreator.FILE_CODE.DCP, AccntNox);
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

        TransNox = Activity_Transaction.getInstance().getTransNox();
        EntryNox = Activity_Transaction.getInstance().getEntryNox();
        AccntNox = Activity_Transaction.getInstance().getAccntNox();
        Remarksx = Activity_Transaction.getInstance().getRemarksCode();

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

        loMessage.initDialog();
        loMessage.setTitle(Remarksx);
        loMessage.setMessage("Please take a selfie in customer's place in order to confirm transaction. \n" +
                "\n" +
                "NOTE: Take a selfie on your current place if customer is not visited");
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
            Objects.requireNonNull(getActivity()).finish();
        });
        loMessage.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageFileCreator.GCAMERA){
            if(resultCode == RESULT_OK){
                if(!Remarksx.equalsIgnoreCase("")) {
                    poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(psPhotox));
                    mViewModel.saveImageInfo(poImageInfo);
                    mViewModel.updateCollectionDetail(DCP_Constants.getRemarksCode(Remarksx));
                    loMessage.initDialog();
                    loMessage.setTitle(Remarksx);
                    loMessage.setMessage("Transaction has been save!");
                    loMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).finish();
                    });

                } else {
                    loMessage.initDialog();
                    loMessage.setTitle(Remarksx);
                    loMessage.setMessage("Transaction has been save!");
                    loMessage.setPositiveButton("Okay", (view, dialog) -> {
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).finish();
                    });
                    loMessage.setNegativeButton("Retry", (view, dialog) -> {
                        poImageInfo.setMD5Hashx(WebFileServer.createMD5Hash(psPhotox));
                        mViewModel.saveImageInfo(poImageInfo);
                        mViewModel.updateCollectionDetail(DCP_Constants.getRemarksCode(Remarksx));
                    });
                }
                loMessage.show();
            } else {
                Objects.requireNonNull(getActivity()).finish();
            }
        }
    }
}