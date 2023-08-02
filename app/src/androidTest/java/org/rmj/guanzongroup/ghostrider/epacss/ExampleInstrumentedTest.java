/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_SplashScreen;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
   @Rule
   public ActivityScenarioRule<Activity_SplashScreen> activityScenarioRule =
           new ActivityScenarioRule(Activity_SplashScreen.class);
   @Before
   public void setUp(){
      onView(withText("Guanzon Circle")).inRoot(isDialog()).perform(click());
   }
   /*@Test
    public void allowPermissions(){
       try {
           UiDevice device = UiDevice.getInstance(getInstrumentation());
           UiObject allowPermissions = device.findObject(new UiSelector()
                   .text("Only this Time")
                   .clickable(true)
                   .checkable(false)
                   .index(0));
           if (allowPermissions.exists()) {
               allowPermissions.click();
           }
       }catch (UiObjectNotFoundException e){
            e.printStackTrace();
       }
   }*/
    public void testLogin(){

    }
}