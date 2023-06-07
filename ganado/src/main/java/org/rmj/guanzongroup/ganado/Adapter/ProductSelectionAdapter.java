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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.MCHondaImages;
import org.rmj.guanzongroup.ganado.ViewModel.MCKawasakiImages;
import org.rmj.guanzongroup.ganado.ViewModel.MCSuzukiImages;
import org.rmj.guanzongroup.ganado.ViewModel.MCYamahaImages;

import java.util.List;

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.ApplicationViewHolder> {

    private List<EMcModel> poModel;
    private OnModelClickListener listener;


    public interface OnModelClickListener {
        void OnClick(String ModelID, String BrandID);
    }

    public ProductSelectionAdapter(List<EMcModel> poModel, OnModelClickListener listener) {
        this.poModel = poModel;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcmodelgrid_item, parent, false);
        return new ApplicationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {

        String lsImgUrl = "";
        EMcModel loModel = poModel.get(position);
        holder.itemName.setText(loModel.getModelNme());
//        holder.setImage(loModel.getModelIDx(),loModel.getBrandIDx());
        try {

            switch (loModel.getBrandIDx()) {

                case "M0W1001"://honda
                    lsImgUrl = MCHondaImages.getModelImageResource(loModel.getModelIDx());
                    break;
                case "M0W1002"://suzuki
                    lsImgUrl = MCSuzukiImages.getModelImageResource(loModel.getModelIDx());
                    break;
                case "M0W1003": // yamaha
                    lsImgUrl = MCYamahaImages.getModelImageResource(loModel.getModelIDx());
                    break;
                case "M0W1009": // kawasaki
                    lsImgUrl = MCKawasakiImages.getModelImageResource(loModel.getModelIDx());
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lsImgUrl != "") {
            Picasso.get().load(lsImgUrl).placeholder(R.drawable.ganado_gradient)
                    .error(R.drawable.ganado_gradient).into(holder.itemImg);
            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.OnClick(loModel.getModelIDx(), loModel.getBrandIDx());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return poModel.size();
    }

    public static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView itemName;

        LinearLayout lnStatus;

        ShapeableImageView itemImg;


        public ApplicationViewHolder(@NonNull View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemImg = view.findViewById(R.id.imagemodel0);
        }

//        public void setImage(String ModelName, String BrandID){
//            String lsImgVal ="";
//            try {
//                switch (BrandID) {
//                    case "M0W1001":
//                        lsImgVal = HondaImages.getModelImageResource(ModelName);
//                        break;
//                    case "M0W1002":
//                        lsImgVal = HondaImages.getModelImageResource(ModelName);
//                        break;
//                    case "M0W1003":
//                        lsImgVal = HondaImages.getModelImageResource(ModelName);
//                        break;
//                    case "M0W1009":
//                        lsImgVal = HondaImages.getModelImageResource(ModelName);
//                        break;
//                }
//
//
//
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//    }

    }
}

