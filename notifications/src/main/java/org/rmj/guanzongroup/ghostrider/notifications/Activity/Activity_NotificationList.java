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

public class Activity_NotificationList extends AppCompatActivity {
    private static Activity_NotificationList instance;

    private Toolbar toolbar;
    private ViewPager viewPager;


    public static Activity_NotificationList getInstance(){
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__notification_list);
        instance = this;

        String type = getIntent().getStringExtra("type");


        toolbar = findViewById(R.id.toolbar_notificationList);
        viewPager = findViewById(R.id.viewpager_notificationsList);
        toolbar.setTitle(getTitle(type));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getFragment(type)));
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
            return type;
        }
    }

    private Fragment[] getFragment(String type){
        if(type.equalsIgnoreCase("Messages")){
            return new Fragment[]{new Fragment_MessageList()};
        } else {
            return new Fragment[]{new Fragment_NotificationList()};
        }
    }
}