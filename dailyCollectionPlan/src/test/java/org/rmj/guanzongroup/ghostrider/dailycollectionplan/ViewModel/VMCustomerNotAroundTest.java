package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMCustomerNotAroundTest extends TestCase {

    private VMCustomerNotAround mViewModel;
    private String
            primeAddress,
            primeContact,
            rqstCode;

    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String STRING_TWO = "2";

    @Before
    public void setUp() {
        mViewModel = new VMCustomerNotAround(ApplicationProvider.getApplicationContext());

        mViewModel.setPrimeAddress(STRING_ZERO);
        mViewModel.getPrimeAddress().observeForever(primeAddress -> this.primeAddress = primeAddress);

        mViewModel.setRequestCode(STRING_TWO);
        mViewModel.getRequestCode().observeForever(rqstCode -> this.rqstCode = rqstCode);

        mViewModel.setPrimeContact(STRING_ONE);
        mViewModel.getPrimeContact().observeForever(primeContact -> this.primeContact = primeContact);
    }

    @Test
    public void testPrimeAddress() {
        assertEquals(STRING_ZERO, primeAddress);
    }

    @Test
    public void testRqstCode() {
        assertEquals(STRING_TWO, rqstCode);
    }

    @Test
    public void testPrimeContact() {
        assertEquals(STRING_ONE, primeContact);
    }




}