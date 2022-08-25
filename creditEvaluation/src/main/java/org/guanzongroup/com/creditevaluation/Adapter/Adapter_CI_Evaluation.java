package org.guanzongroup.com.creditevaluation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.guanzongroup.com.creditevaluation.R;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

public class Adapter_CI_Evaluation extends RecyclerView.Adapter<Adapter_CI_Evaluation.VHEvaluations> {

    private final Context mContext;
    private final JSONArray poList;
    private final onSelectResultListener mListener;

    public interface onSelectResultListener {
        void OnCorrect(String fsPar, String fsKey, String fsRes);
        void OnIncorrect(String fsPar, String fsKey, String fsRes);
    }

    public Adapter_CI_Evaluation(Context mContext, JSONArray poList, onSelectResultListener mListener) {
        this.mContext = mContext;
        this.poList = poList;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public VHEvaluations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluations, parent, false);
        return new VHEvaluations(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHEvaluations holder, int position) {
        try{
            JSONObject loJson = poList.getJSONObject(position);
            String lsHeader = loJson.getString("header");
            holder.lblHeader.setText(lsHeader);
            LinearLayoutManager loManager = new LinearLayoutManager(mContext);
            loManager.setOrientation(RecyclerView.VERTICAL);
            holder.recyclerView.setLayoutManager(loManager);
            JSONArray laDetail = loJson.getJSONArray("detail");
            holder.recyclerView.setAdapter(new Evaluate(laDetail, new onSelectResultListener() {
                @Override
                public void OnCorrect(String fsPar, String fsKey, String fsRes) {
                    mListener.OnCorrect(fsPar, fsKey, fsRes);
                }

                @Override
                public void OnIncorrect(String fsPar, String fsKey, String fsRes) {
                    mListener.OnIncorrect(fsPar, fsKey, fsRes);
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

    public static class VHEvaluations extends RecyclerView.ViewHolder{

        public RecyclerView recyclerView;
        public TextView lblHeader;

        public VHEvaluations(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            lblHeader = itemView.findViewById(R.id.textview);
        }
    }

    private static class Evaluate extends RecyclerView.Adapter<Evaluate.VHQuestions> {

        private final JSONArray poEvaluate;
        private final onSelectResultListener mListener;

        public Evaluate(JSONArray poList, onSelectResultListener listener) {
            this.poEvaluate = poList;
            this.mListener = listener;
        }

        @NonNull
        @Override
        public VHQuestions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluate, parent, false);
            return new VHQuestions(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VHQuestions holder, int position) {
            try{
                String lsParnt = "", lsLabel = "", lsKeyxx = "", lsValue = "";
                JSONObject loJson = poEvaluate.getJSONObject(position);
                JSONArray laDetail = loJson.names();
                if(!loJson.isNull(laDetail.getString(0)) &&
                    isJson(loJson.getString(laDetail.getString(0)))) {
                    JSONObject loDetail = loJson.getJSONObject(laDetail.getString(0));
                    lsParnt = laDetail.getString(0);
                    lsLabel = loDetail.getString("sLabelxxx");
                    lsKeyxx = loDetail.getString("sKeyNamex");
                    lsValue = loDetail.getString("sValuexxx");
                } else if(!loJson.isNull(laDetail.getString(0)) &&
                        !isJson(loJson.getString(laDetail.getString(0)))){
                    lsLabel = loJson.getString("sLabelxxx");
                    lsKeyxx = loJson.getString("sKeyNamex");
                    lsValue = loJson.getString("sValuexxx");
                }

                holder.lblCaption.setText(
                        createCaption(
                                lsLabel,
                                lsKeyxx));

                String finalLsParnt = lsParnt;
                String finalLsKeyxx = lsKeyxx;

                holder.btnEdit.setOnClickListener(v -> {
                    holder.EnableRBs();
                    holder.btnEdit.setVisibility(View.GONE);
                });

                holder.rbCor.setOnClickListener(v -> {
                    holder.rbCor.setChecked(true);
                    mListener.OnCorrect(finalLsParnt, finalLsKeyxx, "10");
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.DisableRBs();
                });

                holder.rbInc.setOnClickListener(v -> {
                    holder.rbInc.setChecked(true);
                    mListener.OnCorrect(finalLsParnt, finalLsKeyxx, "20");
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    holder.DisableRBs();
                });

                if(lsValue.equalsIgnoreCase("10")){
                    holder.rbCor.setChecked(true);
                    holder.DisableRBs();
                } else if(lsValue.equalsIgnoreCase("20")) {
                    holder.rbInc.setChecked(true);
                    holder.DisableRBs();
                }

                if(holder.rbCor.isChecked() || holder.rbInc.isChecked()){
                    holder.btnEdit.setVisibility(View.VISIBLE);
                } else {
                    holder.btnEdit.setVisibility(View.GONE);
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return poEvaluate.length();
        }

        public static class VHQuestions extends RecyclerView.ViewHolder{

            public TextView lblCaption;
            public RadioGroup rgResult;
            public RadioButton rbCor, rbInc;
            public MaterialButton btnEdit;

            public VHQuestions(@NonNull View itemView) {
                super(itemView);
                lblCaption = itemView.findViewById(R.id.textview);
                rgResult = itemView.findViewById(R.id.radiogroup);
                btnEdit = itemView.findViewById(R.id.btn_Edit);
                rbCor = itemView.findViewById(R.id.rb_correct);
                rbInc = itemView.findViewById(R.id.rb_incorrect);
            }

            public void DisableRBs(){
                rbInc.setEnabled(false);
                rbCor.setEnabled(false);
            }

            public void EnableRBs(){
                rbInc.setEnabled(true);
                rbCor.setEnabled(true);
            }
        }
    }

    private static String createCaption(String fsLabel, String fsKey) {
        double lsResult;
        double lnVal;
        String lsValue;
        if(fsLabel.equalsIgnoreCase("1")){
            lsValue = "YES";
        } else {
            lsValue = "NO";
        }
        switch (fsKey){
            case "sProprty1":
                return "Property 1";
            case "sProprty2":
                return "Property 2";
            case "sProprty3":
                return "Property 3";
            case "cWith4Whl":
                return "Has 4 Wheeled Vehicle : "  + lsValue;
            case "cWith3Whl":
                return "Has 3 Wheel Vehicle(Tricycle) : " + lsValue;
            case "cWith2Whl":
                return "Has Bicycles/Motorcycles : " + lsValue;
            case "cWithRefx":
                return "Has Refrigerator : " + lsValue;
            case "cWithTVxx":
                return "Has Television : " + lsValue;
            case "cWithACxx":
                return "Has air condition : " + lsValue;
            case "nLenServc":
                lnVal = Double.parseDouble(fsLabel);
                if(lnVal % 1 == 0) {
                    lsValue = "Year/s";
                    lsResult =  lnVal;
                } else {
                    lsValue = "Month/s";
                    lnVal = lnVal * 12;
                    lsResult = (double) Math.round(lnVal);
                }
                return "Length Of Service : " + lsResult + lsValue;
            case "nBusLenxx":
                lnVal = Double.parseDouble(fsLabel);
                if(lnVal % 1 == 0) {
                    lsResult =  lnVal;
                } else {
                    lnVal = lnVal * 12;
                    lsResult = (double) Math.round(lnVal);
                }
                return "Years of Business : " + lsResult + lsValue;
            case "nSalaryxx":
                return "Salary : " + FormatUIText.getCurrencyUIFormat(fsLabel);
            case "nBusIncom":
                return "Business Income : " + FormatUIText.getCurrencyUIFormat(fsLabel);
            case "nMonExpns":
                return "Monthly Expenses : " + FormatUIText.getCurrencyUIFormat(fsLabel);
            case "nEstIncme":
                return "Estimate Income : " + FormatUIText.getCurrencyUIFormat(fsLabel);
            case "nPensionx":
                return "Pension Amount : " + FormatUIText.getCurrencyUIFormat(fsLabel);
            default:
                return fsLabel;
        }
    }

    private static boolean isJson(String fsVal){
        try{
            JSONObject loJson = new JSONObject(fsVal);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
