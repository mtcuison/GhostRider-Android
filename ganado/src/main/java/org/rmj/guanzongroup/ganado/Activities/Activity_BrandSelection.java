package org.rmj.guanzongroup.ganado.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ganado.Adapter.RecyclerViewAdapter_BrandSelection;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMBrandList;

import java.util.Objects;


public class Activity_BrandSelection extends AppCompatActivity {

    private VMBrandList mViewModel;
    private RecyclerView rvc_brandlist;
    private ShapeableImageView brandcatimg;
    private RecyclerViewAdapter_BrandSelection rec_brandList;
    private LoadDialog poLoad;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_selection);
        intWidgets();
        mViewModel = new ViewModelProvider(this).get(VMBrandList.class);
        int backgroundResId = getIntent().getIntExtra("background", 0);

        brandcatimg = findViewById(R.id.imagebrandtop);
        brandcatimg.setImageResource(backgroundResId);

        mViewModel.getBrandList().observe(Activity_BrandSelection.this, brandList -> {
            if (brandList.size() > 0) {
                rec_brandList = new RecyclerViewAdapter_BrandSelection(brandList, new RecyclerViewAdapter_BrandSelection.OnBrandSelectListener() {
                    @Override
                    public void OnSelect(String BrandID, String BrandName) {
                        Intent intent = new Intent(Activity_BrandSelection.this, Activity_ProductSelection.class);
                        intent.putExtra("lsBrandID", BrandID);
                        intent.putExtra("lsBrandNm", BrandName);
                        intent.putExtra("background", getBrandImageResource(BrandID));
                        startActivity(intent);

                    }
                });

                rvc_brandlist.setAdapter(rec_brandList);
                rvc_brandlist.setLayoutManager(new GridLayoutManager(Activity_BrandSelection.this, 2, RecyclerView.VERTICAL, false));

                rec_brandList.notifyDataSetChanged();
            }
        });
    }

    private void intWidgets() {
        rvc_brandlist = findViewById(R.id.rv_brands);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_brand);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private int getBrandImageResource(String brandIndex) {
        switch (brandIndex) {
            case "M0W1001":
                return R.drawable.brand0; // Replace with your actual image resource
            case "M0W1002":
                return R.drawable.brand1; // Replace with your actual image resource
            case "M0W1003":
                return R.drawable.brand2; // Replace with your actual image resource
            case "M0W1009":
                return R.drawable.brand3; // Replace with your actual image resource
            default:
                return R.drawable.ganado_gradient; // Replace with your default image resource
        }
    }
}