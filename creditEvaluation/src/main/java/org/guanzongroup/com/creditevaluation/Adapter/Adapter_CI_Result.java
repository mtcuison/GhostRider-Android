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
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

import java.util.Objects;

public class Adapter_CI_Result extends RecyclerView.Adapter<Adapter_CI_Result.VHEvaluations> {

    private final Context mContext;
    private final JSONArray poList;

    public Adapter_CI_Result(Context mContext, JSONArray poList) {
        this.mContext = mContext;
        this.poList = poList;
    }


    @NonNull
    @Override
    public VHEvaluations onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ci_result, parent, false);
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
            holder.recyclerView.setAdapter(new Evaluate(laDetail));
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

        public Evaluate(JSONArray poList) {
            this.poEvaluate = poList;
        }

        @NonNull
        @Override
        public VHQuestions onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info_result, parent, false);
            return new VHQuestions(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VHQuestions holder, int position) {
            try{
                String lsLabel = "", lsKeyxx = "", lsValue = "";
                JSONObject loJson = poEvaluate.getJSONObject(position);
                JSONArray laDetail = loJson.names();
                if(!loJson.isNull(Objects.requireNonNull(laDetail).getString(0)) &&
                    isJson(loJson.getString(laDetail.getString(0)))) {
                    JSONObject loDetail = loJson.getJSONObject(laDetail.getString(0));
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

                if(lsValue.equalsIgnoreCase("10")){
                    holder.lblResult.setText("Correct");
                } else if(lsValue.equalsIgnoreCase("20")) {
                    holder.lblResult.setText("Incorrect");
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

            public TextView lblCaption, lblResult;

            public VHQuestions(@NonNull View itemView) {
                super(itemView);
                lblCaption = itemView.findViewById(R.id.lblEvaluate);
                lblResult = itemView.findViewById(R.id.lblResult);
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
