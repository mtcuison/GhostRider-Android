package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private static final String TAG = TransactionAdapter.class.getSimpleName();

    private List<EDCPCollectionDetail> plTransaction;

    public TransactionAdapter(List<EDCPCollectionDetail> plTransaction) {
        this.plTransaction = plTransaction;
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
        holder.detail = plTransaction.get(position);

        holder.lblAccntNo.setText("Account No. : " + plTransaction.get(position).getAcctNmbr());
        holder.lblClntNme.setText(plTransaction.get(position).getFullName());
        holder.lblRemCode.setText(getRemarksCode(plTransaction.get(position).getRemCodex()));
        holder.lblEntryNo.setText("Entry No. : " + plTransaction.get(position).getEntryNox());
        holder.lblRemarks.setText(plTransaction.get(position).getRemarksx());
    }

    @Override
    public int getItemCount() {
        return plTransaction.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder{
        EDCPCollectionDetail detail;

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

    public String getRemarksCode(String fsCode){
        switch (fsCode){
            case "PAY":
                return "Paid";
            case "PTP":
                return "Promise to Pay";
            case "CNA":
                return "Customer Not Around";
            case "FLA":
                return "For Legal Action";
            case "Car":
                return "Carnap";
            case "UNC":
                return "Uncooperative";
            case "MCs":
                return "Missing Customer";
            case "MUn":
                return "Missing Unit";
            case "MCU":
                return "Missing Client and Unit";
            case "LUn":
                return "Loan Unit";
            case "TA":
                return "Transferred/Assumed";
            case "FO":
                return "False Ownership";
            case "DNP":
                return "Did Not Pay";
            case "NV":
                return "Not Visited";
            case "OTH":
                return "Others";
        }
        return "";
    }
}
