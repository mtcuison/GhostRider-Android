/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.notifications.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewNotification;
import org.rmj.guanzongroup.ghostrider.notifications.R;

public class Activity_Notifications extends AppCompatActivity {
    private static Activity_Notifications instance;

    private Toolbar toolbar;
    private ViewPager viewPager;

    private String Title;
    private String Sender;
    private String Message;
    private String date;

    public static Activity_Notifications getInstance(){
        return instance;
    }

    public String getMessageTitle() {
        return Title;
    }

    public String getSender() {
        return Sender;
    }

    public String getMessage() {
        return Message;
    }

    public String getDate() {
        return date;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        instance = this;

        String MessageType = getIntent().getStringExtra("type");

        Title = getIntent().getStringExtra("title");
        Sender = getIntent().getStringExtra("sender");
        Message = getIntent().getStringExtra("message");
        date = getIntent().getStringExtra("date");


        toolbar = findViewById(R.id.toolbar_notification);
        viewPager = findViewById(R.id.viewpager_notifications);
        toolbar.setTitle(getTitle(MessageType));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getFragment(MessageType)));
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
            return "";
        } else {
            return Title;
        }
    }

    private Fragment[] getFragment(String type){
        if(type.equalsIgnoreCase("notification")){
            return new Fragment[]{new Fragment_ViewNotification()};
        } else {
            return new Fragment[]{new Fragment_ViewMessages()};
        }
    }
}