/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.samsungKnox
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.samsungknox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_Activate;
import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_GetOfflinePin;
import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_GetPin;
import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_GetStatus;
import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_Unlock;
import org.rmj.guanzongroup.ghostrider.samsungknox.Fragment.Fragment_Upload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_Knox extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knox);

        int position = getIntent().getIntExtra("knox", 0);

        Toolbar toolbar = findViewById(R.id.toolbar_knox);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_knox);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getKnoxFragment(position)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static class FragmentAdapter extends FragmentStatePagerAdapter{
        private final List<Fragment> fragment = new ArrayList<>();

        public FragmentAdapter(@NonNull FragmentManager fm, Fragment fragment) {
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

    private Fragment getKnoxFragment(int position){
        Fragment[] knoxFragments = {
                new Fragment_Upload(),
                new Fragment_Activate(),
                new Fragment_Unlock(),
                new Fragment_GetPin(),
                new Fragment_GetOfflinePin(),
                new Fragment_GetStatus()};
        return knoxFragments[position];
    }
}