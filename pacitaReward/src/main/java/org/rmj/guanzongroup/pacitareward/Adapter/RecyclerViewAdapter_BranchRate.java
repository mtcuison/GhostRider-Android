package org.rmj.guanzongroup.pacitareward.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.pojo.BranchRate;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewHolder.RecyclerViewHolder_BranchRate;

import java.util.List;

public class RecyclerViewAdapter_BranchRate extends RecyclerView.Adapter<RecyclerViewHolder_BranchRate> {

    private List<BranchRate> questionList;
    private Context context;
    private onSelect mListener;

    public interface onSelect{
        void onItemSelect(String EntryNox, String result);
    }

    public RecyclerViewAdapter_BranchRate(Context context, List<BranchRate> questionList, onSelect mListener){
        this.context = context;
        this.questionList = questionList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_BranchRate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_rec_ratelist, parent, false);
        return new RecyclerViewHolder_BranchRate(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_BranchRate holder, int position) {
            holder.item_question.setText(String.valueOf(position + 1) + ".  " + questionList.get(position).getsRateName());
            holder.pass_btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    mListener.onItemSelect(questionList.get(position).getsRateIDxx().toString(), "1");
                }
            });

        holder.fail_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mListener.onItemSelect(questionList.get(position).getsRateIDxx().toString(), "0");
            }
        });

        if(questionList.get(position).getcPasRatex().equalsIgnoreCase("1")){
            holder.pass_btn.setBackgroundColor(Color.GRAY);
            holder.pass_btn.setSelected(true);
        } else if(questionList.get(position).getcPasRatex().equalsIgnoreCase("0")){
            holder.fail_btn.setBackgroundColor(Color.GRAY);
            holder.fail_btn.setSelected(true);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
