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
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_JobOrder;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_MCSales;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_SPSales;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMMonitoring;

import java.util.Objects;

public class Activity_Monitoring extends AppCompatActivity {

    private VMMonitoring mViewModel;

    private static Activity_Monitoring instance;
    private String psAreaCde;
    private String psBrnchCd;
    private ViewPager viewPager;
    private TextView lblTitle;
    private ColorStateList poColor;
    private TextView lblItem1, lblItem2, lblSelectd;

    public static Activity_Monitoring getInstance(){
        return instance;
    }

    public String getAreaCode(){
        return psAreaCde;
    }

    public String getBranchCD(){
        return psBrnchCd;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        mViewModel = new ViewModelProvider(this).get(VMMonitoring.class);
        lblItem1 = findViewById(R.id.item1);
        lblItem2 = findViewById(R.id.item2);
        lblSelectd = findViewById(R.id.select);
        poColor = lblItem2.getTextColors();
        lblItem1.setOnClickListener(new TabClickHandler());
        lblItem2.setOnClickListener(new TabClickHandler());

        mViewModel.getAreaDesciption().observe(this, s -> {
            try{
                lblTitle.setText(s);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getUserInfo().observe(this, eEmployeeInfo -> {
            try{

            } catch (Exception e){
                e.printStackTrace();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        instance = this;
        psAreaCde = getIntent().getStringExtra("sAreaCode");
        Toolbar toolbar = findViewById(R.id.toolbar_monitoring);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        lblTitle = findViewById(R.id.lbl_title);
        viewPager = findViewById(R.id.viewpager_monitoring);
        viewPager.setAdapter(new MonitoringPageAdapter(getSupportFragmentManager()));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (height/3));
//        viewPager.setLayoutParams(params);
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
                new Fragment_MCSales(),
                new Fragment_SPSales(),
                new Fragment_JobOrder()};

        private final String[] titles = {
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

    private class TabClickHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item1){
                lblSelectd.animate().x(0).setDuration(100);
                lblItem1.setTextColor(Color.WHITE);
                lblItem2.setTextColor(poColor);
                viewPager.setCurrentItem(0);
//                mViewModel.setSalesType(MC_SALES);
            } else if (view.getId() == R.id.item2){
                lblItem1.setTextColor(poColor);
                lblItem2.setTextColor(Color.WHITE);
                int size = lblItem2.getWidth();
                lblSelectd.animate().x(size).setDuration(100);
                viewPager.setCurrentItem(1);
//                mViewModel.setSalesType(SP_SALES);
            }
        }
    }
}