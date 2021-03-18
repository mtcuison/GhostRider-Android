package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.List;

public class LoanHistoryAdapter extends RecyclerView.Adapter<LoanHistoryAdapter.LoanViewHolder> {

    private List<LoanApplication> plLoanApp;
    private List<LoanApplication> plSchList;
    private final LoanApplicantListActionListener mListener;

    public LoanHistoryAdapter(List<LoanApplication> plLoanApp, LoanApplicantListActionListener listener) {
        this.plLoanApp = plLoanApp;
        this.mListener = listener;
    }

    public interface LoanApplicantListActionListener{
        void OnExport(String TransNox);
        void OnUpdate(String TransNox);
        void OnDelete(String TransNox);
        void OnPreview(String TransNox);
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applications, parent, false);
        return new LoanViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        LoanApplication poLoan = plLoanApp.get(position);
        holder.poLoan = poLoan;
        holder.lblGoCasNoxxx.setText("GOCas No. :"+poLoan.getGOCasNumber());
        holder.lblTransNoxxx.setText(poLoan.getTransNox());
        holder.lblClientName.setText(poLoan.getClientName());
        holder.lblAppltnDate.setText(poLoan.getDateTransact());
        holder.lblApplResult.setText(poLoan.getTransactionStatus());
        holder.lblDateApprov.setText(poLoan.getDateApproved());
        holder.btnVoid.setVisibility(poLoan.getVoidStatus());
        holder.btnExpt.setVisibility(poLoan.getExportStatus());
        holder.lblGoCasNoxxx.setText(poLoan.getGOCasNumber());
        holder.lblClientName.setText(poLoan.getClientName());
        holder.lblTransNoxxx.setText(poLoan.getTransNox());
        holder.lblAppltnDate.setText(poLoan.getDateTransact());
        holder.lblApplResult.setText(poLoan.getTransactionStatus());
        holder.lblDateApprov.setText(poLoan.getDateApproved());
        holder.lblSentStatus.setVisibility(poLoan.getSendStatus());
    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

    public static class LoanViewHolder extends RecyclerView.ViewHolder{

        LoanApplication poLoan;
        TextView lblGoCasNoxxx;
        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblAppltnDate;
        TextView lblApplResult;
        TextView lblDateApprov;
        TextView lblDateSentxx;
        TextView lblSentStatus;
        ImageButton btnVoid;
        MaterialButton btnExpt;
        MaterialButton btnUpdt;

        public LoanViewHolder(@NonNull View itemView, LoanApplicantListActionListener listener) {
            super(itemView);

            lblGoCasNoxxx = itemView.findViewById(R.id.lbl_listLog_GoCasNo);
            lblTransNoxxx = itemView.findViewById(R.id.lbl_listLog_applicationTransNo);
            lblClientName = itemView.findViewById(R.id.lbl_listLog_applicantName);
            lblAppltnDate = itemView.findViewById(R.id.lbl_listLog_applicationDate);
            lblApplResult = itemView.findViewById(R.id.lbl_listLog_applicationWithCI);
            lblDateApprov = itemView.findViewById(R.id.lbl_listLog_approvedDate);
            lblDateSentxx = itemView.findViewById(R.id.lbl_listLog_dateSent);
            lblSentStatus = itemView.findViewById(R.id.lbl_applicationSent);
            btnVoid = itemView.findViewById(R.id.btn_deleteApplication);
            btnUpdt = itemView.findViewById(R.id.btn_applicationUpdate);
            btnExpt = itemView.findViewById(R.id.btn_applicationExport);

            btnVoid.setOnClickListener(v1 -> {
                int lnPos = getAdapterPosition();
                if(lnPos != RecyclerView.NO_POSITION){
                    listener.OnDelete(poLoan.getTransNox());
                }
            });

            btnUpdt.setOnClickListener(v -> {
                int lnPos = getAdapterPosition();
                if(lnPos != RecyclerView.NO_POSITION){
                    listener.OnUpdate(poLoan.getTransNox());
                }
            });

            btnExpt.setOnClickListener(v12 -> {
                int lnPos = getAdapterPosition();
                if(lnPos != RecyclerView.NO_POSITION){
                    listener.OnExport(poLoan.getTransNox());
                }
            });

            itemView.setOnClickListener(v12 -> {
                int lnPos = getAdapterPosition();
                if(lnPos != RecyclerView.NO_POSITION){
                    listener.OnPreview(poLoan.getTransNox());
                }
            });
        }
    }
}
