package org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.EvaluationHistoryInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

import java.util.List;

public class CreditEvaluationHistoryInfoAdapter extends RecyclerView.Adapter<CreditEvaluationHistoryInfoAdapter.DataViewHolder> {

    private final List<EvaluationHistoryInfoModel> plCredit;

    public CreditEvaluationHistoryInfoAdapter(List<EvaluationHistoryInfoModel> flCredit) {
        this.plCredit = flCredit;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ci_history_info, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        EvaluationHistoryInfoModel loCredit = plCredit.get(position);
        holder.lnHeader.setVisibility(loCredit.isHeader());
        holder.lnContent.setVisibility(loCredit.isContent());
        holder.lblHeader.setText(loCredit.getHeader());
        holder.lblDetail.setText(loCredit.getLabel());
        holder.lblContnt.setText(loCredit.getContent());
    }

    @Override
    public int getItemCount() {
        return plCredit.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout lnHeader, lnContent;
        private TextView lblHeader, lblDetail, lblContnt;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            lnHeader = itemView.findViewById(R.id.ln_list_header);
            lnContent = itemView.findViewById(R.id.ln_list_content);
            lblHeader = itemView.findViewById(R.id.lbl_list_header);
            lblDetail = itemView.findViewById(R.id.lbl_list_detailInfo);
            lblContnt = itemView.findViewById(R.id.lbl_list_detailContent);
        }
    }

}
