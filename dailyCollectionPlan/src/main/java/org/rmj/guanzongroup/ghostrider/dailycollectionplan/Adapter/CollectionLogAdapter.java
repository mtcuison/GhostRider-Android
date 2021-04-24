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
            holder.lblRemCode.setText(DCP_Constants.getRemarksDescription(detail.getRemCodex()));
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

        public CollectionViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            lblAccntNo = itemView.findViewById(R.id.lbl_AccountNo);
            lblClntNme = itemView.findViewById(R.id.lbl_clientNm);
            lblRemCode = itemView.findViewById(R.id.lbl_dcpRemarkCode);
            lblEntryNo = itemView.findViewById(R.id.lbl_dcpNox);
            lblRemarks = itemView.findViewById(R.id.lbl_dcpRemarks);

            itemView.setOnClickListener(v -> {
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
                detailsFilter = (collectionDetails);
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
