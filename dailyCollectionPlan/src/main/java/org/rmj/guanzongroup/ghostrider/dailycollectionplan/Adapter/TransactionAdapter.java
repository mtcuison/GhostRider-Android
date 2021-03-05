package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private static final String TAG = TransactionAdapter.class.getSimpleName();

    private List<DDCPCollectionDetail.CollectionDetail> plCollection;

    public TransactionAdapter(List<DDCPCollectionDetail.CollectionDetail> collectionDetails){
        this.plCollection = collectionDetails;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.detail = plCollection.get(position);

        holder.lblAccntNo.setText("Account No. : " + plCollection.get(position).sTransNox);
        holder.lblClntNme.setText(plCollection.get(position).xFullName);
        holder.lblRemCode.setText(DCP_Constants.getRemarksDescription(plCollection.get(position).sRemCodex));
        holder.lblEntryNo.setText("Entry No. : " + plCollection.get(position).nEntryNox);
        holder.lblRemarks.setText(plCollection.get(position).sRemarksx);
    }

    @Override
    public int getItemCount() {
        return plCollection.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        DDCPCollectionDetail.CollectionDetail detail;

        TextView lblAccntNo,
                 lblClntNme,
                 lblRemCode,
                 lblEntryNo,
                 lblRemarks;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            lblAccntNo = itemView.findViewById(R.id.lbl_AccountNo);
            lblClntNme = itemView.findViewById(R.id.lbl_clientNm);
            lblRemCode = itemView.findViewById(R.id.lbl_dcpRemarkCode);
            lblEntryNo = itemView.findViewById(R.id.lbl_dcpNox);
            lblRemarks = itemView.findViewById(R.id.lbl_dcpRemarks);
        }
    }
}
