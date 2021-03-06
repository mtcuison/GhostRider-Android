/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchMonitor;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_BranchOpening;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_LeaveApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_ObApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_Reimbursement;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_SelfieLogin;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_Application extends AppCompatActivity {
    public static final String TAG = Activity_Application.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        int application = getIntent().getIntExtra("app", 0);

        Toolbar toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_application);

        if (application == AppConstants.INTENT_OB_APPLICATION) {
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_ObApplication()));
        } else if(application == AppConstants.INTENT_LEAVE_APPLICATION){
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_LeaveApplication()));
        } else if(application == AppConstants.INTENT_SELFIE_LOGIN){
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_SelfieLogin()));
        } else if(application == AppConstants.INTENT_REIMBURSEMENT){
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_Reimbursement()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ApplicationPageAdapter extends FragmentStatePagerAdapter{

        private final List<Fragment> fragment = new ArrayList<>();

        public ApplicationPageAdapter(@NonNull FragmentManager fm, Fragment fragment) {
            super(fm);
            this.fragment.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }
    }
}