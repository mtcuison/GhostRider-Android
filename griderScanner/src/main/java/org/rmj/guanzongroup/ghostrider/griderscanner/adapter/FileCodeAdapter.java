package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.contentResolver;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.*;

public class FileCodeAdapter extends RecyclerView.Adapter<FileCodeAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);
        void OnActionButtonClick();
    }

    private final List<EFileCode> plCollection;
    private final List<EImageInfo> plImgInfo;
    private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private final OnItemClickListener mListener;
    private String TransNox;
    DialogImagePreview dialogPreview;
    private VMClientInfo mvModel;
    private Context aContext;

    private ECreditApplicationDocuments poDocsInfo;
    public FileCodeAdapter(Context mContext, VMClientInfo vm,List<EImageInfo> plImgInfo,List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo, String transNox,List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.documentsInfo = documentsInfo;
        this.plImgInfo = plImgInfo;
        this.TransNox = transNox;
        this.mvModel = vm;
        this.aContext = mContext;
        this.mListener = mListener;
    }
    public FileCodeAdapter(List<EFileCode> plCollection, List<EImageInfo> plImgInfo, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.plImgInfo = plImgInfo;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.list_item_file_to_scan, parent, false);
        dialogPreview = new DialogImagePreview(parent.getContext());
        poDocsInfo = new ECreditApplicationDocuments();
        return new FileCodeViewHolder(parent.getContext(),v, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FileCodeViewHolder holder, int position) {
        try {
            EFileCode collection = plCollection.get(position);
            holder.loPlan = collection;
            holder.lbl_fileCode.setText(collection.getBriefDsc());
            boolean isExist = false;
            String imgName = "", fileLoc ="";
            for (int i = 0; i < documentsInfo.size(); i++){

                if (documentsInfo.get(i).sFileCode.equalsIgnoreCase(collection.getFileCode()) &&
                        documentsInfo.get(i).sTransNox.equalsIgnoreCase(TransNox)){
                    holder.fileStat.setImageResource(drawable.ic_baseline_done_24);
                    holder.fileStat.setTag(R.drawable.ic_baseline_done_24);
                    Log.e("File Loc", String.valueOf(documentsInfo.get(i).sFileLoct));
                    holder.lbl_fileLoc.setText(documentsInfo.get(i).sFileLoct);
                    imgName = documentsInfo.get(i).sImageNme;
                    fileLoc = documentsInfo.get(i).sFileLoct;

                    isExist = true;
                }
                Log.e("File Loc", String.valueOf(documentsInfo.get(i).sFileLoct));
            }


            poDocsInfo.setEntryNox(collection.getEntryNox());
            poDocsInfo.setTransNox(TransNox);
            poDocsInfo.setFileCode(collection.getFileCode());
            poDocsInfo.setFileLoc(fileLoc);
            poDocsInfo.setImageNme(imgName);
//            mvModel.saveDocumentInfo(poDocsInfo);


        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return plCollection.size();
    }


    public static class FileCodeViewHolder extends RecyclerView.ViewHolder {

        public EFileCode loPlan;
        public TextView lbl_fileCode;
        public TextView lbl_fileLoc;
        public ImageView fileStat;
        private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
        public FileCodeViewHolder(Context mContext, @NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.documentsInfo = documentsInfo;
            lbl_fileCode = itemView.findViewById(id.lbl_fileCode);
            lbl_fileLoc = itemView.findViewById(id.lbl_fileLoc);
            fileStat = itemView.findViewById(id.tick_cross);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && fileStat.getTag() == null) {
                    listener.OnClick(position);
                }else {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                                contentResolver, Uri.fromFile(new File(lbl_fileLoc.getText().toString())));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DialogImagePreview loDialog = new DialogImagePreview(mContext, bitmap,lbl_fileCode.getText().toString());
                    loDialog.initDialog(new DialogImagePreview.OnDialogButtonClickListener() {
                        @Override
                        public void OnCancel(Dialog Dialog) {
                            Dialog.dismiss();
                        }
                    });
                    loDialog.show();
                }
            });


        }
    }


}