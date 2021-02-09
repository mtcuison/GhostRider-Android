package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.CollectionAdapter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.CollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionList;

import java.util.ArrayList;
import java.util.List;

public class Activity_CollectionList extends AppCompatActivity {
    private static final String TAG = Activity_CollectionList.class.getSimpleName();

    private VMCollectionList mViewModel;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);
        mViewModel = new ViewModelProvider(this).get(VMCollectionList.class);
        initWidgets();
        mViewModel.DownloadDcp();
        mViewModel.getCollectionList().observe(this, collectionDetails -> {
            List<CollectionPlan> loCollection = new ArrayList<>();
            if(collectionDetails != null)
            for(int x = 0; x < collectionDetails.size(); x++){
                CollectionPlan loPlan = new CollectionPlan();
                loPlan.setAcctNoxxx(collectionDetails.get(x).getAcctNmbr());
                loPlan.setDCPNumber(collectionDetails.get(x).getIsDCPxxx());
                loPlan.setClientNme(collectionDetails.get(x).getFullName());
                loPlan.setHouseNoxx(collectionDetails.get(x).getHouseNox());
                loPlan.setAddressxx(collectionDetails.get(x).getAddressx());
                loPlan.setTownNamex(collectionDetails.get(x).getTownName());
                loPlan.setContactxx(collectionDetails.get(x).getMobileNo());
                loPlan.setDCPCountx(String.valueOf(collectionDetails.size()));
                loPlan.setStatusxxx(collectionDetails.get(x).getTranType());
                loPlan.setBalancexx("Balance");
                loPlan.setAmntDuexx(collectionDetails.get(x).getAmtDuexx());
                loCollection.add(loPlan);
            }
            CollectionAdapter loAdapter = new CollectionAdapter(loCollection, new CollectionAdapter.OnItemClickListener() {
                @Override
                public void OnClick() {

                }

                @Override
                public void OnMobileNoClickListener() {

                }

                @Override
                public void OnAddressClickListener() {

                }

                @Override
                public void OnActionButtonClick() {

                }
            });
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(loAdapter);
        });
    }

    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_collectionList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview_collectionList);
        layoutManager = new LinearLayoutManager(Activity_CollectionList.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
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
}