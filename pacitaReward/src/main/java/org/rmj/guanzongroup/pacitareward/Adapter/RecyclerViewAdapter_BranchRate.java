package org.rmj.guanzongroup.pacitareward.Adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.pojo.BranchRate;
import org.rmj.guanzongroup.pacitareward.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_BranchRate extends RecyclerView.Adapter<RecyclerViewAdapter_BranchRate.RatingViewHolder> {
    private static final String TAG = RecyclerViewAdapter_BranchRate.class.getSimpleName();

    private List<BranchRate> poRatings = new ArrayList<>();
    private final onSelect mListener;

    public interface onSelect{
        void onItemSelect(String EntryNox, String result);
    }

    public RecyclerViewAdapter_BranchRate(onSelect mListener){
        this.mListener = mListener;
    }

    public void setItems(List<BranchRate> foRatings){
        this.poRatings = foRatings;
    }

    @NonNull
    @Override
    public RatingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluation, parent, false);
        return new RatingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingViewHolder holder, int position) {
        BranchRate loRate = poRatings.get(position);
        holder.item_question.setText(position + 1 + ".  " + loRate.getsRateName());
        holder.pass_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mListener.onItemSelect(loRate.getsRateIDxx().toString(), "1");
            }
        });

        holder.fail_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                mListener.onItemSelect(loRate.getsRateIDxx().toString(), "0");
            }
        });

        Log.d(TAG, "Entry No. " + loRate.getsRateIDxx() + ", Result: " + loRate.getcPasRatex());

        if(loRate.getcPasRatex().equalsIgnoreCase("1")){
            holder.toggleGroup.check(R.id.pass_btn);
            holder.toggleGroup.uncheck(R.id.fail_btn);
        } else if(loRate.getcPasRatex().equalsIgnoreCase("0")){
            holder.toggleGroup.check(R.id.fail_btn);
            holder.toggleGroup.uncheck(R.id.pass_btn);
        } else {
            holder.toggleGroup.uncheck(R.id.fail_btn);
            holder.toggleGroup.uncheck(R.id.pass_btn);
        }
    }

    @Override
    public int getItemCount() {
        return poRatings.size();
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView item_question;
        public MaterialButton pass_btn;
        public MaterialButton fail_btn;
        public MaterialButtonToggleGroup toggleGroup;

        public RatingViewHolder(@NonNull View itemView) {
            super(itemView);
            item_question = itemView.findViewById(R.id.item_question);
            toggleGroup = itemView.findViewById(R.id.materialButtonToggleGroup);
            pass_btn = itemView.findViewById(R.id.pass_btn);
            fail_btn = itemView.findViewById(R.id.fail_btn);
        }
    }
}
