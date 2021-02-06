package org.rmj.guanzongroup.ghostrider.approvalcode.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.AdapterApprovalAuth;
import org.rmj.guanzongroup.ghostrider.approvalcode.R;
import org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel.VMApprovalSelection;

public class Activity_ApprovalSelection extends AppCompatActivity {
    public static final String TAG = Activity_ApprovalSelection.class.getSimpleName();

    private VMApprovalSelection mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_selection);
        mViewModel = ViewModelProviders.of(this).get(VMApprovalSelection.class);

        String lsSysType = getIntent().getStringExtra("syscode");
        Toolbar toolbar = findViewById(R.id.toolbar_approvalSelection);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_approvalAuth);
        mViewModel.getReferenceAuthList(lsSysType).observe(this, requestList -> {
            LinearLayoutManager manager = new LinearLayoutManager(Activity_ApprovalSelection.this);
            manager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setAdapter(new AdapterApprovalAuth(requestList, (SystemCode, SCAType) -> {
                Intent loIntentx = new Intent(Activity_ApprovalSelection.this, Activity_ApprovalCode.class);
                if(SystemCode.equalsIgnoreCase("CA")) {
                    loIntentx.putExtra("sysCode", "1");
                } else{
                    loIntentx.putExtra("sysCode", "0");
                }
                loIntentx.putExtra("systype", lsSysType);
                loIntentx.putExtra("sSystemCd", SystemCode);
                loIntentx.putExtra("sSCATypex", SCAType);
                startActivity(loIntentx);
            }));
            recyclerView.setLayoutManager(manager);
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}