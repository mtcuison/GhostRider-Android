package org.rmj.guanzongroup.pacitareward.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita.RecentRecords;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.pacitareward.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_EvalHistory extends RecyclerView.Adapter<RecyclerViewHolder_EvalHist> {

    private Context context;
    private List<RecentRecords> histevaluationlist;
    private RecyclerViewAdapter_EvalHistory.onSelectItem mlistener;
    private List<RecentRecords> histevaluationfilter;

    public RecyclerViewAdapter_EvalHistory(Context context, List<RecentRecords> histevaluationlist, RecyclerViewAdapter_EvalHistory.onSelectItem mlistener){
        this.context = context;
        this.histevaluationlist = histevaluationlist;
        this.mlistener = mlistener;
        this.histevaluationfilter = new RecentEvalFilter(this);
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
        RecentRecords evaldata = histevaluationlist.get(position);
        String transnox = evaldata.sTransNox;
        String branchname = evaldata.sBranchNm;
        String branchcode = evaldata.sBranchCD;

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

    public class RecentEvalFilter extends Filter {
        private final RecyclerViewAdapter_EvalHistory poAdapter;
        public RecentEvalFilter(RecyclerViewAdapter_EvalHistory poAdapter) {
            this.poAdapter = poAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();

            if(constraint.length() == 0){
                histevaluationfilter = histevaluationlist;
            } else {
                List<RecentRecords> filterSearch = new ArrayList<>();
                for (RecentRecords evalhist:histevaluationlist){
                    String lsBranchNm = evalhist.sBranchNm;
                    if(lsBranchNm.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterSearch.add(evalhist);
                    }
                }
                histevaluationfilter = filterSearch;
            }
            results.values = histevaluationfilter;
            results.count = histevaluationfilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            poAdapter.histevaluationfilter = (List<RecentRecords>) results.values;
            this.poAdapter.notifyDataSetChanged();
        }
    }
}
