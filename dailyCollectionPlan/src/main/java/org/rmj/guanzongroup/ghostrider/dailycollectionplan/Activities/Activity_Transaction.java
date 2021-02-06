package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class Activity_Transaction extends AppCompatActivity {
    private static final String TAG = Activity_Transaction.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        int transaction = Integer.parseInt(getIntent().getStringExtra("transaction"));
        Toolbar toolbar = findViewById(R.id.toolbar_transaction);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_transaction);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getTransactionFragment(transaction)));
    }


    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private Fragment fragment;

        public FragmentAdapter(@NonNull FragmentManager fm, Fragment fragment) {
            super(fm);
            this.fragment = fragment;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragment;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    private Fragment getTransactionFragment(int transaction){
        if(transaction == 1){
            return new Fragment_PaidTransaction();
        } else if(transaction == 2){
            return new Fragment_PromiseToPay();
        } else if(transaction == 3){
            return new Fragment_CustomerNotAround();
        } else if(transaction == 4){
            return new Fragment_LoanUnit();
        }
        return null;
    }
}