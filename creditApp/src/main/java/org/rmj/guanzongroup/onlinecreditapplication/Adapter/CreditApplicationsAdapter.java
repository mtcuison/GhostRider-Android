package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.List;

public class CreditApplicationsAdapter extends RecyclerView.Adapter<CreditApplicationsAdapter.VHApplication> {

    private final List<ECreditApplication> poList;
    private final OnItemActionClickListener listener;

    public interface OnItemActionClickListener{
        void Resend(ECreditApplication creditapp);
        void OnPreview(ECreditApplication creditapp);
    }

    public CreditApplicationsAdapter(List<ECreditApplication> poList, OnItemActionClickListener listener) {
        this.poList = poList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VHApplication onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_credit_applications, parent, false);
        return new VHApplication(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHApplication holder, int position) {
        ECreditApplication loApp = poList.get(position);
        holder.lblName.setText(loApp.getClientNm());
        holder.lblDate.setText("Date created: " + FormatUIText.getParseDateTime(loApp.getDateCreatedx()));
        if(loApp.getSendStat().equalsIgnoreCase("0")) {
            holder.btnSubmit.setVisibility(View.VISIBLE);
            holder.lblStat.setText("");
        } else {
            holder.btnSubmit.setVisibility(View.GONE);
            holder.lblStat.setText("Sent");
        }

        holder.btnSubmit.setOnClickListener(v -> listener.Resend(loApp));
        holder.itemView.setOnClickListener(v -> listener.OnPreview(loApp));
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class VHApplication extends RecyclerView.ViewHolder{

        public TextView lblName, lblDate, lblStat;
        private ImageButton btnSubmit;

        public VHApplication(@NonNull View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lbl_applicantName);
            lblDate = itemView.findViewById(R.id.lbl_dateCreated);
            lblStat = itemView.findViewById(R.id.lbl_status);
            btnSubmit = itemView.findViewById(R.id.btn_submit);
        }
    }
}
