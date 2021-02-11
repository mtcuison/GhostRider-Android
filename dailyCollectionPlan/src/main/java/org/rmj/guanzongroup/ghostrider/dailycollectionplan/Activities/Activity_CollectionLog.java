package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionLog;

import java.util.List;

public class Activity_CollectionLog extends AppCompatActivity {
    private static final String TAG = Activity_CollectionLog.class.getSimpleName();

    private VMCollectionLog mViewModel;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_log);
        initWidgets();

        mViewModel = new ViewModelProvider(this).get(VMCollectionLog.class);

        mViewModel.getCollectionList().observe(Activity_CollectionLog.this, new Observer<List<EDCPCollectionDetail>>() {
            @Override
            public void onChanged(List<EDCPCollectionDetail> collectionDetails) {

            }
        });
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionLog);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionLog);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Activity_CollectionLog.this, Activity_CollectionList.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(Activity_CollectionLog.this, Activity_CollectionList.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}