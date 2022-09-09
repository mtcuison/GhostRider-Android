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

public class Adapter_CIEvaluation_Headers extends RecyclerView.Adapter<Adapter_CIEvaluation_Headers.VHEvaluationHeaders> {

    private final Context mContext;
    private final JSONArray poList;
    private final boolean cPreview;
    private final onSelectResultListener mListener;

    public Adapter_CIEvaluation_Headers(Context mContext, JSONArray poList, boolean cPreview, onSelectResultListener mListener) {
        this.mContext = mContext;
        this.poList = poList;
        this.cPreview = cPreview;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public VHEvaluationHeaders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluation_header, parent, false);
        return new VHEvaluationHeaders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHEvaluationHeaders holder, int position) {
        try{
            JSONObject loJson = poList.getJSONObject(position);
            String lsHeader = loJson.getString("header");
            holder.lblHeader.setText(lsHeader);
            LinearLayoutManager loManager = new LinearLayoutManager(mContext);
            loManager.setOrientation(RecyclerView.VERTICAL);
            holder.recyclerView.setLayoutManager(loManager);
            JSONArray laDetail = loJson.getJSONArray("detail");
            if(laDetail.getJSONObject(0).has("category")) {
                holder.recyclerView.setAdapter(new Adapter_CIEvaluation_Category(mContext, laDetail, cPreview, new onSelectResultListener() {
                    @Override
                    public void OnCorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                        mListener.OnCorrect(fsPar, fsKey, fsRes, listener);
                    }

                    @Override
                    public void OnIncorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                        mListener.OnIncorrect(fsPar, fsKey, fsRes, listener);
                    }
                }));
            } else {
                holder.recyclerView.setAdapter(new Adapter_CI_Evaluate(laDetail, cPreview, new onSelectResultListener() {
                    @Override
                    public void OnCorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                        mListener.OnCorrect(fsPar, fsKey, fsRes, listener);
                    }

                    @Override
                    public void OnIncorrect(String fsPar, String fsKey, String fsRes, onEvaluate listener) {
                        mListener.OnIncorrect(fsPar, fsKey, fsRes, listener);
                    }
                }));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poList.length();
    }

    public static class VHEvaluationHeaders extends RecyclerView.ViewHolder{

        public RecyclerView recyclerView;
        public TextView lblHeader;

        public VHEvaluationHeaders(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            lblHeader = itemView.findViewById(R.id.textview);
        }
    }
}
