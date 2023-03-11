package org.rmj.guanzongroup.ghostrider.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.ghostrider.Adapter.FragmentAdapter;
import org.rmj.guanzongroup.ghostrider.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PanaloContainer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PanaloContainer extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Fragment[] fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_PanaloContainer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_PanaloContainer.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PanaloContainer newInstance(String param1, String param2) {
        Fragment_PanaloContainer fragment = new Fragment_PanaloContainer();
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
        view= inflater.inflate(R.layout.fragment_panalo_container, container, false);
        fragment = new Fragment[]{new Fragment_Raffle(), new Fragment_Rewards()};
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.ViewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Raffle Draw"));
        tabLayout.addTab(tabLayout.newTab().setText("Redemption"));

        viewPager.setAdapter(new FragmentAdapter(getChildFragmentManager(), fragment));

        if(fragment.length > 1){
            tabLayout.setupWithViewPager(viewPager);
            Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Raffle Draw");
            Objects.requireNonNull(tabLayout.getTabAt(1)).setText("Redemption");
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}