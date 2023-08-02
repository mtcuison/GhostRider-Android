/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.petmanager;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.petmanager.Fragment.Fragment_LeaveApplication;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestUILeaveApplication {



    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.rmj.guanzongroup.petmanager.test", appContext.getPackageName());


    }
    @Rule
//    public ActivityScenarioRule<TestActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(TestActivity.class);
    public ActivityTestRule<TestActivity> activityRule = new ActivityTestRule<>(TestActivity.class);

    @After
    public void tearDown() {
        // Perform any cleanup after the test
        Intents.release();
    }

    @Before
    public void setUp() throws Exception {
//            this.instance = ApplicationProvider.getApplicationContext();
//            AppConfigPreference.getInstance(instance).setProductID("GuanzonApp");
//            AppConfigPreference.getInstance(instance).setTestCase(true);
//            this.poAccount = new AccountMaster(instance);
//            this.poSys = poAccount.initGuanzonApp().getInstance(Auth.AUTHENTICATE);

    }

    @Test
    public void testApplication(){
        FragmentScenario<Fragment_LeaveApplication> fragmentScenario = FragmentScenario.launchInContainer(Fragment_LeaveApplication.class);
        fragmentScenario.onFragment(Fragment::onStart);
        if (testLeavetype(true)){
            Log.d("System Success" , "Leave Type");
        }
        if (testDateFrom(true)){
            Log.d("System Success" , "Date From");
        }
        if (testDateThru(true)){
            Log.d("System Success" , "Date Thru");
        }
        if (testPurpose(true)){
            Log.d("System Success" , "Purpose");
        }
//            if (testSubmitApplication(true)){
//                Log.d("System Success" , "Submit");
//            }
        onView(withId(R.id.btn_submit))
                .perform(ViewActions.click());
    }


    private boolean testLeavetype(boolean isSuccess) {

        String searchText = "Birthday";
        try {
            onView(withId(R.id.spn_leaveType))
                    .perform(ViewActions.typeText(searchText));

            // Wait for the suggestions to appear (you may need to adjust this delay as per your app's behavior)
            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Click on the first suggestion in the dropdown (assuming suggestions are visible now)
            onData(ViewMatchers.withText(searchText))
                    .inAdapterView(ViewMatchers.isAssignableFrom(ListView.class))
                    .atPosition(0)
                    .perform(ViewActions.click());

            // Verify if the selected suggestion is populated in the AutoCompleteTextView
            onView(withId(R.id.spn_leaveType))
                    .check(ViewAssertions.matches(ViewMatchers.withText(searchText)));

            isSuccess = true;
        }catch (Exception e){
            isSuccess=false;
        }
        return isSuccess;
    }

    private  boolean testDateFrom(boolean isSuccess) {

        String xDate = "August 25, 2023";
        try{
            onView(withId(R.id.txt_dateFrom))
                    .perform(ViewActions.click());
            onView(ViewMatchers.withClassName(equalTo(DatePicker.class.getName())))
                    .perform(PickerActions.setDate(2023, 8, 25));
            onView(withId(android.R.id.button1))
                    .perform(ViewActions.click());
            onView(withId(R.id.txt_dateFrom))
                    .check(ViewAssertions.matches(ViewMatchers.withText(xDate)));
        }catch (Exception e){
            isSuccess = false;
        }
        return isSuccess;
    }
    private  boolean testDateThru(boolean isSuccess) {

        String xDate = "August 25, 2023";
        try{
            onView(withId(R.id.txt_dateFrom))
                    .perform(ViewActions.click());
            onView(ViewMatchers.withClassName(equalTo(DatePicker.class.getName())))
                    .perform(PickerActions.setDate(2023, 8, 25));
            onView(withId(android.R.id.button1))
                    .perform(ViewActions.click());
            onView(withId(R.id.txt_dateFrom))
                    .check(ViewAssertions.matches(ViewMatchers.withText(xDate)));
        }catch (Exception e){
            isSuccess = false;
        }
        return isSuccess;
    }

    private  boolean testPurpose(boolean isSuccess){

        String xRemarks = "Birthday ko na!";
        try{
            onView(withId(R.id.txt_remarks))
                    .perform(ViewActions.typeText(xRemarks), ViewActions.closeSoftKeyboard());
            isSuccess =true;
        }catch (Exception e){
            isSuccess = false;
        }
        return  isSuccess;
    }
    private  boolean testSubmitApplication(boolean isSuccess){

        try{
            onView(withId(R.id.btn_submit))
                    .perform(ViewActions.click());
            isSuccess =true;
        }catch (Exception e){
            isSuccess = false;
        }
        return  isSuccess;
    }
}