package com.dnagaraj.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {
        switch (tabPosition){
            case 0:
                return new ProfileTab();
            case 1:
                return new UsersTab();

            case 2:
                return new SharePicturesTab();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "Users";

            case 2:
                return "Share Photo";

            default:
                return null;
        }
    }
}
