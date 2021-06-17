/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/14/21 10:49 AM
 * project file last modified : 6/14/21 10:49 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_JobOrder;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_MCSales;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_SPSales;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.Objects;

public class Activity_Monitoring extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);

        Toolbar toolbar = findViewById(R.id.toolbar_monitoring);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        TabLayout tabLayout = findViewById(R.id.tablayout_monitoring);

        ViewPager viewPager = findViewById(R.id.viewpager_monitoring);
        viewPager.setAdapter(new MonitoringPageAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static class MonitoringPageAdapter extends FragmentStatePagerAdapter {

        private final Fragment[] fragments = {
                new Fragment_BranchMonitor(),
                new Fragment_MCSales(),
                new Fragment_SPSales(),
                new Fragment_JobOrder()};

        private final String[] titles = {
                "Overall",
                "Mc Sales",
                "Sp Sales",
                "Job Order"};

        public MonitoringPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}