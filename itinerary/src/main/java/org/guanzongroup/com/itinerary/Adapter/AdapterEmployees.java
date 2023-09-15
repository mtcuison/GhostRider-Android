package org.guanzongroup.com.itinerary.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.itinerary.R;
import org.json.JSONObject;

import java.util.List;

public class AdapterEmployees extends RecyclerView.Adapter<AdapterEmployees.VHDetails> {

    private final List<JSONObject> poDetails;
    private final OnEmployeeSelectListener mListener;

    public interface OnEmployeeSelectListener{
        void OnSelect(String args, String args1, String args2);
    }

    public AdapterEmployees(List<JSONObject> poDetails, OnEmployeeSelectListener mListener) {
        this.poDetails = poDetails;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public VHDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_employee, parent, false);
        return new VHDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHDetails holder, int position) {
        try{
            JSONObject loDetail = poDetails.get(position);
            String lsUserIDxx = loDetail.getString("sEmployID");
            String lsUserName = loDetail.getString("sUserName");
            String lsDeptIDxx = loDetail.getString("sDeptIDxx");
            holder.lblEmployee.setText(lsUserName);

            holder.view.setOnClickListener(view -> mListener.OnSelect(lsUserIDxx, lsUserName, lsDeptIDxx));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return poDetails.size();
    }

    public static class VHDetails extends RecyclerView.ViewHolder{

        public TextView lblEmployee;
        public View view;

        public VHDetails(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            lblEmployee = itemView.findViewById(R.id.lbl_employee);
        }
    }
}
