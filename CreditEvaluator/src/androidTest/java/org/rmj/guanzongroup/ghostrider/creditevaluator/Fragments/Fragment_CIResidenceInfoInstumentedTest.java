package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.widget.RadioGroup;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_CIApplication;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIResidenceInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;
import static org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstants.SUB_FOLDER_DCP;

@RunWith(AndroidJUnit4.class)
public class Fragment_CIResidenceInfoInstumentedTest {

    private CIResidenceInfoModel residenceInfo;
    private MessageBox poMessage;
    private ImageFileCreator poFilexx;
    private EImageInfo poImageInfo;
    private TextInputEditText landMark;
    private RadioGroup rgOwner, rgHouseHold , rgHouseType;
    private Fragment_CIResidenceInfo fg;

    @Rule
    public ActivityScenarioRule<Activity_CIApplication> activityRule = new ActivityScenarioRule<Activity_CIApplication>(Activity_CIApplication.class);
    @Before
    public void setUp() throws Exception {
        poFilexx = new ImageFileCreator(InstrumentationRegistry.getInstrumentation().getContext(), SUB_FOLDER_DCP, "C0YNQ2100035");
        poImageInfo = new EImageInfo();
        residenceInfo = new CIResidenceInfoModel();
        poFilexx.setTransNox("C0YNQ2100035");
        poMessage = new MessageBox(InstrumentationRegistry.getInstrumentation().getContext());
        fg = new Fragment_CIResidenceInfo();

    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void saveResidenceInfo(){
        onView(allOf(withText("Owner"),
                withParent(withId(R.id.rg_ci_ownership))))
                .check(matches(isChecked()));
        onView(withId(R.id.tie_landmark))
                .perform(ViewActions.typeText("Sample LandMark"));
    }
}