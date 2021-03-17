package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import static org.rmj.guanzongroup.ghostrider.griderscanner.R.*;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.drawable.ic_baseline_add_24;

public class FileCodeAdapter extends RecyclerView.Adapter<FileCodeAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);
        void OnActionButtonClick();
    }

    private final List<EFileCode> plCollection;
    private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
    private List<EImageInfo> imgInfo;
    private final OnItemClickListener mListener;
    private MessageBox poMessage;
    private String TransNox;
    DialogImagePreview dialogPreview;
    private int pos;
    public FileCodeAdapter(String tansNox,List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo,List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.documentsInfo = documentsInfo;
        this.TransNox = tansNox;
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
        poMessage = new MessageBox(parent.getContext());
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
            Log.e("collectionEntry", String.valueOf(collection.getEntryNox()));
            Log.e("collectionFile Code", collection.getFileCode());
            if (documentsInfo.size()> 0){
                for (int i = 0; i < documentsInfo.size(); i++){
                    Log.e("doc file code", documentsInfo.get(i).sFileCode);
                    Log.e("doc file Entry", String.valueOf(documentsInfo.get(i).nEntryNox));
                    if (collection.getFileCode().equalsIgnoreCase(documentsInfo.get(i).sFileCode) &&
                            collection.getEntryNox() == documentsInfo.get(i).nEntryNox ){
                        holder.fileStatDone.setVisibility(View.VISIBLE);
                        holder.fileStat.setVisibility(View.GONE);
                    }else{
                        holder.fileStatDone.setVisibility(View.GONE);
                        holder.fileStat.setVisibility(View.VISIBLE);
                    }
                }
            }



        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
        //        public TextView lblDCPNox;
        public static ImageView fileStat;
        public ImageView fileStatDone;
        public String fileLoc;
        private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
        public FileCodeViewHolder(Context mContext, @NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            this.documentsInfo = documentsInfo;
            lbl_fileCode = itemView.findViewById(id.lbl_fileCode);
            lbl_fileLoc = itemView.findViewById(id.lbl_fileLoc);
            fileStat = itemView.findViewById(id.tick_cross);
            fileStatDone = itemView.findViewById(id.tick_done);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.OnClick(position);
                }

            });

        }
    }


}