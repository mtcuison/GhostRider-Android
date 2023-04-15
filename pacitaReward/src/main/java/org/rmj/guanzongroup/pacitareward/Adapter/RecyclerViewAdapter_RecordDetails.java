package org.rmj.guanzongroup.pacitareward.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;
import org.rmj.guanzongroup.pacitareward.R;

import java.util.List;

public class RecyclerViewAdapter_RecordDetails extends RecyclerView.Adapter<RecyclerViewHolder_RecordDetails> {
    private List<BranchRate> evaluationList;
    private Context context;

    public RecyclerViewAdapter_RecordDetails(Context context, List<BranchRate> questionList){
        this.context = context;
        this.evaluationList = questionList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_RecordDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_branchrecord_details, parent, false);
        return new RecyclerViewHolder_RecordDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_RecordDetails holder, int position) {
        String cPasRate = evaluationList.get(position).getcPasRatex();
        String scritera = evaluationList.get(position).getsRateName();

        if (cPasRate.equals("0")){
            holder.mtv_criteria.setText(scritera);
            holder.siv_rate.setBackgroundResource(R.drawable.emoji_sad_svgrepo_com);
        } else if (cPasRate.equals("1")) {
            holder.mtv_criteria.setText(scritera);
            holder.siv_rate.setBackgroundResource(R.drawable.emoji_laugh_svgrepo_com);
        }
    }

    @Override
    public int getItemCount() {
        return evaluationList.size();
    }
}
