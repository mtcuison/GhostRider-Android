package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseInfo;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.Objects;

public class Activity_CreditApplication extends AppCompatActivity {
    private static final String TAG = Activity_CreditApplication.class.getSimpleName();
    private static Activity_CreditApplication instance;
    private ViewPager viewPager;
    private String transNox;

    public static Activity_CreditApplication getInstance(){
        return instance;
    }

    public String getTransNox(){
        return transNox;
    }

    public void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_application);
        instance = this;
        transNox = getIntent().getStringExtra("transno");
        initWidgets();
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle loBundle = new Bundle();
        loBundle.putString("transno", transNox);
        Fragment_SpouseInfo personalInfo = new Fragment_SpouseInfo();
        personalInfo.setArguments(loBundle);
        viewPager = findViewById(R.id.viewpager_creditApp);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), CreditAppConstants.APPLICATION_PAGES));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            MessageBox loMessage = new MessageBox(Activity_CreditApplication.this);
            loMessage.initDialog();
            loMessage.setTitle("Credit Application");
            loMessage.setMessage("Exit credit online application?");
            loMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
            });
            loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
            loMessage.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }
}