package org.rmj.guanzongroup.pacitareward;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    List<String> questionList;
    Context context;

    public RecyclerViewAdapter(Context context, List<String> questionList){
        this.context = context;
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_rec_ratelist, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            //holder.item_no.setText(String.valueOf(position + 1) + ".");
            holder.item_question.setText(String.valueOf(position + 1) + ".  " + questionList.get(position));

            holder.pass_btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    holder.pass_btn.setBackgroundColor(R.color.cardview_shadow_start_color);
                    holder.fail_btn.setBackgroundColor(Color.TRANSPARENT);
                }
            });

        holder.fail_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                holder.pass_btn.setBackgroundColor(Color.TRANSPARENT);
                holder.fail_btn.setBackgroundColor(R.color.cardview_shadow_start_color);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
