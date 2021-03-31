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
import org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo;
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

    private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private final OnItemClickListener mListener;
    DialogImagePreview dialogPreview;
    private VMClientInfo mvModel;
    private Context aContext;


    public FileCodeAdapter(Context context,List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo, OnItemClickListener mListener) {
        this.documentsInfo = documentsInfo;
        this.aContext = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.list_item_file_to_scan, parent, false);
        dialogPreview = new DialogImagePreview(parent.getContext());
        return new FileCodeViewHolder(parent.getContext(),v, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FileCodeViewHolder holder, int position) {
        try {
            DCreditApplicationDocuments.ApplicationDocument document = documentsInfo.get(position);

            holder.lbl_fileDsc.setText(document.sBriefDsc);
            holder.lbl_fileLoc.setText(document.sFileLoc);
            if (document.sImageNme != null){
                holder.fileStat.setImageResource(drawable.ic_baseline_done_24);
                holder.fileStat.setTag(R.drawable.ic_baseline_done_24);
            }
            if (document.sSendStat != null){
                holder.imgDB.setVisibility(View.VISIBLE);
            }
            Log.e("FileCodeAdapter", "sSendStat = " + document.sSendStat + " " + position);
        }catch (NullPointerException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return documentsInfo.size();
    }


    public static class FileCodeViewHolder extends RecyclerView.ViewHolder {

        public EFileCode loPlan;
        public TextView lbl_fileDsc;
        public TextView lbl_fileLoc;
        public ImageView fileStat;
        public ImageView imgDB;
        private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
        public FileCodeViewHolder(Context mContext, @NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            lbl_fileDsc = itemView.findViewById(id.lbl_fileCode);
            lbl_fileLoc = itemView.findViewById(id.lbl_fileLoc);
            fileStat = itemView.findViewById(id.tick_cross);
            imgDB = itemView.findViewById(R.id.imgDB);

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
                    DialogImagePreview loDialog = new DialogImagePreview(mContext, bitmap,lbl_fileDsc.getText().toString());
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