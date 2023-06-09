package org.rmj.guanzongroup.ganado.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.guanzongroup.ganado.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_BrandSelection extends RecyclerView.Adapter<RecyclerViewHolder_BrandSelection> {
    private List<EMcBrand> paBrandFilter;
    private final OnBrandSelectListener listener;

    public interface OnBrandSelectListener {
        void OnSelect(String BrandCode, String BranchName);
    }

    public RecyclerViewAdapter_BrandSelection(List<EMcBrand> paBrand, OnBrandSelectListener listener) {
        this.paBrandFilter = paBrand;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_BrandSelection onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcbrandgrid_item, parent, false);
        return new RecyclerViewHolder_BrandSelection(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_BrandSelection holder, int position) {
        EMcBrand loBranch = paBrandFilter.get(position);
        holder.item_brandImage.setImageResource(getBrandImageResource(loBranch.getBrandIDx()));
        holder.item_brand.setText(loBranch.getBrandNme());
//        holder.itemView.setOnClickListener(v -> listener.OnSelect(loBranch.getBrandIDx(), loBranch.getBrandNme()));
        holder.view.setOnClickListener(v -> {
            if (listener != null) {
                listener.OnSelect(loBranch.getBrandIDx(), loBranch.getBrandNme());
            }
        });
    }

    @Override
    public int getItemCount() {
        return paBrandFilter.size();
    }

    private int getBrandImageResource(String brandIndex) {
        switch (brandIndex) {
            case "M0W1001":
                return R.drawable.img_mc_honda; // Replace with your actual image resource
            case "M0W1002":
                return R.drawable.img_mc_suzuki; // Replace with your actual image resource
            case "M0W1003":
                return R.drawable.img_mc_yamaha; // Replace with your actual image resource
            case "M0W1009":
                return R.drawable.img_mc_kawasaki; // Replace with your actual image resource
            default:
                return R.drawable.ic_image_place_holder; // Replace with your default image resource
        }

    }
}
