package org.rmj.guanzongroup.ghostrider.approvalcode.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.approvalcode.Fragment.Fragment_ApprovalEntry;
import org.rmj.guanzongroup.ghostrider.approvalcode.Fragment.Fragment_CreditAppApproval;
import org.rmj.guanzongroup.ghostrider.approvalcode.Fragment.Fragment_ManualLog;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_ApprovalCode extends AppCompatActivity {
    private static Activity_ApprovalCode instance;

    public static Activity_ApprovalCode getInstance(){
        return instance;
    }

    private String psSystemType;
    private String psSystemCode;
    private String psSCATypexxx;

    public String getSystemType(){
        return psSystemType;
    }

    public String getSystemCode(){
        return psSystemCode;
    }

    public String getPsSCAType(){
        return psSCATypexxx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_code);
        instance = this;
        String psSysCode = getIntent().getStringExtra("sysCode");
        psSystemType = getIntent().getStringExtra("systype");
        psSystemCode = getIntent().getStringExtra("sSystemCd");
        psSCATypexxx = getIntent().getStringExtra("sSCATypex");
        Toolbar toolbar = findViewById(R.id.toolbar_approvalEntry);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_approvalEntry);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getFragmentUI(psSysCode)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private static class FragmentAdapter extends FragmentStatePagerAdapter{

        List<Fragment> fragmentList = new ArrayList<>();

        public FragmentAdapter(@NonNull FragmentManager fm, Fragment fragment) {
            super(fm);
            this.fragmentList.add(fragment);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    private Fragment getFragmentUI(String SysCode){
        if(SysCode.equalsIgnoreCase("0")){
            return new Fragment_ApprovalEntry();
        }
        if(SysCode.equalsIgnoreCase("1")){
            return new Fragment_CreditAppApproval();
        } else {
            return new Fragment_ManualLog();
        }
    }
}