package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.Adapter_CashCountDetailInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMCashCountLog;

public class Activity_CashCountLogDetails extends AppCompatActivity {

    private VMCashCountLog mViewModel;
    private Adapter_CashCountDetailInfo poAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_log_details);
        initWidgets();

        mViewModel = new ViewModelProvider(Activity_CashCountLogDetails.this)
                .get(VMCashCountLog.class);

//        poAdapter = new Adapter_CashCountDetailInfo();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CashCountLogDetails.this);
        recyclerView.setLayoutManager(layoutManager);
    }

}