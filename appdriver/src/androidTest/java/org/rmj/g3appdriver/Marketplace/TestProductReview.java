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
import org.rmj.g3appdriver.GConnect.Marketplace.Product.MpProducts;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.MpReview;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.OrderReview;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.ProductReview;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestProductReview {
    private static final String TAG = TestProductReview.class.getSimpleName();

    private Application instance;

    private MpReview poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new MpReview(instance);
    }

    @Test
    public void test01ReviewProduct() {
        OrderReview loReview = new OrderReview("GK0123000003", "C00122000006", 5, "Unit testing entry");
        if(poSys.SendReview(loReview)){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02ImportProductReviews() {
        List<ProductReview> loResult = poSys.GetProductRatings("C00122000006");
        if(loResult == null){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
