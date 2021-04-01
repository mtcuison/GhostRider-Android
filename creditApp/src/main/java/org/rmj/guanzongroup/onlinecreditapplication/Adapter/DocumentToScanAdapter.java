package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

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
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_DocumentToScan.contentResolver;

public class DocumentToScanAdapter extends RecyclerView.Adapter<DocumentToScanAdapter.FileCodeViewHolder> {


    public interface OnItemClickListener {
        void OnClick(int position);
        void OnActionButtonClick();
    }

    private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private final DocumentToScanAdapter.OnItemClickListener mListener;
    DialogImagePreview dialogPreview;;
    private Context aContext;


    public DocumentToScanAdapter(Context context,List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo, DocumentToScanAdapter.OnItemClickListener mListener) {
        this.documentsInfo = documentsInfo;
        this.aContext = context;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DocumentToScanAdapter.FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_document_to_scan, parent, false);
        dialogPreview = new DialogImagePreview(parent.getContext());
        return new DocumentToScanAdapter.FileCodeViewHolder(parent.getContext(),v, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DocumentToScanAdapter.FileCodeViewHolder holder, int position) {
        try {
            DCreditApplicationDocuments.ApplicationDocument document = documentsInfo.get(position);

            holder.lbl_fileDsc.setText(document.sBriefDsc);
            holder.lbl_fileLoc.setText(document.sFileLoc);
            if (document.sImageNme != null){
                holder.fileStat.setImageResource(R.drawable.ic_baseline_done_24);
                holder.fileStat.setTag(R.drawable.ic_baseline_done_24);
                Log.e("Adapter", document.sFileLoc);
            }

            if (document.sSendStat != null){
                holder.imgDB.setVisibility(View.VISIBLE);
            }
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
        public FileCodeViewHolder(Context mContext, @NonNull View itemView, DocumentToScanAdapter.OnItemClickListener listener) {
            super(itemView);
            lbl_fileDsc = itemView.findViewById(R.id.lbl_fileCode);
            lbl_fileLoc = itemView.findViewById(R.id.lbl_fileLoc);
            fileStat = itemView.findViewById(R.id.tick_cross);
            imgDB = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.imgDB);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && fileStat.getTag() == null) {
                    listener.OnClick(position);
                }else {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(
                                contentResolver, Uri.fromFile(new File(lbl_fileLoc.getText().toString())));
                        DialogImagePreview loDialog = new DialogImagePreview(mContext, bitmap,lbl_fileDsc.getText().toString());
                        loDialog.initDialog(new DialogImagePreview.OnDialogButtonClickListener() {
                            @Override
                            public void OnCancel(Dialog Dialog) {
                                Dialog.dismiss();
                            }
                        });
                        loDialog.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });


        }
    }


}
