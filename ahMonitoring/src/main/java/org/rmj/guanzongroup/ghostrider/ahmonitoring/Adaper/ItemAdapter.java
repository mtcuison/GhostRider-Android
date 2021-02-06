package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final List<RandomItem> randomItems;
    private final OnItemClickListener mListener;

    public ItemAdapter(List<RandomItem> randomItems, OnItemClickListener mListener) {
        this.randomItems = randomItems;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_inventory, parent, false);
        return new ItemViewHolder(view, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        RandomItem item = randomItems.get(position);
        holder.item = item;
        holder.lblTransNox.setText("Transaction No. : " + item.getTransNox());
        holder.lblItemCode.setText("Item Code : " + item.getItemCode());
        holder.lblItemDesc.setText("Description : " + item.getItemDesc());
    }

    @Override
    public int getItemCount() {
        return randomItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public RandomItem item;
        public TextView lblTransNox;
        public TextView lblItemCode;
        public TextView lblItemDesc;

        public ItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            lblTransNox = itemView.findViewById(R.id.lbl_itemTransNox);
            lblItemCode = itemView.findViewById(R.id.lbl_itemCode);
            lblItemDesc = itemView.findViewById(R.id.lbl_itemDescription);

            itemView.setOnClickListener(view -> listener.OnClick(item.getTransNox(), item.getItemCode(), item.getItemDesc()));
        }
    }

    public interface OnItemClickListener{
        void OnClick(String TransNox, String ItemCode, String Description);
    }
}
