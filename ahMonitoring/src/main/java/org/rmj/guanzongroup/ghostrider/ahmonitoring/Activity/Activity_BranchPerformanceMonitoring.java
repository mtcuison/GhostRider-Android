package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

public class Activity_BranchPerformanceMonitoring extends AppCompatActivity {

    private String BranchCD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_performance_monitoring);
        this.BranchCD = getIntent().getStringExtra("BranchCD");


    }
}