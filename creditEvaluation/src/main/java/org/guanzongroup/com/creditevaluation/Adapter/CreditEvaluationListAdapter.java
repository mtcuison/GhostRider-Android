/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.guanzongroup.com.creditevaluation.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.R;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditOnlineApplicationCI;

import java.util.ArrayList;
import java.util.List;

public class CreditEvaluationListAdapter extends RecyclerView.Adapter<CreditEvaluationListAdapter.CreditEvaluationViewHolder> implements Filterable{

    private static final String TAG = CreditEvaluationListAdapter.class.getSimpleName();
    private List<DCreditOnlineApplicationCI.oDataEvaluationInfo> plLoanApp;
    private List<DCreditOnlineApplicationCI.oDataEvaluationInfo> plLoanApp1;
    private List<DCreditOnlineApplicationCI.oDataEvaluationInfo> plSchList;
    private List<DCreditOnlineApplicationCI.oDataEvaluationInfo> filteredList;
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;
    private String evaluation;
//    private final SearchFilter poSearch;

    public CreditEvaluationListAdapter(List<DCreditOnlineApplicationCI.oDataEvaluationInfo> plLoanApp, String val,OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.plLoanApp1 = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
        this.evaluation = val;
//        this.poSearch = new SearchFilter();
    }
    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }
    @NonNull
    @Override
    public CreditEvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.e(TAG, String.valueOf(viewType));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ci_list_item, parent, false);
        return new CreditEvaluationViewHolder(view,onApplicationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditEvaluationViewHolder holder, int position) {
        try {
            DCreditOnlineApplicationCI.oDataEvaluationInfo poLoan = plLoanApp.get(position);
            holder.lblTransNoxxx.setText("Transaction No.: " + poLoan.sTransNox);
            holder.lblClientName.setText(poLoan.sClientNm);
            holder.lblAppltnDate.setText(poLoan.dTransact);
            holder.lblBranch.setText(poLoan.sBranchNm);
            holder.lnRcmdtnx1.setVisibility(View.GONE);
            if (evaluation.equalsIgnoreCase("CI Evaluation History")) {
                holder.lnRcmdtnx1.setVisibility(View.VISIBLE);
                holder.lblRcmdtnx1.setText(poLoan.sRcmdtnx1);
                Log.e("sRcmdtnx1", poLoan.sRcmdtnx1);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

//    public SearchFilter getSearchFilter(){
//        return poSearch;
//    }
    public class CreditEvaluationViewHolder extends RecyclerView.ViewHolder{

        private TextView lblTransNoxxx;
        private TextView lblClientName;
        private TextView lblAppltnDate;
        private TextView lblBranch;
        private TextView lblRcmdtnx1;
        private LinearLayout lnRcmdtnx1;

        public CreditEvaluationViewHolder(@NonNull View itemView, OnApplicationClickListener onApplicationClickListener) {
            super(itemView);

            lblTransNoxxx = itemView.findViewById(R.id.lbl_ci_transNox);
            lblClientName = itemView.findViewById(R.id.lbl_ci_applicantName);
            lblAppltnDate = itemView.findViewById(R.id.lbl_ci_applicationDate);
            lblBranch = itemView.findViewById(R.id.lbl_ci_sBranch);
            lnRcmdtnx1 = itemView.findViewById(R.id.lnRcmdtnx1);
            lblRcmdtnx1 = itemView.findViewById(R.id.lbl_ci_sRcmdtnx1);


            itemView.setOnClickListener(v12 -> {
                if(CreditEvaluationListAdapter.this.onApplicationClickListener !=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        CreditEvaluationListAdapter.this.onApplicationClickListener.OnClick(lnPos, plLoanApp);
                    }
                }
            });
        }
    }


    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, List<DCreditOnlineApplicationCI.oDataEvaluationInfo> loanList);
    }

    public interface OnExportGOCASListener{
        void onExport(String GOCAS, String ClientName,String DateApplied);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty() || charString.length()==0 || charString == null) {
                    plLoanApp = plLoanApp1;

                } else {
                    filteredList = new ArrayList<>();
                    for (DCreditOnlineApplicationCI.oDataEvaluationInfo row : plLoanApp1) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or category in match match
                        if (row.sClientNm.toLowerCase().contains(charString.toLowerCase()) ||
                                row.sTransNox.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    plLoanApp = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = plLoanApp;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                plLoanApp = (ArrayList<DCreditOnlineApplicationCI.oDataEvaluationInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
