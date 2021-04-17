package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class AdapterClientSearchList extends RecyclerView.Adapter<AdapterClientSearchList.ClientListHolder> {

    private final List<EDCPCollectionDetail> collectionDetails;
    private final OnAdapterClickListener mListener;

    public interface OnAdapterClickListener{
        void OnClick(EDCPCollectionDetail detail);
    }

    public AdapterClientSearchList(List<EDCPCollectionDetail> collectionDetails, OnAdapterClickListener listener) {
        this.collectionDetails = collectionDetails;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ClientListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_client_search, parent, false);
        return new ClientListHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListHolder holder, int position) {
        EDCPCollectionDetail loDetail = collectionDetails.get(position);
        holder.loDetail = collectionDetails.get(position);
        holder.lblAccNox.setText(loDetail.getAcctNmbr());
        holder.lblClientNm.setText(loDetail.getFullName());
        String lsAddress = "";
        if(!loDetail.getHouseNox().isEmpty()){
            lsAddress = lsAddress + loDetail.getHouseNox() + ", ";
        }
        if(!loDetail.getAddressx().isEmpty()){
            lsAddress = lsAddress + loDetail.getAddressx() + ", ";
        }
        if(!loDetail.getTownName().isEmpty()){
            lsAddress = lsAddress + loDetail.getTownName();
        }
        holder.lblAddress.setText(lsAddress);
    }

    @Override
    public int getItemCount() {
        return collectionDetails.size();
    }

    public static class ClientListHolder extends RecyclerView.ViewHolder{

        EDCPCollectionDetail loDetail;
        TextView lblAccNox, lblClientNm, lblAddress;

        public ClientListHolder(@NonNull View itemView, OnAdapterClickListener listener) {
            super(itemView);
            lblAccNox = itemView.findViewById(R.id.lbl_AccountNo);
            lblClientNm = itemView.findViewById(R.id.lbl_clientNm);
            lblAddress = itemView.findViewById(R.id.lbl_addressx);


            itemView.setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION){
                    listener.OnClick(loDetail);
                }
            });
        }
    }
}
