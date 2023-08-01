/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/16/21, 10:47 AM
 * project file last modified : 9/16/21, 10:47 AM
 */

package org.rmj.guanzongroup.ganado.Adapter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.Entities.EMCColor;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.g3appdriver.lib.Ganado.Obj.Ganado;
import org.rmj.g3appdriver.lib.Ganado.Obj.ProductInquiry;
import org.rmj.g3appdriver.lib.Ganado.model.GConstants;
import org.rmj.g3appdriver.lib.Ganado.pojo.ClientInfo;
import org.rmj.g3appdriver.lib.Panalo.Panalo;
import org.rmj.guanzongroup.ganado.R;

import java.util.List;

public class InquiryListAdapter extends RecyclerView.Adapter<InquiryListAdapter.ApplicationViewHolder> {

    private List<EGanadoOnline> poModel;
    private ProductInquiry poApp;
    private OnModelClickListener listener;
    private boolean forViewing = false;

    private Application mContex;
    public interface OnModelClickListener{
        void OnClick(String TransNox);
    }

    public InquiryListAdapter(Application mContex, List<EGanadoOnline> poModel, OnModelClickListener listener) {
        this.poModel = poModel;
        this.mContex = mContex;
        this.listener = listener;
        this.poApp = new ProductInquiry(mContex);
    }


    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inquiry_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        try {
            EGanadoOnline loModel = poModel.get(position);
            holder.itemName01.setText(loModel.getClientNm());
            holder.itemName02.setText(loModel.getSendStat());
            JSONObject object = new JSONObject(loModel.getClntInfo());
            holder.itemName03.setText(object.getString("sMobileNo"));
            JSONObject object1 = new JSONObject(loModel.getProdInfo());
            DGanadoOnline.McInfo eganado = poApp.GetMCInfo(object1.getString("sModelIDx"),object1.getString("sBrandIDx"),object1.getString("sColorIDx"));
            holder.itemName04.setText(eganado.ModelNme + " (" + eganado.ColorNme + ")");
            holder.itemName05.setText(GConstants.PAYMENT_FORM[Integer.parseInt(loModel.getPaymForm())]);
            holder.itemName06.setText(loModel.getTranStat());
            holder.itemView.setOnClickListener(v -> {
                if(listener != null){
                    listener.OnClick(loModel.getTransNox());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return poModel.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder{

        MaterialTextView itemName01, itemName02, itemName03, itemName04,itemName05,itemName06;

        LinearLayout lnStatus;

        ShapeableImageView imgType;

        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            itemName01 = view.findViewById(R.id.lbl_clientName);
            itemName02 = view.findViewById(R.id.lbl_status);
            itemName03 = view.findViewById(R.id.lbl_mobileNo);
            itemName04 = view.findViewById(R.id.lbl_modelDesc);
            itemName05 = view.findViewById(R.id.lbl_InquiryType);
            itemName06 = view.findViewById(R.id.lbl_recorStats);
        }
    }
}
