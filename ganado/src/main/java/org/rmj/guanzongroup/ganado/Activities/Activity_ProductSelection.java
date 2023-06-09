package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.imageview.ShapeableImageView;

import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.guanzongroup.ganado.Adapter.ProductSelectionAdapter;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMProductSelection;

import java.util.List;
import java.util.Objects;

public class Activity_ProductSelection extends AppCompatActivity {
    private RecyclerView rvMcModel;
    private TextView txtBrandNm;
    private VMProductSelection mViewModel;
    private ProductSelectionAdapter adapter;
    private SearchView searchView;
    private List<EMcModel> poModel;
    private List<EMcModel> poModelFilteredList;

    private ShapeableImageView brandselectedimg;
    private int backgroundResId;
    private String backgroundResIdCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);
        initView();

        backgroundResId = getIntent().getIntExtra("background", 0);
        backgroundResIdCat = getIntent().getStringExtra("backgroundold");
        brandselectedimg = findViewById(R.id.imageprodselection);
        brandselectedimg.setImageResource(backgroundResId);
        searchView = findViewById(R.id.searchview);
        mViewModel = new ViewModelProvider(Activity_ProductSelection.this).get(VMProductSelection.class);

        mViewModel.GetModelsList(getIntent().getStringExtra("lsBrandID")).observe(Activity_ProductSelection.this, eMcModels -> {
            if (eMcModels.size() > 0) {
                adapter = new ProductSelectionAdapter(eMcModels, new ProductSelectionAdapter.OnModelClickListener() {
                    @Override
                    public void OnClick(String ModelID, String BrandID, String ImgLink) {
                        Intent intent = new Intent(Activity_ProductSelection.this, Activity_ProductInquiry.class);
                        intent.putExtra("lsBrandID", BrandID);
                        intent.putExtra("lsModelID", ModelID);
                        intent.putExtra("lsBrandNm", getIntent().getStringExtra("lsBrandNm"));
                        intent.putExtra("lsImgLink", ImgLink);
                        intent.putExtra("bgbrandimage", backgroundResId);
                        intent.putExtra("backgroundold", backgroundResIdCat);
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
                        finish();
                    }
                });

                rvMcModel.setAdapter(adapter);
                rvMcModel.setLayoutManager(new GridLayoutManager(Activity_ProductSelection.this, 2, RecyclerView.VERTICAL, false));

            }
        });
        txtBrandNm.setText(getIntent().getStringExtra("lsBrandNm"));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterModel(newText);
                return true;
            }
        });
    }

    private void initView() {
        rvMcModel = findViewById(R.id.rvMcModel);
        txtBrandNm = findViewById(R.id.lblBrand);

        MaterialToolbar toolbar = findViewById(R.id.toolbar_selection);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            if (item.getItemId() == android.R.id.home) {
                Intent loIntent = new Intent(Activity_ProductSelection.this, Activity_BrandSelection.class);
                loIntent.putExtra("background", backgroundResIdCat);
                startActivity(loIntent);
                overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onBackPressed () {
            Intent loIntent = new Intent(Activity_ProductSelection.this, Activity_BrandSelection.class);
            loIntent.putExtra("background", backgroundResIdCat);
            overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
            startActivity(loIntent);
            finish();
        }

        @Override
        protected void onDestroy () {
            getViewModelStore().clear();
            super.onDestroy();
        }
    }
