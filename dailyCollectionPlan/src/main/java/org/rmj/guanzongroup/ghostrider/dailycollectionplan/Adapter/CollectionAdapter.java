package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.CollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private final List<CollectionPlan> collectionPlans;
    private final OnItemClickListener mListener;

    public CollectionAdapter(List<CollectionPlan> collectionPlans, OnItemClickListener listener){
        this.collectionPlans = collectionPlans;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_collection, parent, false);
        return new CollectionViewHolder(v, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        CollectionPlan collection = collectionPlans.get(position);
        holder.loPlan = collection;
        holder.lblAcctNo.setText("Account No. : "+collection.getAcctNoxxx());
        holder.lblDCPNox.setText(collection.getDCPNumber());
        holder.lblClient.setText(collection.getClientNme());
        holder.lblAdd1xx.setText(collection.getAddressxx());
        holder.lblMobile.setText(collection.getContactxx());
        holder.lblBalanc.setText(collection.getBalancexx());
        holder.lblAmount.setText(collection.getAmntDuexx());
        holder.lblStatus.setText(collection.getStatusxxx());
    }

    @Override
    public int getItemCount() {
        return collectionPlans.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder{

        public CollectionPlan loPlan;
        public TextView lblAcctNo;
        public TextView lblDCPNox;
        public TextView lblClient;
        public TextView lblAdd1xx;
        public TextView lblMobile;
        public TextView lblBalanc;
        public TextView lblAmount;
        public TextView lblStatus;

        public CollectionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            lblAcctNo = itemView.findViewById(R.id.lbl_AccountNo);
            lblDCPNox = itemView.findViewById(R.id.lbl_dcpNox);
            lblClient = itemView.findViewById(R.id.lbl_clientNm);
            lblAdd1xx = itemView.findViewById(R.id.lbl_dcpAddress1);
            lblMobile = itemView.findViewById(R.id.lbl_dcpContact);
            lblBalanc = itemView.findViewById(R.id.lbl_dcpBalance);
            lblAmount = itemView.findViewById(R.id.lbl_dcpAmount);
            lblStatus = itemView.findViewById(R.id.lbl_dcpStatus);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.OnClick(position);
                }
            });

            lblAdd1xx.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.OnAddressClickListener("Sample", null);
                }
            });

            lblMobile.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.OnMobileNoClickListener(loPlan.getContactxx());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnClick(int position);
        void OnMobileNoClickListener(String MobileNo);
        void OnAddressClickListener(String Address, String[] args);
        void OnActionButtonClick();
    }
}
