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

import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.etc.CashFormatter;
import org.rmj.g3appdriver.etc.FormatUIText;
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
            holder.lblTotalEntry.setText(getTotalCashCount(poCount));
            holder.itemView.setOnClickListener(v -> mListener.OnClick(poCount));
        }

        @Override
        public int getItemCount() {
            return plCashCount.size();
        }

    public static class CashCountViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView lblTransNox, lblEntryDte, lblBranchCde, lblSendStat, lblTotalEntry;

        public CashCountViewHolder(@NonNull View itemView) {
            super(itemView);

            lblTransNox = itemView.findViewById(R.id.lbl_list_ccTransNox);
            lblBranchCde = itemView.findViewById(R.id.lbl_list_ccBranch);
            lblEntryDte = itemView.findViewById(R.id.lbl_list_ccEntryDte);
            lblSendStat = itemView.findViewById(R.id.lbl_list_ccSentStatus);
            lblTotalEntry = itemView.findViewById(R.id.lbl_list_ccTotalEntry);
        }
    }

    private String getTotalCashCount(DCashCount.CashCountLog foCashCnt) {
        double lnTotalCsh = 0.0;
        lnTotalCsh += cashMultiplier(CashValues.P1000, foCashCnt.nNte1000p);
        lnTotalCsh += cashMultiplier(CashValues.P0500, foCashCnt.nNte0500p);
        lnTotalCsh += cashMultiplier(CashValues.P0200, foCashCnt.nNte0200p);
        lnTotalCsh += cashMultiplier(CashValues.P0100, foCashCnt.nNte0100p);
        lnTotalCsh += cashMultiplier(CashValues.P0050, foCashCnt.nNte0050p);
        lnTotalCsh += cashMultiplier(CashValues.P0020, foCashCnt.nNte0020p);
        lnTotalCsh += cashMultiplier(CashValues.P0010, foCashCnt.nCn0010px);
        lnTotalCsh += cashMultiplier(CashValues.P0005, foCashCnt.nCn0005px);
        lnTotalCsh += cashMultiplier(CashValues.P0001, foCashCnt.nCn0001px);
        lnTotalCsh += cashMultiplier(CashValues.C0050, foCashCnt.nCn0050cx);
        lnTotalCsh += cashMultiplier(CashValues.C0025, foCashCnt.nCn0025cx);
        lnTotalCsh += cashMultiplier(CashValues.C0010, foCashCnt.nCn0010cx);
        lnTotalCsh += cashMultiplier(CashValues.C0005, foCashCnt.nCn0005cx);
        lnTotalCsh += cashMultiplier(CashValues.C0001, foCashCnt.nCn0001cx);
        return CashFormatter.parse(String.valueOf(lnTotalCsh));
    }
    
    public double cashMultiplier(CashValues foValue, String fnQuantty) {
        switch (foValue) {
            case P1000:
                return 1000 * Double.parseDouble(fnQuantty);
            case P0500:
                return 500 * Double.parseDouble(fnQuantty);
            case P0200:
                return 200 * Double.parseDouble(fnQuantty);
            case P0100:
                return 100  * Double.parseDouble(fnQuantty);
            case P0050:
                return 50  * Double.parseDouble(fnQuantty);
            case P0020:
                return 20 * Double.parseDouble(fnQuantty);
            case P0010:
                return 10  * Double.parseDouble(fnQuantty);
            case P0005:
                return 5 * Double.parseDouble(fnQuantty);
            case P0001:
                return 1 * Double.parseDouble(fnQuantty);
            case C0050:
                return 0.50 * Double.parseDouble(fnQuantty);
            case C0025:
                return 0.25 * Double.parseDouble(fnQuantty);
            case C0010:
                return 0.10 * Double.parseDouble(fnQuantty);
            case C0005:
                return 0.05 * Double.parseDouble(fnQuantty);
            case C0001:
                return 0.01 * Double.parseDouble(fnQuantty);
            default:
                return 0.0;
        }
    }

    public enum CashValues{
        P1000,
        P0500,
        P0200,
        P0100,
        P0050,
        P0020,
        P0010,
        P0005,
        P0001,
        C0050,
        C0025,
        C0010,
        C0005,
        C0001,
    }

}
