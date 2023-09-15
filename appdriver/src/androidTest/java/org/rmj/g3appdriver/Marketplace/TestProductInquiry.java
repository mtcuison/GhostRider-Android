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
import org.rmj.g3appdriver.GConnect.Marketplace.Product.MpQuestion;
import org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo.ProductInquiry;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestProductInquiry {
    private static final String TAG = TestProductInquiry.class.getSimpleName();

    private Application instance;

    private MpQuestion poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new MpQuestion(instance);
    }

    @Test
    public void test01CreateInquiry() {
        if(poSys.SendProductInquiry("C0W122000003", "Sample Entry")){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02ImportInquiries() {
        List<ProductInquiry> loResult = poSys.GetProductInquiries("C0W122000003");
        if(loResult == null){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
