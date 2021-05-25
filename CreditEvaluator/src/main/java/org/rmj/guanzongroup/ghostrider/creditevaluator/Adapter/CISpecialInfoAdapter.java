/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/25/21 1:25 PM
 * project file last modified : 5/25/21 1:25 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CISpecialInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

import java.util.List;

public class CISpecialInfoAdapter extends RecyclerView.Adapter<CISpecialInfoAdapter.CiSpecialHolder>{
    private static final String TAG = CISpecialInfoAdapter.class.getSimpleName();
    private final List<CISpecialInfoModel> plCiSpecl;
    private final OnSaveResult mListener;

    public CISpecialInfoAdapter(List<CISpecialInfoModel> flCiSpecl, OnSaveResult fmListenr) {
        this.plCiSpecl = flCiSpecl;
        this.mListener = fmListenr;
    }

    @NonNull
    @Override
    public CiSpecialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ci_special_info, parent, false);
        return new CiSpecialHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CiSpecialHolder holder, int position) {
        CISpecialInfoModel loSpecial = plCiSpecl.get(position);
        holder.tvCiSpecl.setText(loSpecial.getCiSpecial());
    }

    @Override
    public int getItemCount() {
        return plCiSpecl.size();
    }

    public static class CiSpecialHolder extends RecyclerView.ViewHolder {
        private final TextView tvCiSpecl;
        private final TextInputEditText txtResult;

        public CiSpecialHolder(@NonNull View itemView, OnSaveResult listener) {
            super(itemView);

            tvCiSpecl = itemView.findViewById(R.id.lbl_ci_special);
            txtResult = itemView.findViewById(R.id.txt_ci_special_result);

            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                if(!txtResult.getText().toString().trim().isEmpty()) {
                    listener.onSuccessResult(position, txtResult.getText().toString().trim());
                } else {
                    listener.onFailedResult(position, "Field is empty.");
                }
            }
        }
    }

    public interface OnSaveResult {
        void onSuccessResult(int nPosition, String fsResultx);
        void onFailedResult(int nPosition, String fsError);
    }
}
