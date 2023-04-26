/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.Etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.ESCA_Request;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;

import java.util.List;

public class AdapterApprovalAuth extends RecyclerView.Adapter<AdapterApprovalAuth.SCAViewHolder> {

    private List<ESCA_Request> laScaList;
    private OnAuthItemClickListener poListener;

    public AdapterApprovalAuth(List<ESCA_Request> faScaList, OnAuthItemClickListener foListener) {
        this.laScaList = faScaList;
        this.poListener = foListener;
    }

    @NonNull
    @Override
    public SCAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_auth, parent, false);
        return new SCAViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SCAViewHolder holder, int position) {
        ESCA_Request loRequest = laScaList.get(position);
        String lsTitle = loRequest.getSCATitle();
        holder.btnScaAuth.setText(lsTitle);
        holder.btnScaAuth.setOnClickListener(view -> poListener.OnClick(loRequest.getSCACodex(), loRequest.getSCATypex()));
    }

    @Override
    public int getItemCount() {
        return laScaList.size();
    }

    public static class SCAViewHolder extends RecyclerView.ViewHolder{

        MaterialButton btnScaAuth;

        public SCAViewHolder(@NonNull View itemView) {
            super(itemView);
            btnScaAuth = itemView.findViewById(R.id.btn_listItem);
        }
    }

    public interface OnAuthItemClickListener{
        void OnClick(String SystemCode, String SCAType);
    }
}
