package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class MobileInfoAdapter extends RecyclerView.Adapter<MobileInfoAdapter.MobilenoxHolder> {

    private List<EMobileUpdate> mobileUpdates = new ArrayList<>();

    @NonNull
    @Override
    public MobilenoxHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MobilenoxHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MobilenoxHolder holder, int position) {
        EMobileUpdate current = mobileUpdates.get(position);
        if(current.getPrimaryx().equalsIgnoreCase("1")) {
            holder.tvPrimary.setVisibility(View.VISIBLE);
            holder.tvPrimary.setText("Primary");
        }
        holder.tvDetails.setText(current.getMobileNo());


    }

    @Override
    public int getItemCount() {
        return mobileUpdates.size();
    }

    public void setMobileNox(List<EMobileUpdate> mobileUpdates) {
        this.mobileUpdates = mobileUpdates;
        notifyDataSetChanged();
    }

    class MobilenoxHolder extends RecyclerView.ViewHolder {
        private TextView tvDetails;
        private TextView tvPrimary;

        public MobilenoxHolder(@NonNull View itemView) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvPrimary = itemView.findViewById(R.id.tvPrimary);
        }
    }

}
