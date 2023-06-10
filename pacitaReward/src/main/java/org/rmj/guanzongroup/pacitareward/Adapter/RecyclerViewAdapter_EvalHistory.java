package org.rmj.guanzongroup.pacitareward.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.pacitareward.R;

import java.util.List;

public class RecyclerViewAdapter_EvalHistory extends RecyclerView.Adapter<RecyclerViewHolder_EvalHist> {

    private Context context;
    private List<DPacita.BranchRecords> branchRecords;
    private RecyclerViewAdapter_BranchRecord.onSelectItem mlistener;

    public RecyclerViewAdapter_EvalHistory(Context context, List<DPacita.BranchRecords> branchRecords, RecyclerViewAdapter_BranchRecord.onSelectItem mlistener){
        this.context = context;
        this.branchRecords = branchRecords;
        this.mlistener = mlistener;
    }

    public interface onSelectItem {
        void onItemSelected(String transactNox);
    }

    @NonNull
    @Override
    public RecyclerViewHolder_EvalHist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_branchrecords, parent, false);
        return new RecyclerViewHolder_EvalHist(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_EvalHist holder, int position) {
        holder.mtvrecord_date.setText(FormatUIText.formatGOCasBirthdate(branchRecords.get(position).dTransact));
        holder.mtvrecord_rate.setText(branchRecords.get(position).nRatingxx);

        holder.mtvrecord_date.setOnClickListener(v -> mlistener.onItemSelected(branchRecords.get(position).sTransNox));
        holder.mtvrecord_rate.setOnClickListener(v -> mlistener.onItemSelected(branchRecords.get(position).sTransNox));
    }

    @Override
    public int getItemCount() {
        return branchRecords.size();
    }
}
