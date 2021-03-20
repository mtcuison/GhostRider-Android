package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.ghostrider.griderscanner.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClientInfoAdapter extends RecyclerView.Adapter<ClientInfoAdapter.ClientInfoViewHolder> {

    private List<LoanApplication> plLoanApp;
    private List<LoanApplication> plSchList;
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;

    public ClientInfoAdapter(List<LoanApplication> plLoanApp, OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
    }
    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }
    @NonNull
    @Override
    public ClientInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_doc_scan, parent, false);
        return new ClientInfoViewHolder(view,onApplicationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientInfoViewHolder holder, int position) {
        LoanApplication poLoan = plLoanApp.get(position);


        holder.lblTransNoxxx.setText("TransNox. :"+poLoan.getsTransNox());
        holder.lblClientName.setText(poLoan.getsCompnyNm());
        holder.lblAppltnDate.setText(poLoan.getdTransact());
        holder.lblStatus.setText(poLoan.getTransactionStatus());
        holder.lblAccntTern.setText(poLoan.getnAcctTerm());
        holder.lblModelName.setText(poLoan.getsModelNme());
        holder.lblMobileNo.setText(poLoan.getsMobileNo());
//        holder.lblApplResult.setText(poLoan.getTransactionStatus());
//
//        holder.btnVoid.setVisibility(poLoan.getVoidStatus());
//
//        holder.lblSentStatus.setVisibility(poLoan.getSendStatus());
    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

    public class ClientInfoViewHolder extends RecyclerView.ViewHolder{


        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblAppltnDate;
        TextView lblModelName;
        TextView lblAccntTern;
        TextView lblMobileNo;
        TextView lblStatus;
        ImageButton btnVoid;

        public ClientInfoViewHolder(@NonNull View itemView, OnApplicationClickListener onApplicationClickListener) {
            super(itemView);

            lblTransNoxxx = itemView.findViewById(R.id.lbl_list_transNox);
            lblClientName = itemView.findViewById(R.id.lbl_list_applicantName);
            lblAppltnDate = itemView.findViewById(R.id.lbl_list_applicationDate);
            lblStatus = itemView.findViewById(R.id.lbl_list_applicationWithCI);
            lblModelName = itemView.findViewById(R.id.lbl_modelName);
            lblAccntTern = itemView.findViewById(R.id.lbl_accntTerm);
            lblMobileNo = itemView.findViewById(R.id.lbl_mobileNo);


            itemView.setOnClickListener(v12 -> {
                if(ClientInfoAdapter.this.onApplicationClickListener !=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        ClientInfoAdapter.this.onApplicationClickListener.OnClick(lnPos, plLoanApp);
                    }
                }
            });
        }
    }


    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, List<LoanApplication> loanList);
    }

    public interface OnExportGOCASListener{
        void onExport(String GOCAS, String ClientName,String DateApplied);
    }

}