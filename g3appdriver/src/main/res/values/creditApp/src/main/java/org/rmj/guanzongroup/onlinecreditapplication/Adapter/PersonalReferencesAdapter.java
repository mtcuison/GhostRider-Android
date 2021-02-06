package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalReferencesAdapter extends RecyclerView.Adapter<PersonalReferencesAdapter.ItemViewHolder>{

    List<OtherInfoModel> otherInfoModels;

    public PersonalReferencesAdapter(List<OtherInfoModel> otherInfoModels) {
        this.otherInfoModels = otherInfoModels;
    }


    @NonNull
    @Override
    public PersonalReferencesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_preferences, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalReferencesAdapter.ItemViewHolder holder, int position) {
        OtherInfoModel otherInfoModel = otherInfoModels.get(position);

        holder.lblRefName.setText("Fullname. : " + otherInfoModel.getFullname());
        holder.lblRefAddres.setText("Address : " + otherInfoModel.getAddress1());
        holder.lblRefTown.setText("Town / City : " + otherInfoModel.getTownCity());
        holder.lblRefContact.setText("Contanct No : " + otherInfoModel.getContactN());
    }

    @Override
    public int getItemCount() {
        return otherInfoModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lblRefName;
        private TextView lblRefAddres;
        private TextView lblRefTown;
        private TextView lblRefContact;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblRefName = itemView.findViewById(R.id.lbl_itemRefName);
            lblRefAddres = itemView.findViewById(R.id.lbl_itemRefAddress);
            lblRefTown = itemView.findViewById(R.id.lbl_itemRefTown);
            lblRefContact = itemView.findViewById(R.id.lbl_itemRefContactN);
        }
    }


}
