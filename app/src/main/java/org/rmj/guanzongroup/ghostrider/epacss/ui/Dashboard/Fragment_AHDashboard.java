package org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.rmj.guanzongroup.ghostrider.Fragment.Fragment_PanaloContainer;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Fragment.Fragment_AreaMonitor;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Home_AH;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_Notifications;
import org.rmj.guanzongroup.petmanager.Fragment.Fragment_SelfieLog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_AHDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_AHDashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_AHDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AHDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_AHDashboard newInstance(String param1, String param2) {
        Fragment_AHDashboard fragment = new Fragment_AHDashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ah_dashboard, container, false);

        Fragment[] loFragments = new Fragment[]{
                new Fragment_Home_AH(),
                new Fragment_SelfieLog(),
                new Fragment_PanaloContainer(),
                new Fragment_AreaMonitor(),
                new Fragment_Notifications()};

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), loFragments));
        BottomNavigationView botNav = view.findViewById(R.id.botNav);
        botNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav_selfielog:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_panalo:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.nav_bullseye:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.nav_notifications:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return true;
        });

        return view;
    }
}