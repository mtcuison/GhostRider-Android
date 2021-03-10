package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class MobileInfoAdapter_Log extends RecyclerView.Adapter<MobileInfoAdapter_Log.MobilenoxHolder>  {

    private List<DMobileRequest.CNAMobileInfo> mobileUpdates = new ArrayList<>();
    private final OnItemInfoClickListener mListener;

    public MobileInfoAdapter_Log(OnItemInfoClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MobilenoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_mobile_log, parent, false);
        return new MobilenoxHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MobilenoxHolder holder, int position) {
        DMobileRequest.CNAMobileInfo current = mobileUpdates.get(position);
        if(current.mobilePrimaryx.equalsIgnoreCase("1")) {
            holder.tvPrimary.setVisibility(View.VISIBLE);
            holder.tvPrimary.setText("Primary");
        }
        holder.tvDetails.setText(current.sMobileNo);
        holder.tvRemarks.setText(current.mobileRemarksx);
    }

    @Override
    public int getItemCount() {
        return mobileUpdates.size();
    }

    public void setMobileNox(List<DMobileRequest.CNAMobileInfo> mobileUpdates) {
        this.mobileUpdates = mobileUpdates;
        notifyDataSetChanged();
    }

    class MobilenoxHolder extends RecyclerView.ViewHolder {
        private TextView tvDetails;
        private TextView tvPrimary;
        private TextView tvRemarks;

        public MobilenoxHolder(@NonNull View itemView, OnItemInfoClickListener listener) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPrimary = itemView.findViewById(R.id.tvPrimary);
            tvRemarks = itemView.findViewById(R.id.tvRemarks);

            tvDetails.setOnClickListener(v ->{
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.OnMobileNoClick(tvDetails.getText().toString());
                }
            });
        }
    }

    public interface OnItemInfoClickListener {
        void OnDelete(int position);
        void OnMobileNoClick(String MobileNo);
    }
}
