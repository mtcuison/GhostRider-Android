package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.TransactionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Activity_CollectionLog extends AppCompatActivity implements VMCollectionLog.PostTransactionCallback {
    private static final String TAG = Activity_CollectionLog.class.getSimpleName();

    private VMCollectionLog mViewModel;

    private TextView lblBranch, lblAddrss, lblDate;
    private MaterialButton btnPost;
    private TransactionAdapter poAdapter;
    private LinearLayoutManager poManager;
    private RecyclerView recyclerView;

    private LoadDialog poDialog;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_log);
        initWidgets();

        mViewModel = new ViewModelProvider(this).get(VMCollectionLog.class);
        mViewModel.getUserBranchInfo().observe(Activity_CollectionLog.this, eBranchInfo -> {
            lblBranch.setText(eBranchInfo.getBranchNm());
            lblAddrss.setText(eBranchInfo.getAddressx());
            lblDate.setText(getDate());
        });

        mViewModel.getCollectionMaster().observe(Activity_CollectionLog.this, collectionMaster -> {
            try{
                mViewModel.setCollectionMaster(collectionMaster);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        mViewModel.getCollectionList().observe(Activity_CollectionLog.this, collectionDetails -> {
            try {
                mViewModel.setTransactionList(collectionDetails);

                poAdapter = new TransactionAdapter(collectionDetails);
                poManager = new LinearLayoutManager(Activity_CollectionLog.this);
                poManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(poManager);
                recyclerView.setAdapter(poAdapter);
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.postTransactions(Activity_CollectionLog.this);
            }
        });
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionLog);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionLog);
        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddrss = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);
        btnPost = findViewById(R.id.btn_postTransaction);

        poDialog = new LoadDialog(Activity_CollectionLog.this);
        poMessage = new MessageBox(Activity_CollectionLog.this);
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

    public String getDate(){
        return "Collection For " + FormatUIText.getParseDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
    }

    @Override
    public void OnLoad() {
        poDialog.initDialog("Daily Collection Plan", "Posting all transactions. Please wait...", false);
        poDialog.show();
    }

    @Override
    public void OnPostSuccess(String[] args) {
        poDialog.dismiss();
        poMessage.setTitle("Daily Collection Plan");
        poMessage.setMessage(args[0]);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }

    @Override
    public void OnPostFailed(String message) {
        poDialog.dismiss();
        poMessage.setTitle("Daily Collection Plan");
        poMessage.setMessage(message);
        poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
        poMessage.show();
    }
}