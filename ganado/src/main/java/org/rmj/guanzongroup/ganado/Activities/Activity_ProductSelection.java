package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.rmj.guanzongroup.ganado.Adapter.ProductSelectionAdapter;
import org.rmj.guanzongroup.ganado.Adapter.RecyclerViewAdapter_BrandSelection;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMProductSelection;

public class Activity_ProductSelection extends AppCompatActivity {
    private RecyclerView rvMcModel;
    private TextView txtBrandNm;
    private VMProductSelection mViewModel;
    private ProductSelectionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);
        initView();
        mViewModel = new ViewModelProvider(Activity_ProductSelection.this).get(VMProductSelection.class);

        mViewModel.GetModelsList(getIntent().getStringExtra("lsBrandID")).observe(Activity_ProductSelection.this, eMcModels -> {
            if (eMcModels.size() > 0){
                adapter = new ProductSelectionAdapter(eMcModels, new ProductSelectionAdapter.OnModelClickListener() {
                    @Override
                    public void OnClick(String ModelID, String BrandID) {
                        Intent intent = new Intent(Activity_ProductSelection.this, Activity_ProductInquiry.class);
                        intent.putExtra("lsBrandID",BrandID);
                        intent.putExtra("lsModelID",ModelID);
                        intent.putExtra("lsBrandNm",getIntent().getStringExtra("lsBrandNm"));
                        startActivity(intent);

                    }
                });

                rvMcModel.setAdapter(adapter);
                rvMcModel.setLayoutManager(new GridLayoutManager(Activity_ProductSelection.this,2,RecyclerView.VERTICAL,false));

            }
        });
        txtBrandNm.setText(getIntent().getStringExtra("lsBrandNm"));
    }
    private void initView(){
        rvMcModel = findViewById(R.id.rvMcModel);
        txtBrandNm = findViewById(R.id.lblBrand);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}