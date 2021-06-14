/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office 
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/10/21 1:06 PM
 * project file last modified : 6/10/21 1:06 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.ArrayList;
import java.util.List;

public class CashCountLogAdapter extends RecyclerView.Adapter<CashCountLogAdapter.CreditEvaluationViewHolder> implements Filterable {

    private static final String TAG = CashCountLogAdapter.class.getSimpleName();
    private List<CashCountInfoModel> plCashCount;
    private List<CashCountInfoModel> plCashCount1;
    private List<CashCountInfoModel> plSchList;
    private List<CashCountInfoModel> filteredList;
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;

//    private final SearchFilter poSearch;

    public CashCountLogAdapter(List<CashCountInfoModel> plCashCount, OnApplicationClickListener onApplicationClickListener) {
        this.plCashCount = plCashCount;
        this.plCashCount1 = plCashCount;
        this.onApplicationClickListener = onApplicationClickListener;
//        this.poSearch = new SearchFilter();
    }
    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }
    @NonNull
    @Override
    public CashCountLogAdapter.CreditEvaluationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cash_count_log, parent, false);
        return new CashCountLogAdapter.CreditEvaluationViewHolder(view,onApplicationClickListener);
    }

        @Override
        public void onBindViewHolder(@NonNull CreditEvaluationViewHolder holder, int position) {
            CashCountInfoModel poCount = plCashCount.get(position);

            holder.lblTransNoxxx.setText("Transaction No.: " + poCount.getTransNox());
            holder.lblRqstName.setText(poCount.getReqstdNm());
            holder.lblSendDate.setText(poCount.getTranDate());
        }

        @Override
        public int getItemCount() {
            return plCashCount.size();
        }

//    public SearchFilter getSearchFilter(){
//        return poSearch;
//    }
public class CreditEvaluationViewHolder extends RecyclerView.ViewHolder{

    TextView lblTransNoxxx;
    TextView lblRqstName;
    TextView lblSendDate;
    TextView lblStats;

    public CreditEvaluationViewHolder(@NonNull View itemView, CashCountLogAdapter.OnApplicationClickListener onApplicationClickListener) {
        super(itemView);

        lblTransNoxxx = itemView.findViewById(R.id.lbl_list_ccTransNox);
        lblRqstName = itemView.findViewById(R.id.lbl_list_ccRequestName);
        lblSendDate = itemView.findViewById(R.id.lbl_list_ccDateSent);
        lblStats = itemView.findViewById(R.id.lbl_list_ccSentStatus);


        itemView.setOnClickListener(v12 -> {
            if(CashCountLogAdapter.this.onApplicationClickListener !=null){
                int lnPos = getAdapterPosition();
                if(lnPos != RecyclerView.NO_POSITION){
                    CashCountLogAdapter.this.onApplicationClickListener.OnClick(lnPos, plCashCount);
                }
            }
        });
    }
}


public interface OnVoidApplicationListener{
    void OnVoid(int position, String TransNox);
}

public interface OnApplicationClickListener{
    void OnClick(int position, List<CashCountInfoModel> loanList);
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
                    plCashCount = plCashCount1;

                } else {
                    filteredList = new ArrayList<>();
                    for (CashCountInfoModel row : plCashCount1) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or category in match match
                        if (row.getReqstdNm().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    plCashCount = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = plCashCount;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                plCashCount = (ArrayList<CashCountInfoModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
