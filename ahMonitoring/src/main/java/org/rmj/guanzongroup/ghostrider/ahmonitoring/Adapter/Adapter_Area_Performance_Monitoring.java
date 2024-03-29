package org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.Apps.BullsEye.PerformancePeriod;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.PeriodicPerformance;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class Adapter_Area_Performance_Monitoring extends RecyclerView.Adapter<Adapter_Area_Performance_Monitoring.ChartViewHolder> {
    private List<PeriodicPerformance> areaPerformances;
    private final Adapter_Area_Performance_Monitoring.OnAreasClickListener mListener;
    public interface OnAreasClickListener {
        void OnClick(String sBranchCd, String sBranchnm);
    }
    public Adapter_Area_Performance_Monitoring(List<PeriodicPerformance> areaMonitoringPerformancebyMC, OnAreasClickListener listener) {
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
        PeriodicPerformance loArea = areaPerformances.get(position);
        holder.lblPeriod.setText(PerformancePeriod.getPeriodText(loArea.getsPeriod()));
//        Log.e("PERIOD NAME", PerformancePeriod.getPeriodText());
        holder.lblActual.setText(loArea.getnActual());
        holder.lblGoal.setText(loArea.getnGoalxx());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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