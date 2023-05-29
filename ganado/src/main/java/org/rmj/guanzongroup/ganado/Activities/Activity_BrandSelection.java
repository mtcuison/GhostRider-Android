package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.rmj.guanzongroup.ganado.Adapter.RecyclerViewAdapter_BrandSelection;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMBrandList;

import java.util.List;

public class Activity_BrandSelection extends AppCompatActivity {

    private VMBrandList mViewModel;

    private RecyclerView recyclerView;
    private TextView lblNoItem;

    private RecyclerViewAdapter_BrandSelection poAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_BrandSelection.this).get(VMBrandList.class);
        setContentView(R.layout.activity_brand_selection);
        recyclerView = findViewById(R.id.rv_brands);
        lblNoItem = findViewById(R.id.textView);
        recyclerView.setLayoutManager(new GridLayoutManager(Activity_BrandSelection.this,
                2, RecyclerView.VERTICAL, false));
        String lsBrandNme = getIntent().getStringExtra("xBrandNme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetupListView(lsBrandNme);

        
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetupListView(String lsBrandNme) {
        mViewModel.getAllBrandName();

        /*lblNoItem.setVisibility(View.GONE);*/

        recyclerView.setAdapter(poAdapter);
        poAdapter.notifyDataSetChanged();
            /*} else {
                lblNoItem.setVisibility(View.VISIBLE);
                lblNoItem.setText("Currently there are no available items for product '" + "" + "'");
            }*/


    }
}
