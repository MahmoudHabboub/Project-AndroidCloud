package com.example.coronavirusproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    List<String> fragmentListTitle;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment>fragmentList, List<String> fragmentListTitle) {
        super(fm);
        this.fragmentList=fragmentList;
        this.fragmentListTitle=fragmentListTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentListTitle.get(position);
    }
}
