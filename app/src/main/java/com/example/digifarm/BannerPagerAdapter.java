package com.example.digifarm;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class BannerPagerAdapter extends FragmentStatePagerAdapter {

    private List<Integer> bannerImages;

    public BannerPagerAdapter(FragmentManager fm, List<Integer> bannerImages) {
        super(fm);
        this.bannerImages = bannerImages;
    }

    @Override
    public Fragment getItem(int position) {
        return BannerFragment.newInstance(bannerImages.get(position));
    }

    @Override
    public int getCount() {
        return bannerImages.size();
    }
}

