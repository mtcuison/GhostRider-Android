package org.rmj.guanzongroup.ghostrider.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.Entities.EItinerary;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.R;

import java.util.List;

public class AdapterRewards extends RecyclerView.Adapter<AdapterRewards.RewardsViewHolder> {

    private final List<String> poList;
    private final OnClickListener mListener;

    public interface OnClickListener{
        void OnClick(EItinerary args);
    }

    public AdapterRewards(List<String> poList, OnClickListener mListener) {
        this.poList = poList;
        this.mListener = mListener;
    }
    @NonNull
    @Override
    public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rewards, parent, false);
        return new RewardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsViewHolder holder, int position) {
        try {
            String loDetail = poList.get(position);
            holder.lblLastNme.setText("DE CELIS");
            holder.lblFirstNme.setText("TEEJEI");
            holder.lblMiddleNme.setText("FERNANDEZ");

            //holder.view.setOnClickListener(view -> mListener.OnClick(loDetail));
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
                lblLastNme,
                lblFirstNme,
                lblMiddleNme;

        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lblLastNme = itemView.findViewById(R.id.lblLastNme);
            lblFirstNme = itemView.findViewById(R.id.lblFirstNme);
            lblMiddleNme = itemView.findViewById(R.id.lblMiddleNme);
        }
    }
}
