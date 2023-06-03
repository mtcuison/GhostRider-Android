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

public abstract class AdapterRaffleDraw extends RecyclerView.Adapter<AdapterRaffleDraw.RaffleViewHolder> {

    private final List<PanaloRewards> poList;
    private final AdapterRaffleDraw.OnClickListener mListener;

    public abstract void OnClick(String args);

    public interface OnClickListener{
        void OnClick(PanaloRewards args);

    }
    public AdapterRaffleDraw(List<PanaloRewards> poList, OnClickListener mListener) {
        this.poList = poList;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public AdapterRaffleDraw.RaffleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_raffle, parent, false);
        return new AdapterRaffleDraw.RaffleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRaffleDraw.RaffleViewHolder holder, int position) {
        try {
            PanaloRewards loDetail = poList.get(position);
            holder.lblUserID.setText(loDetail.getUserIDxx());
            holder.lblBranch.setText(loDetail.getItemDesc());
            holder.itemView.setOnClickListener(v -> {
                //Action Done here...
                mListener.OnClick(loDetail);
            });


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class RaffleViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView
                lblUserID,
                lblBranch;

        public RaffleViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lblUserID = itemView.findViewById(R.id.lblUserID);
            lblBranch = itemView.findViewById(R.id.lblBranch);
        }
    }
}


