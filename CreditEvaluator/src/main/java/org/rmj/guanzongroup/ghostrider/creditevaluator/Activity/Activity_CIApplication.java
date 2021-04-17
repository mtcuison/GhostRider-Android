package org.rmj.guanzongroup.ghostrider.creditevaluator.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIResidenceInfo;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

import java.util.Objects;

import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

public class Activity_CIApplication extends AppCompatActivity {
    private static final String TAG = Activity_CIApplication.class.getSimpleName();
    private static Activity_CIApplication instance;
    private ViewPager viewPager;
    private String transNox, sCompnyNm, dTransact, sModelNm, nTerm, nMobile;
    public static Activity_CIApplication getInstance(){
        return instance;
    }
    public String getTransNox(){
        return transNox;
    }

    public String getsCompnyNm(){
        return sCompnyNm;
    }
    public String getdTransact(){
        return dTransact;
    }
    public String getsModelNm(){
        return sModelNm;
    }
    public String getnTerm(){
        return nTerm;
    }
    public String getnMobile(){
        return nMobile;
    }

    public void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci_application);
        instance = this;

        transNox = getIntent().getStringExtra("transno");
        sCompnyNm = getIntent().getStringExtra("ClientNm");
        dTransact = getIntent().getStringExtra("dTransact");
        sModelNm = getIntent().getStringExtra("ModelName");
        nTerm = getIntent().getStringExtra("term");
        nMobile = getIntent().getStringExtra("MobileNo");
        initWidgets();
    }

    @Override
    public void onBackPressed() {
        MessageBox loMessage = new MessageBox(Activity_CIApplication.this);
        loMessage.initDialog();
        loMessage.setTitle("CI Evaluation");
        loMessage.setMessage("Exit ci evaluation?");
        loMessage.setPositiveButton("Yes", (view, dialog) -> {

            dialog.dismiss();
            finish();
        });
        loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
        loMessage.show();
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_ci_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle loBundle = new Bundle();
        loBundle.putString("transno", transNox);
        Fragment_CIResidenceInfo ciResidenceInfo = new Fragment_CIResidenceInfo();
        ciResidenceInfo.setArguments(loBundle);
        viewPager = findViewById(R.id.viewpager_ciContainer);
        viewPager.setAdapter(new FragmentAdapter(this.getSupportFragmentManager(), CIConstants.CI_HOME_PAGES));
        ViewCompat.setNestedScrollingEnabled(viewPager, true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            MessageBox loMessage = new MessageBox(Activity_CIApplication.this);
            loMessage.initDialog();
            loMessage.setTitle("CI Evaluation");
            loMessage.setMessage("Exit ci evaluation?");
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