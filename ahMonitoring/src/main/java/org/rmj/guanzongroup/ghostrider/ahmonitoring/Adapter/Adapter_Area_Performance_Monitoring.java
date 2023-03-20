package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DAreaPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_Area_Performance_Monitoring extends RecyclerView.Adapter<Adapter_Area_Performance_Monitoring.ChartViewHolder> {
    private List<DAreaPerformance.PeriodicPerformance> areaPerformances;
    private final Adapter_Area_Performance_Monitoring.OnAreasClickListener mListener;
    public interface OnAreasClickListener {
        void OnClick(String sBranchCd, String sBranchnm);
    }
    public Adapter_Area_Performance_Monitoring(List<DAreaPerformance.PeriodicPerformance> areaMonitoringPerformancebyMC, OnAreasClickListener listener) {
        this.areaPerformances = areaMonitoringPerformancebyMC;
        this.mListener = listener;
    }

    @NonNull

    public Adapter_Area_Performance_Monitoring.ChartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_area_performance_monitoring, parent, false);
        return new Adapter_Area_Performance_Monitoring.ChartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChartViewHolder holder, int position) {
        DAreaPerformance.PeriodicPerformance loArea = areaPerformances.get(position);
        holder.lblPeriod.setText(loArea.sPeriodxx);
        holder.lblActual.setText(String.valueOf(Double.valueOf(loArea.nActualxx)));
        holder.lblGoal.setText(String.valueOf(Double.valueOf(loArea.nGoalxxxx)));
    }

    @Override
    public int getItemCount() {
        return areaPerformances.size();
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