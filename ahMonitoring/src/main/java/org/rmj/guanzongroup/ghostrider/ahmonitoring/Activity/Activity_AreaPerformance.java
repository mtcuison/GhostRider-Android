/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/14/21, 10:43 AM
 * project file last modified : 10/14/21, 10:43 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.FragmentAdapter;
import org.rmj.g3appdriver.etc.NonSwipeableViewPager;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaPerformance_BarChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaPerformance_LineChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaPerformance_PieChart;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMAreaPerfromanceMonitoring;

import java.util.Objects;

public class Activity_AreaPerformance extends AppCompatActivity{
    private VMAreaPerfromanceMonitoring mViewModel;
    private MaterialToolbar toolbar;
    private NonSwipeableViewPager viewPager;

    private Fragment[] poPages = new Fragment[] {
            new Fragment_AreaPerformance_LineChart(),
            new Fragment_AreaPerformance_PieChart(),
            new Fragment_AreaPerformance_BarChart()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_performance);
        mViewModel = new ViewModelProvider(Activity_AreaPerformance.this)
                .get(VMAreaPerfromanceMonitoring.class);
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

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), poPages));
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Performance Monitoring");
    }

    private void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

}