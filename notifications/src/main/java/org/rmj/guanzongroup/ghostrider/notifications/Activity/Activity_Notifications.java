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
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_ViewNotification;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMNotification;

import java.util.Objects;

public class Activity_Notifications extends AppCompatActivity {
    private static Activity_Notifications instance;

    private VMNotification mViewModel;
    private Toolbar toolbar;
    private ViewPager viewPager;

    private String MsgID;
    private String Title;
    private String MessageType;
    private MessageBox poMessage;

    public static Activity_Notifications getInstance(){
        return instance;
    }

    public String getMessageID(){
        return MsgID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        mViewModel = new ViewModelProvider(this).get(VMNotification.class);
        instance = this;
        poMessage = new MessageBox(Activity_Notifications.this);

        MessageType = getIntent().getStringExtra("type");

        MsgID = getIntent().getStringExtra("id");
        Title = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar_notification);
        viewPager = findViewById(R.id.viewpager_notifications);
        toolbar.setTitle(getTitle(MessageType));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getFragment(MessageType)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        } else if(item.getItemId() == R.id.action_notification_delete){
            poMessage.initDialog();
            poMessage.setTitle("Notifications");
            poMessage.setMessage("Delete " + Title + " notification?");
            poMessage.setPositiveButton("Delete", (view, dialog) -> {
                mViewModel.DeleteNotification(MsgID);
                dialog.dismiss();
                finish();
            });
            poMessage.setNegativeButton("Cancel", (view, dialog) -> dialog.dismiss());
            poMessage.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    private String getTitle(String type){
        if(type == null ||
                type.equalsIgnoreCase("notification")){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(!MessageType.equalsIgnoreCase("00003")) {
            getMenuInflater().inflate(R.menu.action_menu_notification, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}