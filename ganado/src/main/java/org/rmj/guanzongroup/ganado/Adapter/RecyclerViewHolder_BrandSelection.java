package org.rmj.guanzongroup.ganado.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.ganado.R;

public class RecyclerViewHolder_BrandSelection extends RecyclerView.ViewHolder {
    public MaterialTextView item_brand;
    public ShapeableImageView item_brandImage;
    public View view;

    public RecyclerViewHolder_BrandSelection(@NonNull View itemView) {
        super(itemView);

        item_brand = itemView.findViewById(R.id.itemName0);
        item_brandImage = itemView.findViewById(R.id.imagebrand0);
        view = itemView;
    }
}
