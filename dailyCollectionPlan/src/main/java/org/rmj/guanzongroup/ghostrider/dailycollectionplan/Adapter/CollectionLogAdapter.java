package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class CollectionLogAdapter extends RecyclerView.Adapter<CollectionLogAdapter.CollectionViewHolder> {
    private static final String TAG = CollectionLogAdapter.class.getSimpleName();

    public interface OnItemClickListener{
        void OnClick(int position);
    }

    private final List<EDCPCollectionDetail> collectionDetails;
    private List<EDCPCollectionDetail> detailsFilter;
    private final CollectionSearch poSearch;
    private final OnItemClickListener mListener;

    public CollectionLogAdapter(List<EDCPCollectionDetail> collectionDetails, OnItemClickListener mListener) {
        this.collectionDetails = collectionDetails;
        this.detailsFilter = collectionDetails;
        this.poSearch = new CollectionSearch(CollectionLogAdapter.this);
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction, parent, false);
        return new CollectionViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        try {
            EDCPCollectionDetail detail = detailsFilter.get(position);
            holder.detail = detailsFilter.get(position);

            holder.lblAccntNo.setText("Account No. : " + detail.getAcctNmbr());
            holder.lblClntNme.setText(detail.getFullName());
            holder.lblRemCode.setText(detail.getRemCodex());

//            if(detail.getRemCodex().equalsIgnoreCase("PAY")) {
//                holder.lblRemCode.setText("Paid");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("PTP")) {
//                holder.lblRemCode.setText("Promise To Pay");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("CNA")) {
//                holder.lblRemCode.setText("Customer Not Around");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("Car")) {
//                holder.lblRemCode.setText("Carnap");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("UNC")) {
//                holder.lblRemCode.setText("Uncooperative");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("MCs")) {
//                holder.lblRemCode.setText("Missing Customer");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("MUn")) {
//                holder.lblRemCode.setText("Missing Unit");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("MCU")) {
//                holder.lblRemCode.setText("Missing Customer and Unit");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("DNP")) {
//                holder.lblRemCode.setText("Did Not Pay");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("NV")) {
//                holder.lblRemCode.setText("Not Visited");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("OTH")) {
//                holder.lblRemCode.setText("Others");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("FLA")) {
//                holder.lblRemCode.setText("For Legal Action");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("TA")) {
//                holder.lblRemCode.setText("Transferred/Assumed");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("FO")) {
//                holder.lblRemCode.setText("False Ownership");
//            }
//            else if(detail.getRemCodex().equalsIgnoreCase("LUn")) {
//                holder.lblRemCode.setText("Loan Unit");
//            }


            holder.lblEntryNo.setText("Entry No. : " + detail.getEntryNox());
            holder.lblRemarks.setText(detail.getRemarksx());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return detailsFilter.size();
    }

    public CollectionSearch getCollectionFilter(){
        return poSearch;
    }

    static class CollectionViewHolder extends RecyclerView.ViewHolder{
        EDCPCollectionDetail detail;

        TextView lblAccntNo,
                lblClntNme,
                lblRemCode,
                lblEntryNo,
                lblRemarks;

        MaterialButton btnSeeDetx;

        public CollectionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            lblAccntNo = itemView.findViewById(R.id.lbl_AccountNo);
            lblClntNme = itemView.findViewById(R.id.lbl_clientNm);
            lblRemCode = itemView.findViewById(R.id.lbl_dcpRemarkCode);
            lblEntryNo = itemView.findViewById(R.id.lbl_dcpNox);
            lblRemarks = itemView.findViewById(R.id.lbl_dcpRemarks);
            btnSeeDetx = itemView.findViewById(R.id.btn_see_details);

            btnSeeDetx.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    listener.OnClick(position);
                }
            });
        }
    }

    public class CollectionSearch extends Filter{

        private final CollectionLogAdapter poAdapter;

        public CollectionSearch(CollectionLogAdapter poAdapter) {
            super();
            this.poAdapter = poAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            final FilterResults results = new FilterResults();
            if(charSequence.length() == 0){
                detailsFilter.addAll(collectionDetails);
            } else {
                List<EDCPCollectionDetail> filterSearch = new ArrayList<>();
                for(EDCPCollectionDetail plan : collectionDetails){
                    String lsClientNme = plan.getFullName().toLowerCase();
                    if(lsClientNme.contains(charSequence.toString().toLowerCase())){
                        filterSearch.add(plan);
                    }
                }
                detailsFilter = filterSearch;
            }

            results.values = detailsFilter;
            results.count = detailsFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    }
}
