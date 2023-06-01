package org.rmj.guanzongroup.ganado.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ganado.Adapter.RecyclerViewAdapter_BrandSelection;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMBrandList;


public class Activity_BrandSelection extends AppCompatActivity {

    private VMBrandList mViewModel;
    private RecyclerView rvc_brandlist;
    private RecyclerViewAdapter_BrandSelection rec_brandList;
    private LoadDialog poLoad;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_selection);
        intWidgets();
        mViewModel = new ViewModelProvider(this).get(VMBrandList.class);
//
//        rvc_brandlist = findViewById(R.id.rv_brands);
//
//
//
//        mViewModel.importCriteria();
//        mViewModel.getBrandList().observe(Activity_BrandSelection.this, new Observer<List<EMcBrand>>() {
//            @Override
//            public void onChanged(List<EMcBrand> eBrandInfos) {
//                if (eBrandInfos.size() <= 0){
//                }
//                rec_brandList = new RecyclerViewAdapter_BrandSelection(eBrandInfos, new RecyclerViewAdapter_BrandSelection.OnBrandSelectListener() {
//                    @Override
//                    public void OnSelect(String BrandID, String BrandName) {
//
//                        }
//                });
//
//                rec_brandList.notifyDataSetChanged();
//                rvc_brandlist.setAdapter(rec_brandList);
//                rvc_brandlist.setLayoutManager(new LinearLayoutManager(Activity_BrandSelection.this));
//            }
//        });
        mViewModel.getBrandList().observe(Activity_BrandSelection.this, brandList ->{
            if (brandList.size() > 0){
                rec_brandList = new RecyclerViewAdapter_BrandSelection(brandList, new RecyclerViewAdapter_BrandSelection.OnBrandSelectListener() {
                    @Override
                    public void OnSelect(String BrandID, String BrandName) {

                    }
                });

                rvc_brandlist.setAdapter(rec_brandList);
                rvc_brandlist.setLayoutManager(new GridLayoutManager(Activity_BrandSelection.this,2,RecyclerView.VERTICAL,false));

                rec_brandList.notifyDataSetChanged();
            }
        });
    }
    private void intWidgets(){
        rvc_brandlist = findViewById(R.id.rv_brands);
    }
}