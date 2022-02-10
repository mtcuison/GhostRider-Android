package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountDetailedInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_CashCountDetailInfo extends RecyclerView.Adapter<Adapter_CashCountDetailInfo.CashCountDataHolder> {

    private final List<CashCountDetailedInfo> poCashDta;

    public Adapter_CashCountDetailInfo(List<CashCountDetailedInfo> foCashDta) {
        this.poCashDta = foCashDta;
    }

    @NonNull
    @Override
    public CashCountDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cash_count_list_info, parent, false);
        
        return new CashCountDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CashCountDataHolder holder, int position) {
        CashCountDetailedInfo loCashDta = poCashDta.get(position);
        holder.loTitlex.setVisibility(loCashDta.isHeader());
        holder.loDataxx.setVisibility(loCashDta.isContent());

        holder.txtTitle.setText(loCashDta.getHeader());
        holder.txtLabel.setText(loCashDta.getLabel());
        holder.txtDatax.setText(loCashDta.getContent());
    }

    @Override
    public int getItemCount() {
        return poCashDta.size();
    }

    public static class CashCountDataHolder extends RecyclerView.ViewHolder{

        LinearLayout loTitlex, loDataxx;
        TextView txtTitle, txtLabel, txtDatax;

        public CashCountDataHolder(@NonNull View itemView) {
            super(itemView);
            loTitlex = itemView.findViewById(R.id.title_layout);
            loDataxx = itemView.findViewById(R.id.data_layout);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtLabel = itemView.findViewById(R.id.txtLabel);
            txtDatax = itemView.findViewById(R.id.txtData);
        }
    }

}
