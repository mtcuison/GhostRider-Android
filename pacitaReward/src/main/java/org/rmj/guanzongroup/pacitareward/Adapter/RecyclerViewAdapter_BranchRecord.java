package org.rmj.guanzongroup.pacitareward.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPacita.BranchRecords;
import org.rmj.guanzongroup.pacitareward.R;

import java.util.List;

public class RecyclerViewAdapter_BranchRecord extends RecyclerView.Adapter<RecyclerViewHolder_BranchRecord> {
    private Context context;
    private List<BranchRecords> branchRecords;
    private onSelectItem mlistener;

    public RecyclerViewAdapter_BranchRecord(Context context, List<BranchRecords> branchRecords, onSelectItem mlistener){
        this.context = context;
        this.branchRecords = branchRecords;
        this.mlistener = mlistener;
    }

    public interface onSelectItem {
        void onItemSelected(String dtTransact);
    }

    @NonNull
    @Override
    public RecyclerViewHolder_BranchRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_branchrecords, parent, false);
        return new RecyclerViewHolder_BranchRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_BranchRecord holder, int position) {
        holder.mtvrecord_date.setText(branchRecords.get(position).dTransact);
        holder.mtvrecord_rate.setText(branchRecords.get(position).nRatingxx);

        holder.mtvrecord_date.setOnClickListener(v -> mlistener.onItemSelected(branchRecords.get(position).dTransact));
        holder.mtvrecord_rate.setOnClickListener(v -> mlistener.onItemSelected(branchRecords.get(position).dTransact));
    }

    @Override
    public int getItemCount() {
        return branchRecords.size();
    }
}
