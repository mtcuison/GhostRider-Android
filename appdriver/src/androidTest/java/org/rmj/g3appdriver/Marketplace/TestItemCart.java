package org.rmj.g3appdriver.Marketplace;


import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GConnect.Marketplace.Cart.MpCart;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.MpProducts;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestItemCart {
    private static final String TAG = TestItemCart.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;

    private MpProducts poProdct;
    private MpCart poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poProdct = new MpProducts(instance);
        poSys = new MpCart(instance);
    }

    @Test
    public void test01ImportItemCart() {
        if(poSys.ImportMarketPlaceItemCart()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02AddToCart() {
        if(poSys.AddToCart("C00122000001", 2)){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
