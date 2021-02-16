package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class AddressInfoAdapter extends RecyclerView.Adapter<AddressInfoAdapter.AddressHolder> {

    private List<DAddressRequest.CustomerAddressInfo> addressUpdates = new ArrayList<>();
    private final OnDeleteInfoListener mListener;

    public AddressInfoAdapter(OnDeleteInfoListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AddressHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        DAddressRequest.CustomerAddressInfo current = addressUpdates.get(position);
        holder.tvDetails.setText(current.sTownName +  ", " + current.sProvName);
        holder.tvAddress.setText(current.sHouseNox + " " +
                current.sAddressx + ", " +
                current.sBrgyName);

        holder.transNox = current.sTransNox;
        /*holder.icDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poCna.deleteAddress(holder.transNox);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return addressUpdates.size();
    }

    public void setAddress(List<DAddressRequest.CustomerAddressInfo> addressUpdates) {
        this.addressUpdates = addressUpdates;
        notifyDataSetChanged();
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        private TextView tvDetails;
        private TextView tvAddress;
        private ImageView icDelete;
        private String transNox;

        public AddressHolder(@NonNull View itemView, OnDeleteInfoListener listener) {
            super(itemView);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            icDelete = itemView.findViewById(R.id.icDelete);

            icDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    listener.OnDelete(position);
                }
            });
        }
    }

    public interface OnDeleteInfoListener{
        void OnDelete(int position);
    }

}

