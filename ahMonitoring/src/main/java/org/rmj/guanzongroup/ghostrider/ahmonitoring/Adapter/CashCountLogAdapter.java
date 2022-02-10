/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office 
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 1:06 PM
 * project file last modified : 6/10/21 1:06 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class CashCountLogAdapter extends RecyclerView.Adapter<CashCountLogAdapter.CashCountViewHolder> {

    private static final String TAG = CashCountLogAdapter.class.getSimpleName();
    private final List<DCashCount.CashCountLog> plCashCount;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnClick(DCashCount.CashCountLog cashCountLog);
    }

    public CashCountLogAdapter(List<DCashCount.CashCountLog> plCashCount, OnItemClickListener listener) {
        this.plCashCount = plCashCount;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CashCountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cash_count_log, parent, false);
        return new CashCountViewHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull CashCountViewHolder holder, int position) {
            DCashCount.CashCountLog poCount = plCashCount.get(position);

            holder.lblTransNox.setText("Transaction No.: " + poCount.sTransNox);
            if(poCount.sSendStat.equalsIgnoreCase("1")) {
                holder.lblSendStat.setText("Sent");
            } else {
                holder.lblSendStat.setText("Waiting to send...");
            }
            holder.lblBranchCde.setText(poCount.sBranchNm);
            holder.lblEntryDte.setText(FormatUIText.getParseDateTime(poCount.dEntryDte));
            holder.itemView.setOnClickListener(v -> mListener.OnClick(poCount));
        }

        @Override
        public int getItemCount() {
            return plCashCount.size();
        }

    public static class CashCountViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNox, lblEntryDte, lblBranchCde, lblSendStat, lblTotalEntry;

        public CashCountViewHolder(@NonNull View itemView) {
            super(itemView);

            lblTransNox = itemView.findViewById(R.id.lbl_list_ccTransNox);
            lblBranchCde = itemView.findViewById(R.id.lbl_list_ccBranch);
            lblEntryDte = itemView.findViewById(R.id.lbl_list_ccEntryDte);
            lblSendStat = itemView.findViewById(R.id.lbl_list_ccSentStatus);
            lblTotalEntry = itemView.findViewById(R.id.lbl_list_ccTotalEntry);
        }
    }

    private String getTotalEntry(ECashCount cashCount){
        double lnTotal = 0.0;
        int Qty = 0;
        double denomination = 0.0;
        if(!cashCount.getNte1000p().equalsIgnoreCase("0")){
            denomination = 1000;
            Qty = Integer.valueOf(cashCount.getNte1000p());
        }
        return String.valueOf(lnTotal);
    }
}
