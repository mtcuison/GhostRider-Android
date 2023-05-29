package org.rmj.guanzongroup.ganado.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.rmj.guanzongroup.ganado.R;

public class RecyclerViewHolder_BrandSelection extends RecyclerView.ViewHolder {

    public ShapeableImageView item_brandimge;
    public MaterialTextView item_brandNm;
    public View view;


    public RecyclerViewHolder_BrandSelection(@NonNull View itemView) {
        super(itemView);

        item_brandimge = itemView.findViewById(R.id.imagebrand0);
        item_brandNm = itemView.findViewById(R.id.itemName0);
        view = itemView;

    }
    public void setImage(String image){
        try {
            JSONArray laJson = new JSONArray(image);
            String lsImgVal = laJson.getJSONObject(0).getString("sImageURL");
            Picasso.get().load(lsImgVal).placeholder(R.drawable.brand0)
                    .error(R.drawable.brand1).into(item_brandimge);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
