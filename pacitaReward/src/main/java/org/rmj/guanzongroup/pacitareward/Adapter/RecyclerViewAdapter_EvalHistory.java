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
    private List<DPacita.RecentRecords> histevaluationlist;
    private RecyclerViewAdapter_EvalHistory.onSelectItem mlistener;

    public RecyclerViewAdapter_EvalHistory(Context context, List<DPacita.RecentRecords> histevaluationlist, RecyclerViewAdapter_EvalHistory.onSelectItem mlistener){
        this.context = context;
        this.histevaluationlist = histevaluationlist;
        this.mlistener = mlistener;
    }

    public interface onSelectItem {
        void onItemSelected(String transactNox, String branchcode, String branchname);
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
        DPacita.RecentRecords evaldata = histevaluationlist.get(position);
        String transnox = evaldata.sTransNox;
        String branchname = evaldata.sBranchNm;
        String branchcode = "evaldata.sBranchCd";

        holder.mtvrecord_date.setText(FormatUIText.formatGOCasBirthdate(histevaluationlist.get(position).dTransact));
        holder.mtvrecord_rate.setText(histevaluationlist.get(position).nRatingxx);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onItemSelected(transnox, branchcode, branchname);
            }
        });
        holder.mtvrecord_date.setOnClickListener(v -> mlistener.onItemSelected(transnox, branchcode, branchname));
        holder.mtvrecord_rate.setOnClickListener(v -> mlistener.onItemSelected(transnox, branchcode, branchname));
    }

    @Override
    public int getItemCount() {
        return histevaluationlist.size();
    }
}
