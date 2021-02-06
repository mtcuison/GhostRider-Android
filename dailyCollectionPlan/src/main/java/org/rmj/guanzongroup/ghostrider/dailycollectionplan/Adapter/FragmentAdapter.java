package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private final Fragment[] mFragmentList;

    public FragmentAdapter(@NonNull FragmentManager fm, Fragment[] mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList[position];
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }
}
