package org.rmj.g3appdriver.Marketplace;

import static org.junit.Assert.assertTrue;
import static org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.FilterType.DEFAULT;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.MpProducts;
import org.rmj.g3appdriver.GConnect.room.DataAccessObject.DProduct;
import org.rmj.g3appdriver.GConnect.room.Entities.EProducts;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestProducts {
    private static final String TAG = TestProducts.class.getSimpleName();

    private Application instance;

    private MpProducts poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new MpProducts(instance);
    }

    @Test
    public void test01ImportProducts() {
        if(poSys.ImportMPProducts()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02GetProductList() {
        poSys.GetProductsList(0, DEFAULT, null, null).observeForever(new Observer<List<DProduct.oProduct>>() {
            @Override
            public void onChanged(List<DProduct.oProduct> oProducts) {
                if(oProducts == null){
                    return;
                }

                if(oProducts.size() == 0){
                    return;
                }

                for(int x=0; x < oProducts.size(); x++){
                    Log.d(TAG, "Product Name : " + oProducts.get(x).sProdctNm);
                    Log.d(TAG, "Price : " + oProducts.get(x).sPricexxx);
                }
            }
        });
    }

//    @Test
    public void test03SearchProduct() {
        if(poSys.SearchProduct("Samsung")){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
