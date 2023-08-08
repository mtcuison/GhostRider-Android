package org.rmj.guanzongroup.pacitareward;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.app.Instrumentation;
import android.util.Log;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchList;
import org.rmj.guanzongroup.pacitareward.Fragments.Fragment_BranchList;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchList;


import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTestPacitaReward {
    FragmentScenario fragmentScenario;
    VMBranchList mviewModel;
    @Before
    public void setup() throws Exception{
        fragmentScenario = FragmentScenario.launchInContainer(Fragment_BranchList.class, null, R.style.GhostRiderMaterialTheme);
        fragmentScenario.moveToState(Lifecycle.State.RESUMED);

        //mviewModel = new VMBranchList(ApplicationProvider.getApplicationContext());
        AppConfigPreference.getInstance(ApplicationProvider.getApplicationContext()).setTestCase(true);
    }
    @Test
    public void getBranchList(){
       fragmentScenario.onFragment(fragment -> {
           mviewModel = new VMBranchList(ApplicationProvider.getApplicationContext());
           mviewModel.importCriteria();
           mviewModel.getBranchlist().observe(fragment.getViewLifecycleOwner(), new Observer<List<EBranchInfo>>() {
               @Override
               public void onChanged(List<EBranchInfo> eBranchInfos) {
                   assertTrue("List size is " + eBranchInfos.size(), eBranchInfos.size() > 0);
               }
           });
       });

        /*fragmentScenario.onFragment(fragment -> {
            RecyclerView recyclerView = fragment.getView().findViewById(R.id.branch_list);
            mviewModel = fragment.getDefaultViewModelProviderFactory().create(VMBranchList.class);
            mviewModel.importCriteria();
            mviewModel.getBranchlist().observeForever(new Observer<List<EBranchInfo>>() {
                @Override
                public void onChanged(List<EBranchInfo> eBranchInfos) {
                    RecyclerViewAdapter_BranchList rec_adapt =
                            new RecyclerViewAdapter_BranchList(eBranchInfos, new RecyclerViewAdapter_BranchList.OnBranchSelectListener() {
                                @Override
                                public void OnSelect(String BranchCode, String BranchName) {
                                }
                            });
                    rec_adapt.notifyDataSetChanged();
                    recyclerView.setAdapter(rec_adapt);
                    recyclerView.setLayoutManager(new LinearLayoutManager(fragment.getActivity()));

                    assertTrue("List size is " + eBranchInfos.size(), eBranchInfos.size() > 0);
                }
            });
        });*/
    }
}