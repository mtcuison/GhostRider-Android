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

import com.google.android.material.button.MaterialButton;

import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class DependentAdapter extends RecyclerView.Adapter<DependentAdapter.ItemViewHolder>{

    List<DependentsInfoModel> infoModels;

    public DependentAdapter(List<DependentsInfoModel> dependentInfoModels) {
        this.infoModels = dependentInfoModels;
    }


    @NonNull
    @Override
    public DependentAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_dependent, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DependentAdapter.ItemViewHolder holder, int position) {
        DependentsInfoModel dependentsInfoModel = infoModels.get(position);

        holder.lblDpdName.setText("Fullname. : " + dependentsInfoModel.getDpdFullName());
        //holder.lblDpdRelation.setText("Address : " + dependentsInfoModel.getDpdRlationship() + " Age: " + dependentsInfoModel.getDpdAge());

    }

    @Override
    public int getItemCount() {
        return infoModels.size();
    }

//    @Override
//    public int getItemCount() {
//        return otherInfoModels.size();
//    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView lblDpdName;
        private TextView lblDpdRelation;
        private MaterialButton btnRemove;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lblDpdName = itemView.findViewById(R.id.lbl_list_cap_dependent);
            btnRemove = itemView.findViewById(R.id.btn_list_dpdRemove);
        }
    }


}
