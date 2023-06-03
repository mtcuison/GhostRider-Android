package org.rmj.guanzongroup.ghostrider.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.R;

import java.util.List;

public class AdapterRewardsClaimed extends RecyclerView.Adapter<AdapterRewardsClaimed.RewardsViewHolder> {

    private final List<PanaloRewards> poList;
    private final OnClickListener mListener;

    public interface OnClickListener{
        void OnClick(PanaloRewards args);
        void OnUseButtonClick(PanaloRewards args);

    }

    public AdapterRewardsClaimed(List<PanaloRewards> poList, OnClickListener mListener) {
        this.poList = poList;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_claim, parent, false);
        return new RewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
        try {
            PanaloRewards loDetail = poList.get(position);
            holder.lblRewardNme.setText(loDetail.getItemDesc());
            holder.lblRwrdType.setText(loDetail.getPanaloDs());
            holder.lblRewardDte.setText("Date:" + loDetail.getExpiryDt());
            holder.lblQty.setText("Qty: " + loDetail.getItemQtyx());
            holder.itemView.setOnClickListener(v -> {
                //Action Done here...
                mListener.OnClick(loDetail);
            });
            holder.btnUse.setOnClickListener(v -> {
                //Action Done here...
                mListener.OnUseButtonClick(loDetail);

            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class RewardsViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView
                lblRewardNme,
                lblRwrdType,
                lblRewardDte,
                lblQty;
        public MaterialButton btnUse;


        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lblQty = itemView.findViewById(R.id.lblQty);
            lblRewardNme = itemView.findViewById(R.id.lblRwrdNme);
            lblRwrdType = itemView.findViewById(R.id.lblRwrdType);
            lblRewardDte = itemView.findViewById(R.id.lblRwrdDte);
            btnUse = itemView.findViewById(R.id.btn_redeem);
        }
    }
}
