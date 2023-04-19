package org.rmj.guanzongroup.pacitareward.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;
import org.rmj.guanzongroup.pacitareward.R;

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
                    holder.pass_btn.setBackgroundColor(R.color.cardview_shadow_start_color);
                    holder.fail_btn.setBackgroundColor(Color.TRANSPARENT);
                    mListener.onItemSelect(questionList.get(position).getsRateIDxx().toString(), "1");
                }
            });

        holder.fail_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                holder.pass_btn.setBackgroundColor(Color.TRANSPARENT);
                holder.fail_btn.setBackgroundColor(R.color.cardview_shadow_start_color);
                mListener.onItemSelect(questionList.get(position).getsRateIDxx().toString(), "0");
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
