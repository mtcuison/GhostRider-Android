/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

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
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalReferenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PersonalReferencesAdapter extends RecyclerView.Adapter<PersonalReferencesAdapter.ItemViewHolder>{

    List<PersonalReferenceInfoModel> referenceInfoModels;

    public PersonalReferencesAdapter(List<PersonalReferenceInfoModel> referenceInfoModels) {
        this.referenceInfoModels = referenceInfoModels;
    }


    @NonNull
    @Override
    public PersonalReferencesAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_preferences, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PersonalReferencesAdapter.ItemViewHolder holder, int position) {
        int lnSizexxx = referenceInfoModels.size();
        int lnCurrent = position+1;
        PersonalReferenceInfoModel reference = referenceInfoModels.get(position);

        holder.lblRefName.setText("Fullname. : " + reference.getFullname());
        holder.lblRefTown.setText(reference.getAddress1() + ", " + reference.getTownCity());
        holder.lblRefContact.setText("Contact No : " + reference.getContactN());
        if(lnCurrent!=lnSizexxx){
            holder.vDivider.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return referenceInfoModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lblRefName;
        private TextView lblRefAddres;
        private TextView lblRefTown;
        private TextView lblRefContact;
        private View vDivider;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblRefName = itemView.findViewById(R.id.lbl_itemRefName);
            lblRefAddres = itemView.findViewById(R.id.lbl_itemRefAddress);
            lblRefTown = itemView.findViewById(R.id.lbl_itemRefTown);
            lblRefContact = itemView.findViewById(R.id.lbl_itemRefContactN);
            vDivider = itemView.findViewById(R.id.view_divider);
        }
    }


}
