package com.train._4graphics_anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-20.
 */

public class DisplayBitmapFragment415 extends DisplayBitmapFragment {
    @Override
    protected void init(View view) {
        mAdapter = new ImagePagerAdapter(getFragmentManager(), imageResIds.length);
        mPager = (ViewPager)view. findViewById(R.id.pager);
    }

    public static final String EXTRA_IMAGE = "extra_image";
    private ImagePagerAdapter mAdapter;
    private ViewPager mPager;

    // A static data set to back the ViewPager adapter
    public final static Integer[] imageResIds = new Integer[] {

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;

        public ImagePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
//            return ImageDetailFragment.newInstance(position);
        }
    }

}
