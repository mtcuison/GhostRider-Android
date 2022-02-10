package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class AdapterInventoryBranch extends RecyclerView.Adapter<AdapterInventoryBranch.BranchListViewHolder> {

    private final List<EBranchInfo> paBranch;
    private final OnBranchSelectListener listener;

    public interface OnBranchSelectListener{
        void OnSelect(String BranchCode, String BranchName);
    }

    public AdapterInventoryBranch(List<EBranchInfo> paBranch, OnBranchSelectListener listener) {
        this.paBranch = paBranch;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BranchListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventory_branch, parent, false);
        return new BranchListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BranchListViewHolder holder, int position) {
        EBranchInfo loBranch = paBranch.get(position);
        holder.lblBranchCd.setText(loBranch.getBranchCd());
        holder.lblBranchNm.setText(loBranch.getBranchNm());
        holder.itemView.setOnClickListener(v -> listener.OnSelect(loBranch.getBranchCd(), loBranch.getBranchNm()));
    }

    @Override
    public int getItemCount() {
        return paBranch.size();
    }

    public static class BranchListViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView lblBranchCd, lblBranchNm;

        public BranchListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            lblBranchCd = itemView.findViewById(R.id.lbl_branchCd);
            lblBranchNm = itemView.findViewById(R.id.lbl_branchNm);
        }
    }
}
