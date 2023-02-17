/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/8/21, 1:18 PM
 * project file last modified : 10/8/21, 1:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.*;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.NonSwipeableViewPager;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchPerformance_BarChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchPerformance_LineChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchPerformance_PieChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMBranchMonitor;

import java.util.Objects;

public class Activity_BranchPerformance extends AppCompatActivity {
    private static Activity_BranchPerformance instance;
    private VMBranchMonitor mViewModel;
    private MaterialToolbar toolbar;
    private NonSwipeableViewPager viewPager;
    private static String sBranchCd = "";
    private Fragment[] poPages = new Fragment[] {
            new Fragment_BranchPerformance_LineChart(),
            new Fragment_BranchPerformance_PieChart(),
            new Fragment_BranchPerformance_BarChart()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_performance);
        instance = this;
        sBranchCd = getIntent().getStringExtra("brnCD");
        mViewModel = new ViewModelProvider(Activity_BranchPerformance.this).get(VMBranchMonitor.class);
        initWidgets();
        setUpToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.line_chart) {
            moveToPageNumber(0);
        } else if(item.getItemId() == R.id.pie_chart) {
            moveToPageNumber(1);
        } else if(item.getItemId() == R.id.bar_chart) {
            moveToPageNumber(2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static Activity_BranchPerformance getInstance() {
        return instance;
    }

    public String getBranchCode() {
        return sBranchCd;
    }

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), poPages));
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Branch Performance Monitoring");
    }

    private void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

}