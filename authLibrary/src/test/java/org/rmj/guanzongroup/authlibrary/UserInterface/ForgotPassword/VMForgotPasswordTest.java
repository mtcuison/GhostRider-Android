package org.rmj.guanzongroup.authlibrary.UserInterface.ForgotPassword;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

public class VMForgotPasswordTest extends TestCase {

    private VMForgotPassword mViewModel;

    public void setUp() throws Exception {
        super.setUp();
        mViewModel = Mockito.mock(VMForgotPassword.class);
    }

    public void tearDown() throws Exception {
    }

    @Test
    public void testEmailValidator(){
        String email = "mikegarcia8748@gmail.com";
        boolean result = mViewModel.isEmailValid(email);
        assertTrue(result);
    }
}