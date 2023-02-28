package org.guanzongroup.com.creditevaluation.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.guanzongroup.com.creditevaluation.R;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.etc.FormatUIText;

import java.util.List;

public class Adapter_CI_Evaluate extends RecyclerView.Adapter<Adapter_CI_Evaluate.VHEvaluation> {

    private final JSONArray poEvaluate;
    private final List<EOccupationInfo> poJob;
    private final boolean cPreview;
    private final onSelectResultListener mListener;

    public Adapter_CI_Evaluate(JSONArray poList,
                               List<EOccupationInfo> poJob,
                               boolean cPreview,
                               onSelectResultListener listener) {
        this.poEvaluate = poList;
        this.poJob = poJob;
        this.cPreview = cPreview;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VHEvaluation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_evaluate, parent, false);
        return new VHEvaluation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHEvaluation holder, int position) {
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
                if(lsKeyxx.equalsIgnoreCase("sPosition")){
                    for (int x = 0; x < poJob.size(); x++){
                        if(lsLabel.equalsIgnoreCase(poJob.get(x).getOccptnID())){
                            lsLabel = poJob.get(x).getOccptnNm();
                            break;
                        }
                    }
                }
                lsValue = loDetail.getString("sValuexxx");
            } else if(!loJson.isNull(laDetail.getString(0)) &&
                    !isJson(loJson.getString(laDetail.getString(0)))){
                lsLabel = loJson.getString("sLabelxxx");
                lsKeyxx = loJson.getString("sKeyNamex");
                if(lsKeyxx.equalsIgnoreCase("sPosition")){
                    for (int x = 0; x < poJob.size(); x++){
                        if(lsLabel.equalsIgnoreCase(poJob.get(x).getOccptnID())){
                            lsLabel = poJob.get(x).getOccptnNm();
                            break;
                        }
                    }
                }
                lsValue = loJson.getString("sValuexxx");
            }

            holder.lblCaption.setText(
                    createCaption(
                            lsLabel,
                            lsKeyxx));

            String finalLsParnt = lsParnt;
            String finalLsKeyxx = lsKeyxx;

            if(!cPreview) {
                holder.btnEdit.setOnClickListener(v -> {
                    mListener.OnUpdate(finalLsKeyxx, isValid -> {
                        if(isValid){
                            holder.EnableRBs();
                            holder.btnEdit.setVisibility(View.GONE);
                        }
                    });
                });

                holder.rbCor.setOnClickListener(v -> {
                    mListener.OnCorrect(finalLsParnt, finalLsKeyxx, "10", isChecked -> {
                        if (isChecked) {
                            holder.rbCor.setChecked(true);
                            holder.btnEdit.setVisibility(View.VISIBLE);
                            holder.DisableRBs();
                        } else {
                            holder.rbCor.setChecked(false);
                        }
                    });
                });

                holder.rbInc.setOnClickListener(v -> {
                    mListener.OnCorrect(finalLsParnt, finalLsKeyxx, "20", isChecked -> {
                        if (isChecked) {
                            holder.rbInc.setChecked(true);
                            holder.btnEdit.setVisibility(View.VISIBLE);
                            holder.DisableRBs();
                        } else {
                            holder.rbInc.setChecked(false);
                        }
                    });
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
            } else {
                holder.DisableRBs();
                if(lsValue.equalsIgnoreCase("10")){
                    holder.rbCor.setChecked(true);
                } else if(lsValue.equalsIgnoreCase("20")) {
                    holder.rbInc.setChecked(true);
                }
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

    public static class VHEvaluation extends RecyclerView.ViewHolder{

        public MaterialTextView lblCaption;
        public RadioGroup rgResult;
        public RadioButton rbCor, rbInc;
        public MaterialButton btnEdit;

        public VHEvaluation(@NonNull View itemView) {
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

    private static String createCaption(String fsLabel, String fsKey) {
        double lsResult;
        double lnVal;
        String lsValue;
        if(fsLabel.equalsIgnoreCase("1")){
            lsValue = "YES";
        } else if(fsLabel.equalsIgnoreCase("0")){
            lsValue = "NO";
        } else if(fsLabel.isEmpty()){
            lsValue = "No property info provided by applicant.";
        } else {
            lsValue = fsLabel;
        }
        switch (fsKey){
            case "sProprty1":
                return "Property 1: " + lsValue;
            case "sProprty2":
                return "Property 2: " + lsValue;
            case "sProprty3":
                return "Property 3: " + lsValue;
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
            case "sPosition":
                return "Job Title/Position : " + lsValue;
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
