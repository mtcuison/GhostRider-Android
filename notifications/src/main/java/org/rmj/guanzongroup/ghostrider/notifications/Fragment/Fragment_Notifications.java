package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMNotifications;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Notifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Notifications extends Fragment {

    private VMNotifications mViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Notifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Notifications.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Notifications newInstance(String param1, String param2) {
        Fragment_Notifications fragment = new Fragment_Notifications();
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
        mViewModel = new ViewModelProvider(requireActivity()).get(VMNotifications.class);
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        viewPager.setAdapter(new ApplicationPageAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        mViewModel.GetUnreadPayslipCount().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                try{
                    if(count == null){
                        return;
                    }

                    if(count == 0){
                        Objects.requireNonNull(tabLayout.getTabAt(0)).removeBadge();
                        return;
                    }

                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(0)).getOrCreateBadge()).setNumber(count);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetUnreadMessagesCount().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                try{
                    if(count == null){
                        return;
                    }

                    if(count == 0){
                        Objects.requireNonNull(tabLayout.getTabAt(1)).removeBadge();
                        return;
                    }

                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(1)).getOrCreateBadge()).setNumber(count);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetUnreadNotificationCount().observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                try{
                    if(count == null){
                        return;
                    }

                    if(count == 0){
                        Objects.requireNonNull(tabLayout.getTabAt(2)).removeBadge();
                        return;
                    }

                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(2)).getOrCreateBadge()).setNumber(count);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private static class ApplicationPageAdapter extends FragmentStatePagerAdapter {

        private final String[] lsTitle = new String[]{"Payslip", "Message", "Notification"};

        private final Fragment[] loFragments = new Fragment[]{
                new Fragment_PayslipList(),
                new Fragment_MessageUsers(),
                new Fragment_NotificationList()};

        public ApplicationPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return loFragments[position];
        }

        @Override
        public int getCount() {
            return loFragments.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return lsTitle[position];
        }
    }
}