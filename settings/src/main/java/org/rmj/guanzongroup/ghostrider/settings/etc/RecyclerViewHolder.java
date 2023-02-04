package org.rmj.guanzongroup.ghostrider.settings.etc;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.ghostrider.settings.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView tvCategory;
    View view;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvCategory = itemView.findViewById(R.id.lblLogCategory);
        view = itemView;
    }
}
