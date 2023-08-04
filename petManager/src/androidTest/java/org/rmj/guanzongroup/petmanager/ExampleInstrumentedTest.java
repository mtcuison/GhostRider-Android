package org.rmj.guanzongroup.petmanager;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.ListView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.rmj.guanzongroup.petmanager.test", appContext.getPackageName());
    }
    @Test
    public void testFragmentTextInputAndSubmitButton() {
        // Launch the fragment using FragmentScenario
//        FragmentScenario<Fragment_LeaveApplication> fragmentScenario = fragmentScenarioRule.getFragmentScenario();
        String searchText = "Birthday";
        // Find the views in the fragment
        onView(withId(R.id.spn_leaveType))
                .perform(ViewActions.typeText(searchText));
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

        // You can add further assertions here to verify the expected behavior after the submit button is clicked.
        // For example, you can check if a Toast message is displayed or navigate to another fragment/activity.

        // Here's an example of checking a Toast message (requires androidx.test.espresso:espresso-contrib):
        // onView(withText("Submission Successful")).inRoot(withDecorView(not(activityScenario.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}