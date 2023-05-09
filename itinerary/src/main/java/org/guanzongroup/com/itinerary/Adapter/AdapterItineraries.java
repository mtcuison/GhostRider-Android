package org.guanzongroup.com.itinerary.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.itinerary.R;
import org.rmj.g3appdriver.dev.Database.Entities.EItinerary;
import org.rmj.g3appdriver.etc.FormatUIText;

import java.util.List;

public class AdapterItineraries extends RecyclerView.Adapter<AdapterItineraries.ItineraryViewHolder>{

    private final List<EItinerary> poList;
    private final OnClickListener mListener;

    public interface OnClickListener{
        void OnClick(EItinerary args);
    }

    public AdapterItineraries(List<EItinerary> poList,OnClickListener listener) {
        this.poList = poList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ItineraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_itinerary, parent, false);
        return new ItineraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItineraryViewHolder holder, int position) {
        try {
            EItinerary loDetail = poList.get(position);
            holder.lblLocation.setText("Location: " + loDetail.getLocation());
            holder.lblDateSchd.setText(FormatUIText.formatGOCasBirthdate(loDetail.getTransact()));
            String lsTripTme = FormatUIText.formatTime_HHMMSS_to_HHMMAA(loDetail.getTimeFrom()) + "\n To \n" + FormatUIText.formatTime_HHMMSS_to_HHMMAA(loDetail.getTimeThru());
            holder.lblTripTime.setText(lsTripTme);
            holder.lblPurposex.setText("Purpose: " + loDetail.getRemarksx());

            holder.view.setOnClickListener(view -> mListener.OnClick(loDetail));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class ItineraryViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView
                lblLocation,
                lblDateSchd,
                lblTripTime,
                lblPurposex;

        public ItineraryViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lblLocation = itemView.findViewById(R.id.lbl_location);
            lblDateSchd = itemView.findViewById(R.id.lbl_dateSched);
            lblTripTime = itemView.findViewById(R.id.lbl_tripTime);
            lblPurposex = itemView.findViewById(R.id.lbl_purpose);
        }
    }
}
