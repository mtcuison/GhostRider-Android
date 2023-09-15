//package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
//
//public class Adapter_Branch_Performance_Monitoring extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.list_adapter_branch_performance_monitoring);
//    }
//}
package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DAreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_Branch_Performance_Monitoring extends RecyclerView.Adapter<Adapter_Branch_Performance_Monitoring.ChartViewHolder> {
    private List<DAreaPerformance.BranchPerformance> BranchPerformances;
    private final Adapter_Branch_Performance_Monitoring.OnAreasClickListener mListener;
    public interface OnAreasClickListener {
        void OnClick(String sBranchCd, String sBranchnm);
    }
    public Adapter_Branch_Performance_Monitoring(List<DAreaPerformance.BranchPerformance> BranchMonitoringPerformance, OnAreasClickListener listener) {
        this.BranchPerformances = BranchMonitoringPerformance;
        this.mListener = listener;
    }

    @NonNull

    public Adapter_Branch_Performance_Monitoring.ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter_branch_performance_monitoring, parent, false);
        return new Adapter_Branch_Performance_Monitoring.ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        DAreaPerformance.BranchPerformance loBranch = BranchPerformances.get(position);
        holder.lblPeriod.setText(loBranch.sBranchNm);
        holder.lblActual.setText(String.valueOf(Double.valueOf(loBranch.nActualxx)));
        holder.lblGoal.setText(String.valueOf(Double.valueOf(loBranch.nGoalxxxx)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnClick(loBranch.sBranchCd, loBranch.sBranchNm);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BranchPerformances.size();
    }

    public static class ChartViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView lblPeriod, lblActual, lblGoal;
        public CircularProgressIndicator pi;
        public ConstraintLayout btnBrPerformance;
        public ChartViewHolder(@NonNull View itemView) {
            super(itemView);
            lblPeriod = itemView.findViewById(R.id.lblMonth);
            lblGoal = itemView.findViewById(R.id.lblGoal);
            lblActual = itemView.findViewById(R.id.lblActual);
        }
    }

}