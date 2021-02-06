package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

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
    private OnVoidApplicationListener onVoidApplicationListener;
    private OnExportGOCASListener onExportGOCASListener;
    private OnApplicationClickListener onApplicationClickListener;

    public LoanHistoryAdapter(List<LoanApplication> plLoanApp) {
        this.plLoanApp = plLoanApp;
    }

    @NonNull
    @Override
    public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_applications, parent, false);
        return new LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanViewHolder holder, int position) {
        LoanApplication poLoan = plLoanApp.get(position);
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

    public class LoanViewHolder extends RecyclerView.ViewHolder{

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

        public LoanViewHolder(@NonNull View itemView) {
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

            /*btnVoid.setOnClickListener(v1 -> {
                if(onVoidApplicationListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onVoidApplicationListener.OnVoid(lnPos, applicantSearchFilter.get(lnPos).getsTransNox());
                    }
                }
            });*/

            /*btnExpt.setOnClickListener(v12 -> {
                if(onExportGOCASListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onExportGOCASListener.onExport(applicantSearchFilter.get(lnPos).getDetlInfox(),
                                applicantSearchFilter.get(lnPos).getsTransNox(),
                                applicantSearchFilter.get(lnPos).getGoCasNoxx());
                    }
                }
            });*/

            itemView.setOnClickListener(v12 -> {
                if(onApplicationClickListener!=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        onApplicationClickListener.OnClick(lnPos, plLoanApp.get(lnPos).getTransNox());
                    }
                }
            });
        }
    }


    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, String TransNox);
    }

    public interface OnExportGOCASListener{
        void onExport(String GOCAS, String ClientName,String DateApplied);
    }

}
