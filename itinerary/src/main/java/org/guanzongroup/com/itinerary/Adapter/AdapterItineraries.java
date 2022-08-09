package org.guanzongroup.com.itinerary.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.itinerary.R;
import org.rmj.g3appdriver.GRider.Database.Entities.EItinerary;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

import java.util.List;

public class AdapterItineraries extends RecyclerView.Adapter<AdapterItineraries.ItineraryViewHolder>{

    private final List<EItinerary> poList;

    public AdapterItineraries(List<EItinerary> poList) {
        this.poList = poList;
    }

    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_itinerary, parent, false);
        return new ItineraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {
        EItinerary loDetail = poList.get(position);
        holder.lblTransNox.setText(loDetail.getTransNox());
        holder.lblTransact.setText(FormatUIText.formatGOCasBirthdate(loDetail.getTransact()));
        holder.lblTimeStrt.setText(loDetail.getTimeFrom());
        holder.lblTimeEndx.setText(loDetail.getTimeThru());
        holder.lblLocation.setText(loDetail.getLocation());
        holder.txtPurpose.setText(loDetail.getRemarksx());
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class ItineraryViewHolder extends RecyclerView.ViewHolder{

        public TextView lblTransNox,
                lblTransact,
                lblTimeStrt,
                lblTimeEndx,
                lblLocation;

        public TextInputEditText txtPurpose;

        public ItineraryViewHolder(@NonNull View itemView) {
            super(itemView);

            lblTransNox = itemView.findViewById(R.id.lbl_transNox);
            lblTransact = itemView.findViewById(R.id.lbl_dTransact);
            lblTimeStrt = itemView.findViewById(R.id.lbl_timeStart);
            lblTimeEndx = itemView.findViewById(R.id.lbl_timeEnd);
            lblLocation = itemView.findViewById(R.id.lbl_location);
            txtPurpose = itemView.findViewById(R.id.txt_purpose);
        }
    }
}
