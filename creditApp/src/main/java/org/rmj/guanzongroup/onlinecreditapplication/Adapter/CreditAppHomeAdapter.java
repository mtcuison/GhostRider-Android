/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreditAppHomeAdapter extends RecyclerView.Adapter<CreditAppHomeAdapter.ClientInfoViewHolder> implements Filterable{

    private List<ECreditApplicantInfo> plLoanApp;
    private List<ECreditApplicantInfo> plLoanApp1;
    private List<ECreditApplicantInfo> plSchList;
    private List<ECreditApplicantInfo> filteredList;
    private CreditAppHomeAdapter.OnVoidApplicationListener onVoidApplicationListener;
    private CreditAppHomeAdapter.OnExportGOCASListener onExportGOCASListener;
    private CreditAppHomeAdapter.OnApplicationClickListener onApplicationClickListener;
    private RMcModel mcModel;
//    private final SearchFilter poSearch;

    public CreditAppHomeAdapter(Context context, List<ECreditApplicantInfo> plLoanApp, CreditAppHomeAdapter.OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.plLoanApp1 = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
        mcModel = new RMcModel((Application) context.getApplicationContext());
    }
    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }
    @NonNull
    @Override
    public CreditAppHomeAdapter.ClientInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_application, parent, false);
        return new CreditAppHomeAdapter.ClientInfoViewHolder(view,onApplicationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientInfoViewHolder holder, int position) {
        holder.poLoan = plLoanApp.get(position);
        holder.lblTransNoxxx.setText("TransNox : " + holder.poLoan.getTransNox());
        holder.lblClientName.setText(holder.poLoan.getClientNm());
        JSONObject obj = null;
        try {
            obj = new JSONObject(holder.poLoan.getPurchase());
            holder.lblAppltnType.setText(CreditAppConstants.CUSTOMER_TYPE[Integer.parseInt(obj.getString("cUnitAppl"))]);
            holder.lblUnitType.setText(CreditAppConstants.APPLICATION_TYPE[Integer.parseInt(obj.getString("cApplType"))]);
            holder.lblUnitBrand.setText(obj.getString("sUnitAppl"));
            holder.lblModelName.setText(mcModel.getModelName(obj.getString("sModelIDx")));
        } catch (JSONException e) {
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
    public class ClientInfoViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblAppltnType;
        TextView lblModelName;
        TextView lblUnitType;
        TextView lblUnitBrand;
        ECreditApplicantInfo poLoan;
        public ClientInfoViewHolder(@NonNull View itemView, CreditAppHomeAdapter.OnApplicationClickListener onApplicationClickListener) {
            super(itemView);

            lblTransNoxxx = itemView.findViewById(R.id.lbl_purchase_transNox);
            lblClientName = itemView.findViewById(R.id.lbl_purchase_applicantName);
            lblAppltnType = itemView.findViewById(R.id.lbl_purchaseAppType);
            lblUnitBrand = itemView.findViewById(R.id.lbl_purchaseBrand);
            lblModelName = itemView.findViewById(R.id.lbl_purchaseModel);
            lblUnitType = itemView.findViewById(R.id.lbl_purchaseUnitType);


            itemView.setOnClickListener(v12 -> {
                if(CreditAppHomeAdapter.this.onApplicationClickListener !=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        CreditAppHomeAdapter.this.onApplicationClickListener.OnClick(lnPos, poLoan);
                    }
                }
            });
        }
    }


    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, ECreditApplicantInfo loanList);
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
                    for (ECreditApplicantInfo row : plLoanApp1) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or category in match match
                        if (row.getClientNm().toLowerCase().contains(charString.toLowerCase())) {
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
                plLoanApp = (ArrayList<ECreditApplicantInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
