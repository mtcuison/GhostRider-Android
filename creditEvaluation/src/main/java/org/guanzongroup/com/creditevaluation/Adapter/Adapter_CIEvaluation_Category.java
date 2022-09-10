package org.guanzongroup.com.creditevaluation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.R;
import org.json.JSONArray;
import org.json.JSONObject;

public class Adapter_CIEvaluation_Category extends RecyclerView.Adapter<Adapter_CIEvaluation_Category.VHEvaluationCategory> {

    private final Context mContext;
    private final JSONArray poList;
    private final boolean cPreview;
    private final onSelectResultListener mListener;

    public Adapter_CIEvaluation_Category(Context mContext, JSONArray poList, boolean cPreview, onSelectResultListener mListener) {
        this.mContext = mContext;
        this.poList = poList;
        this.cPreview = cPreview;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public VHEvaluationCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluation_category, parent, false);
        return new VHEvaluationCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHEvaluationCategory holder, int position) {
        try{
            JSONObject loJson = poList.getJSONObject(position);
            String lsHeader = loJson.getString("category");
            holder.lblHeader.setText(getCategoryLabel(lsHeader));
            LinearLayoutManager loManager = new LinearLayoutManager(mContext);
            loManager.setOrientation(RecyclerView.VERTICAL);
            holder.recyclerView.setLayoutManager(loManager);
            JSONArray laDetail = loJson.getJSONArray(lsHeader);
            holder.recyclerView.setAdapter(new Adapter_CI_Evaluate(laDetail, cPreview, new onSelectResultListener() {
                @Override
                public void OnCorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                    mListener.OnCorrect(lsHeader, fsKey, fsRes, listener);
                }

                @Override
                public void OnIncorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                    mListener.OnIncorrect(lsHeader, fsKey, fsRes, listener);
                }
            }));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poList.length();
    }

    public static class VHEvaluationCategory extends RecyclerView.ViewHolder{

        public RecyclerView recyclerView;
        public TextView lblHeader;

        public VHEvaluationCategory(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            lblHeader = itemView.findViewById(R.id.textview);
        }
    }

    private String getCategoryLabel(String fsVal){
        switch (fsVal){
            case "present_address":
                return "Present Address";
            case "primary_address":
                return "Primary Address";
            case "employed":
                return "Employment Info";
            case "self_employed":
                return "Business Info";
            case "financed":
                return "Finance Info";
            default:
                return "Pension Info";
        }
    }
}
