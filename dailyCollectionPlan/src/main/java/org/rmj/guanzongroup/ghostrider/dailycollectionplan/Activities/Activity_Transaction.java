package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_IncTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class Activity_Transaction extends AppCompatActivity {
    private static final String TAG = Activity_Transaction.class.getSimpleName();
    private static Activity_Transaction instance;
    private String TransNox = "";
    private String EntryNox = "";
    private String AccntNox = "";
    private String Remarksx = "";

    public static Activity_Transaction getInstance(){
        return instance;
    }

    public String getTransNox(){
        return TransNox;
    }

    public String getEntryNox(){
        return EntryNox;
    }

    public String getAccntNox(){
        return AccntNox;
    }

    public String getRemarksCode(){
        return Remarksx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        instance = this;
        Remarksx = getIntent().getStringExtra("remarksx");
        TransNox = getIntent().getStringExtra("transnox");
        EntryNox = getIntent().getStringExtra("entrynox");
        AccntNox = getIntent().getStringExtra("accntnox");
        Toolbar toolbar = findViewById(R.id.toolbar_transaction);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_transaction);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getTransactionFragment(Remarksx)));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static class FragmentAdapter extends FragmentStatePagerAdapter {
        private final Fragment fragment;

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

    private Fragment getTransactionFragment(String transaction){
        if(transaction.equalsIgnoreCase("Paid")){
            return new Fragment_PaidTransaction();
        } else if(transaction.equalsIgnoreCase("Promise to Pay")){
            return new Fragment_PromiseToPay();
        } else if(transaction.equalsIgnoreCase("Customer Not Around")){
            return new Fragment_CustomerNotAround();
        } else if(transaction.equalsIgnoreCase("Loan Unit")){
            return new Fragment_LoanUnit();
        } else if(transaction.equalsIgnoreCase("Carnap") ||
                transaction.equalsIgnoreCase("Uncooperative") ||
                transaction.equalsIgnoreCase("Missing Customer") ||
                transaction.equalsIgnoreCase("Missing Unit") ||
                transaction.equalsIgnoreCase("Missing Client and Unit") ||
                transaction.equalsIgnoreCase("Did Not Pay") ||
                transaction.equalsIgnoreCase("Others")){
            return new Fragment_IncTransaction();
        }
        return null;
    }
}