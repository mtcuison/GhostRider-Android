package org.rmj.guanzongroup.ghostrider.epacss.ui.HomeContainer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.appConstants;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.FragmentDashboard;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_DisbursementInfo;

public class Fragment_MainContainer extends Fragment {

    private VMMainContainer mViewModel;

    private static Fragment_MainContainer instance;
    private static ViewPager viewPager;
    private String transNox;
    public static Fragment_MainContainer newInstance() {
        return new Fragment_MainContainer();
    }

    public static Fragment_MainContainer getInstance(){
        return instance;
    }

    public String getTransNox(){
        return transNox;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_container, container, false);
        instance = this;
        viewPager = view.findViewById(R.id.viewpager_mainContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMainContainer.class);

        viewPager.setAdapter(new FragmentAdapter(getParentFragmentManager(), appConstants.APPLICATION_HOME_PAGES));
        // TODO: Use the ViewModel
    }
    public static void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

}