package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.ArrayList;
import java.util.List;

public class AddressInfoAdapter extends RecyclerView.Adapter<AddressInfoAdapter.AddressHolder> {

    private List<EAddressUpdate> addressUpdates = new ArrayList<>();

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new AddressHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        EAddressUpdate current = addressUpdates.get(position);
        holder.textViewTitle.setText(current.getHouseNox() +  ", " +
                current.getAddressx() +  ", " +
                current.getBrgyIDxx() +  ", " +
                current.getTownIDxx());
    }

    @Override
    public int getItemCount() {
        return addressUpdates.size();
    }

    public void setAddress(List<EAddressUpdate> addressUpdates) {
        this.addressUpdates = addressUpdates;
        notifyDataSetChanged();
    }

    class AddressHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewPriority;

        public AddressHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
        }
    }

}

