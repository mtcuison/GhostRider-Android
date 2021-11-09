package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class VMCollectionListTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRemittanceValidation(){
        boolean hasRemitted;
        int paid = 0;
        String remittance = null;
        if(paid > 0){
            if(remittance == null){
                hasRemitted = false;
            } else if (!remittance.equalsIgnoreCase("0")
                    || !remittance.equalsIgnoreCase("0.0")){
                hasRemitted = false;
            } else {
                hasRemitted = true;
            }
        } else {
            hasRemitted = true;
        }
        assertTrue(hasRemitted);
    }

    @Test
    public void TestDCPPostTagging(){
        boolean postDcp;
        int accounts = 1;
        int unposted = 0;
        if(accounts == 0){
            postDcp = false;
        } else if(unposted == 0){
            postDcp = true;
        } else {
            postDcp = false;
        }

        assertFalse(postDcp);
    }
}