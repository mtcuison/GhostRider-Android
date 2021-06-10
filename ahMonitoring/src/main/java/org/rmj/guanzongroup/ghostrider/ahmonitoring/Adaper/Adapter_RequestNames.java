package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_RequestNames extends RecyclerView.Adapter<Adapter_RequestNames.ViewHolder>{

    private List<RequestNamesInfoModel> infoList;
    private RequestNamesInfoModel infoModel;
    private Context mContext;
    private onItemClickListener mListener;

    public Adapter_RequestNames(Context context,
                                List<RequestNamesInfoModel> requestNames_InfoModel_List){
        this.mContext = context;
        this.infoList = requestNames_InfoModel_List;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View nView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_requestnames, parent, false);
        return new ViewHolder(nView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.requestNamesInfoModel = infoList.get(position);
        infoModel = infoList.get(position);

        holder.textView.setText(infoModel.getRequestName());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RequestNamesInfoModel requestNamesInfoModel;
        TextView textView;
        public ViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.lbl_employeeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
