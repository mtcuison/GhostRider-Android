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
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_LeaveApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_ObApplication;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_SelfieLogin;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.ArrayList;
import java.util.List;

public class Activity_Application extends AppCompatActivity {
    public static final String TAG = Activity_Application.class.getSimpleName();

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        int application = getIntent().getIntExtra("app", 0);

        Toolbar toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_application);

        if (application == AppConstants.INTENT_OB_APPLICATION) {
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_ObApplication()));
        }
        if(application == AppConstants.INTENT_LEAVE_APPLICATION){
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_LeaveApplication()));
        }
        if(application == AppConstants.INTENT_SELFIE_LOGIN){
            viewPager.setAdapter(new ApplicationPageAdapter(getSupportFragmentManager(), new Fragment_SelfieLogin()));
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

        private List<Fragment> fragment = new ArrayList<>();

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