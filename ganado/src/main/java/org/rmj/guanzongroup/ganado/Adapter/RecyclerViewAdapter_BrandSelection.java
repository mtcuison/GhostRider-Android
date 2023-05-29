package org.rmj.guanzongroup.ganado.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.etc.CashFormatter;
import org.rmj.guanzongroup.ganado.R;

import java.util.List;

public class RecyclerViewAdapter_BrandSelection extends RecyclerView.Adapter<RecyclerViewAdapter_BrandSelection.ViewHolderItem> {

    private final List<DMcBrand.oProduct> poProdcts;
    private final OnItemClick poCallBck;

    public RecyclerViewAdapter_BrandSelection(List<DMcBrand.oProduct> foProdcts, OnItemClick foCallBck){
        this.poProdcts = foProdcts;
        this.poCallBck = foCallBck;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.mcbrandgrid_item, parent, false);
        return new ViewHolderItem(viewItem, poCallBck);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        try {
            DMcBrand.oProduct loBrand = poProdcts.get(position);
            holder.txtBrandNm.setText(loBrand.xBrandNme);
            holder.setImage(loBrand.sImagesxx);
            // TODO: Set product image url ~> Picasso.get().load(stringUrl).into(holder.imgProdct);
            // TODO: Display promo banner if there is any (8:1 aspect ratio)
//            boolean isThereAPromoForThisItem = true;
//            if(isThereAPromoForThisItem) {
//                holder.imgPromox.setVisibility(View.VISIBLE);
//                Picasso.get().load(stringUrl).into(holder.imgPromox);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poProdcts.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder{

        public String sBrandIDxx = "";
        public ImageView imgBrand;
        public TextView txtBrandNm;

        public ViewHolderItem(@NonNull View itemView, OnItemClick foCallBck) {
            super(itemView);
            imgBrand = itemView.findViewById(R.id.imagebrand0);
            txtBrandNm = itemView.findViewById(R.id.itemName0);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION) {
                    foCallBck.onClick(sBrandIDxx);
                }
            });
        }

        public void setImage(String image){
            try {
                JSONArray laJson = new JSONArray(image);
                String lsImgVal = laJson.getJSONObject(0).getString("sImageURL");
                Picasso.get().load(lsImgVal).placeholder(R.drawable.honda1)
                        .error(R.drawable.honda1).into(imgBrand);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClick {
        void onClick(String fsListIdx);
    }
}