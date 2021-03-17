package org.rmj.guanzongroup.onlinecreditapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_DisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo;
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

    /**
     *            <p>0 new Fragment_PersonalInfo(),</p>
     *            <p>1 new Fragment_ResidenceInfo(),</p>
     *            <p>2 new Fragment_MeansInfoSelection(),</p>
     *            <p>3 new Fragment_EmploymentInfo(),</p>
     *            <p>4 new Fragment_SelfEmployedInfo(),</p>
     *            <p>5 new Fragment_Finance(),</p>
     *            <p>6 new Fragment_PensionInfo(),</p>
     *            <p>7 new Fragment_SpouseInfo(),</p>
     *            <p>8 new Fragment_SpouseResidenceInfo(),</p>
     *            <p>9 new Fragment_SpouseEmploymentInfo(),</p>
     *            <p>10 new Fragment_SpouseSelfEmployedInfo(),</p>
     *            <p>11 new Fragment_SpousePensionInfo(),</p>
     *            <p>12 new Fragment_DisbursementInfo(),</p>
     *            <p>13 new Fragment_Dependent(),</p>
     *            <p>14 new Fragment_OtherInfo(),</p>
     *            <p>15 new Fragment_CoMaker()</p>
     */
    public void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_application);
        instance = this;
        transNox = getIntent().getStringExtra("transno");
        Log.e(TAG, transNox);

        Toolbar toolbar = findViewById(R.id.toolbar_application);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Bundle loBundle = new Bundle();
        loBundle.putString("transno", transNox);
        Fragment_MeansInfoSelection personalInfo = new Fragment_MeansInfoSelection();
        personalInfo.setArguments(loBundle);
        viewPager = findViewById(R.id.viewpager_creditApp);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), CreditAppConstants.APPLICATION_PAGES));
        //viewPager.setOffscreenPageLimit(7);
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