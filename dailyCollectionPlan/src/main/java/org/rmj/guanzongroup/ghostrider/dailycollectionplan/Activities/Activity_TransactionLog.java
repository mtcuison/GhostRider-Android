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

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_ClientDtl_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_CustomerNotAround;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_CustomerNotAround_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_IncTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_OtherTransactions_Log;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PaidTransaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.Objects;

public class Activity_TransactionLog extends AppCompatActivity {
    private static final String TAG = Activity_TransactionLog.class.getSimpleName();
    private static Activity_TransactionLog instance;
    public static String fullNme, entryNox, acctNox, remCodex, imgNme, clientID, clientAddress, remarks ;


    public static Activity_TransactionLog getInstance(){
        return instance;
    }

//    public String getTransNox(){
//        return TransNox;
//    }
//
//    public int getEntryNox(){
//        return EntryNox;
//    }
//
//    public String getAccntNox(){
//        return AccntNox;
//    }
//
//    public String getRemarksCode(){
//        return Remarksx;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionlog);
        instance = this;
        entryNox = getIntent().getStringExtra("entryNox");
        acctNox = getIntent().getStringExtra("acctNox");
        fullNme = getIntent().getStringExtra("fullNme");
        remCodex = getIntent().getStringExtra("remCodex");
        imgNme = getIntent().getStringExtra("imgNme");
        clientID = getIntent().getStringExtra("sClientID");
        clientAddress = getIntent().getStringExtra("sAddressx");
        remarks = getIntent().getStringExtra("sRemarksx");


        Toolbar toolbar = findViewById(R.id.toolbar_transaction);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = findViewById(R.id.viewpager_transaction);

        if(remCodex.equalsIgnoreCase("CNA")) {
            getSupportActionBar().setTitle("Customer Not Around");
        }
        else if(remCodex.equalsIgnoreCase("Car")) {
            getSupportActionBar().setTitle("Carnap");
        }
        else if(remCodex.equalsIgnoreCase("UNC")) {
            getSupportActionBar().setTitle("Uncooperative");
        }
        else if(remCodex.equalsIgnoreCase("MCs")) {
            getSupportActionBar().setTitle("Missing Customer");
        }
        else if(remCodex.equalsIgnoreCase("MUn")) {
            getSupportActionBar().setTitle("Missing Unit");
        }
        else if(remCodex.equalsIgnoreCase("MCU")) {
            getSupportActionBar().setTitle("Missing Customer and Unit");
        }
        else if(remCodex.equalsIgnoreCase("DNP")) {
            getSupportActionBar().setTitle("Did Not Pay");
        }
        else if(remCodex.equalsIgnoreCase("NV")) {
            getSupportActionBar().setTitle("Not Visited");
        }
        else if(remCodex.equalsIgnoreCase("OTH")) {
            getSupportActionBar().setTitle("Others");
        }
        else if(remCodex.equalsIgnoreCase("FLA")) {
            getSupportActionBar().setTitle("For Legal Action");
        }
        else if(remCodex.equalsIgnoreCase("TA")) {
            getSupportActionBar().setTitle("Transferred/Assumed");
        }
        else if(remCodex.equalsIgnoreCase("FO")) {
            getSupportActionBar().setTitle("False Ownership");
        }
        else if(remCodex.equalsIgnoreCase("LUn")) {
            getSupportActionBar().setTitle("Loan Unit");
        }

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
        if(transaction.equalsIgnoreCase("CNA")){
            return new Fragment_CustomerNotAround_Log();
        }
        else if(transaction.equalsIgnoreCase("FLA") ||
                transaction.equalsIgnoreCase("TA") ||
                transaction.equalsIgnoreCase("FO") ||
                transaction.equalsIgnoreCase("LUn")) {
            return new Fragment_ClientDtl_Log();
        }
        else if((transaction.equalsIgnoreCase("Car")
                || (transaction.equalsIgnoreCase("UNC"))
                || (transaction.equalsIgnoreCase("MCs"))
                || (transaction.equalsIgnoreCase("MUn"))
                || (transaction.equalsIgnoreCase("MCU"))
                || (transaction.equalsIgnoreCase("DNP"))
                || transaction.equalsIgnoreCase("NV"))
                || (transaction.equalsIgnoreCase("OTH"))){
            return new Fragment_OtherTransactions_Log();
        }
        return null;
    }
}

//if(transaction.equalsIgnoreCase("Paid")){
//        return new Fragment_PaidTransaction();
//        } else if(transaction.equalsIgnoreCase("Promise to Pay")){
//        return new Fragment_PromiseToPay();
//        } else if(transaction.equalsIgnoreCase("Customer Not Around")){
//        return new Fragment_CustomerNotAround();
////            return new Fragment_CustomerNotAround();
//        } else if(transaction.equalsIgnoreCase("Loan Unit") ||
//        transaction.equalsIgnoreCase("For Legal Action") ||
//        transaction.equalsIgnoreCase("False Ownership") ||
//        transaction.equalsIgnoreCase("Transferred/Assumed")){
//        return new Fragment_LoanUnit();
//        } else if(transaction.equalsIgnoreCase("Car nap") ||
//        transaction.equalsIgnoreCase("Uncooperative") ||
//        transaction.equalsIgnoreCase("Missing Customer") ||
//        transaction.equalsIgnoreCase("Missing Unit") ||
//        transaction.equalsIgnoreCase("Missing Client and Unit") ||
//        transaction.equalsIgnoreCase("Did Not Pay") ||
//        transaction.equalsIgnoreCase("Not Visited") ||
//        transaction.equalsIgnoreCase("Others")){
//        return new Fragment_IncTransaction();
//        }
//        return null;
