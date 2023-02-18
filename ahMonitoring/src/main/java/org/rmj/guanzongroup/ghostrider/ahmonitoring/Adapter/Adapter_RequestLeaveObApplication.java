package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.PetManager.pojo.LeaveApprovalInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_RequestLeaveObApplication extends RecyclerView.Adapter<Adapter_RequestLeaveObApplication.ViewHolder>{

    private List<LeaveApprovalInfo> infoList;
    private LeaveApprovalInfo infoModel;
    private Context mContext;
    private onItemClickListener mListener;

    public Adapter_RequestLeaveObApplication(Context context,
                                             List<LeaveApprovalInfo> requestleaveOb_InfoModel_List){
        this.mContext = context;
        this.infoList = requestleaveOb_InfoModel_List;
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

//        holder.textView.setText(infoModel.getEmployName());
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LeaveApprovalInfo requestNamesInfoModel;
        MaterialTextView textView;
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
