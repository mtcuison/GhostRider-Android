/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 1:21 PM
 * project file last modified : 6/9/21 1:21 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.notifications.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewNotification;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.Objects;

public class Activity_Container extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);


        toolbar = findViewById(R.id.toolbar_notification);
        viewPager = findViewById(R.id.viewpager_notifications);
        String lsType = getIntent().getStringExtra("type");
        toolbar.setTitle(getTitle(lsType));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getFragment(lsType)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String getTitle(String type){
        if(type.equalsIgnoreCase("notification")){
            return "Notifications";
        } else {
            return "Messages";
        }
    }

    private Fragment[] getFragment(String type){
        if(type.equalsIgnoreCase("notification")){
            return new Fragment[]{new Fragment_NotificationList()};
        } else {
            return new Fragment[]{new Fragment_MessageList()};
        }
    }
}