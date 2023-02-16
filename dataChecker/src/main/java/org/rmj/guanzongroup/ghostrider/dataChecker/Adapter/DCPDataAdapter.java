/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dataChecker
 * Electronic Personnel Access Control Security System
 * project file created : 10/16/21, 2:15 PM
 * project file last modified : 10/16/21, 2:15 PM
 */

package org.rmj.guanzongroup.ghostrider.dataChecker.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.DCPData;
import org.rmj.guanzongroup.ghostrider.dataChecker.R;

import java.util.ArrayList;

public class DCPDataAdapter extends RecyclerView.Adapter<DCPDataAdapter.DataViewHolder> {

    private final ArrayList<DCPData> dcpData;

    public DCPDataAdapter(ArrayList<DCPData> dcpData){
        this.dcpData = dcpData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dcp_data, parent, false);
        return new DataViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        DCPData loData = dcpData.get(position);
        holder.lblAccntx.setText(loData.sAcctNmbr);
        holder.lblRemCde.setText(loData.sRemCodex);
        holder.lblCrdntx.setText("Latitude: " + loData.saLatitude + ", Longitude: " + loData.saLongitude);
    }

    @Override
    public int getItemCount() {
        return dcpData.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView lblAccntx,
                lblRemCde,
                lblCrdntx;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            lblAccntx = itemView.findViewById(R.id.lbl_accountNo);
            lblRemCde = itemView.findViewById(R.id.lbl_remarksCode);
            lblCrdntx = itemView.findViewById(R.id.lbl_coordinates);
        }
    }
}
