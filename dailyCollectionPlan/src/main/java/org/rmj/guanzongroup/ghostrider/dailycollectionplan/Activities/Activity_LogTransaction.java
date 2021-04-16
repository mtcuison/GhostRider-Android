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

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_Log_Client_Detail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_Log_CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_Log_OtherTransactions;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_Log_Paid_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_Log_PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class Activity_LogTransaction extends AppCompatActivity {
    private static final String TAG = Activity_LogTransaction.class.getSimpleName();
    private static Activity_LogTransaction instance;
    public static String transNox, fullNme, entryNox, acctNox, remCodex, imgNme, clientID, clientAddress, remarks ;
    public static String psTransTp;

    public static Activity_LogTransaction getInstance(){
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionlog);
        instance = this;
        transNox = getIntent().getStringExtra("sTransNox");
        acctNox = getIntent().getStringExtra("acctNox");
        fullNme = getIntent().getStringExtra("fullNme");
        remCodex = getIntent().getStringExtra("remCodex");
        imgNme = getIntent().getStringExtra("imgNme");
        clientID = getIntent().getStringExtra("sClientID");
        clientAddress = getIntent().getStringExtra("sAddressx");
        remarks = getIntent().getStringExtra("sRemarksx");
        psTransTp = DCP_Constants.getRemarksDescription(remCodex);


        Toolbar toolbar = findViewById(R.id.toolbar_transaction);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_transaction);

        getSupportActionBar().setTitle("GhostRider Android");

        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), getTransactionFragment(remCodex)));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModelStore().clear();
    }

    private Fragment getTransactionFragment(String transaction){
        if(transaction.equalsIgnoreCase("PAY")) {
            return new Fragment_Log_Paid_Transaction();
        }
        else if(transaction.equalsIgnoreCase("PTP")) {
            return new Fragment_Log_PromiseToPay();
        }
        else if(transaction.equalsIgnoreCase("CNA")){
            return new Fragment_Log_CustomerNotAround();
        }
        else if(transaction.equalsIgnoreCase("FLA") ||
                transaction.equalsIgnoreCase("TA") ||
                transaction.equalsIgnoreCase("FO") ||
                transaction.equalsIgnoreCase("LUn")) {
            return new Fragment_Log_Client_Detail();
        }
        else if((transaction.equalsIgnoreCase("Car")
                || (transaction.equalsIgnoreCase("UNC"))
                || (transaction.equalsIgnoreCase("MCs"))
                || (transaction.equalsIgnoreCase("MUn"))
                || (transaction.equalsIgnoreCase("MCU"))
                || (transaction.equalsIgnoreCase("DNP"))
                || transaction.equalsIgnoreCase("NV"))
                || (transaction.equalsIgnoreCase("OTH"))){
            return new Fragment_Log_OtherTransactions();
        }
        return null;
    }
}
