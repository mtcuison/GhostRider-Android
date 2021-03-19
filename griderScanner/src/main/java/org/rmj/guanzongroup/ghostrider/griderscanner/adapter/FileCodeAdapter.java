package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.dialog.DialogImagePreview;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import static org.rmj.guanzongroup.ghostrider.griderscanner.ClientInfo.contentResolver;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.*;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.drawable.ic_baseline_add_24;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.drawable.ic_baseline_done_24;

public class FileCodeAdapter extends RecyclerView.Adapter<FileCodeAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);
        void OnActionButtonClick();
    }

    private final List<EFileCode> plCollection;
    private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private final OnItemClickListener mListener;
    private String TransNox;
    DialogImagePreview dialogPreview;
    private Context aContext;
    public FileCodeAdapter(Context mContext,String tansNox,List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo,List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.documentsInfo = documentsInfo;
        this.TransNox = tansNox;
        this.aContext = mContext;
        this.mListener = mListener;
    }
    public FileCodeAdapter(List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
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
            EFileCode collection = plCollection.get(position);
            holder.loPlan = collection;
            holder.lbl_fileCode.setText(collection.getBriefDsc());

            Log.e("File Loc", String.valueOf(documentsInfo.size()));
            for (int i = 0; i <  documentsInfo.size(); i++){
                if (documentsInfo.get(i).sFileCode.equalsIgnoreCase(collection.getFileCode()) &&
                        documentsInfo.get(i).nEntryNox == collection.getEntryNox() &&
                        documentsInfo.get(i).sTransNox.equalsIgnoreCase(TransNox) &&
                        documentsInfo.get(i).sImageNme != null ){
                    holder.fileStat.setImageResource(drawable.ic_baseline_done_24);
                    holder.fileStat.setTag(R.drawable.ic_baseline_done_24);

                    Log.e("Position EntryNox", String.valueOf(collection.getEntryNox()) + " EntryNox " + documentsInfo.get(i).nEntryNox);
                    Log.e("Position Filecode", String.valueOf(collection.getFileCode()) + " Filecode " + documentsInfo.get(i).sFileCode);
                    holder.lbl_fileLoc.setText(documentsInfo.get(i).sFileLoc);
                }
            }



        }catch (NullPointerException e){

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