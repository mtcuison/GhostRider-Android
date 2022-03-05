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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.Model.CreditEvaluationModel;
import org.guanzongroup.com.creditevaluation.R;

import java.util.ArrayList;
import java.util.List;

public class CreditEvaluationListAdapter extends RecyclerView.Adapter<CreditEvaluationListAdapter.CreditEvaluationViewHolder> implements Filterable{

    private static final String TAG = CreditEvaluationListAdapter.class.getSimpleName();
    private List<CreditEvaluationModel> plLoanApp;
    private List<CreditEvaluationModel> plLoanApp1;
    private List<CreditEvaluationModel> plSchList;
    private List<CreditEvaluationModel> filteredList;
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;

//    private final SearchFilter poSearch;

    public CreditEvaluationListAdapter(List<CreditEvaluationModel> plLoanApp, OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.plLoanApp1 = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_app_list, parent, false);
        return new CreditEvaluationViewHolder(view,onApplicationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditEvaluationViewHolder holder, int position) {
        CreditEvaluationModel poLoan = plLoanApp.get(position);

        holder.lblTransNoxxx.setText("Transaction No.: " + poLoan.getsTransNox());
        holder.lblClientName.setText(poLoan.getsCompnyNm());
        holder.lblAppltnDate.setText(poLoan.getdTransact());
        holder.lblMobileNo.setText(poLoan.getsMobileNo());
        holder.lblAccountTerm.setText(poLoan.getnAcctTerm() + " Month/s");
        holder.lblModelName.setText(poLoan.getsModelNme());
        holder.lblModelName.setText(poLoan.getsModelNme());
        holder.lblDownPayment.setText("₱" + poLoan.getnDownPaym());

    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

//    public SearchFilter getSearchFilter(){
//        return poSearch;
//    }
    public class CreditEvaluationViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblAppltnDate;
        TextView lblAccountTerm;
        TextView lblMobileNo;

        TextView lblModelName;
        TextView lblDownPayment;

        public CreditEvaluationViewHolder(@NonNull View itemView, OnApplicationClickListener onApplicationClickListener) {
            super(itemView);

            lblTransNoxxx = itemView.findViewById(R.id.lbl_ci_transNox);
            lblClientName = itemView.findViewById(R.id.lbl_ci_applicantName);
            lblAppltnDate = itemView.findViewById(R.id.lbl_ci_applicationDate);
            lblAccountTerm = itemView.findViewById(R.id.lbl_ci_accountTerm);
            lblMobileNo = itemView.findViewById(R.id.lbl_ci_mobileNo);
            lblModelName = itemView.findViewById(R.id.lbl_ci_modelName);
            lblDownPayment = itemView.findViewById(R.id.lbl_ci_downPayment);


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
        void OnClick(int position, List<CreditEvaluationModel> loanList);
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
                    for (CreditEvaluationModel row : plLoanApp1) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or category in match match
                        if (row.getsCompnyNm().toLowerCase().contains(charString.toLowerCase())) {
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
                plLoanApp = (ArrayList<CreditEvaluationModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
