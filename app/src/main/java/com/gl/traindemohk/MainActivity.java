package com.gl.traindemohk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static final int NUM_PAGES = 2;
    ViewPager mViewPager;
    private static final String TRAIN_TAG = "android_train_Fragment";
    private static final String PRACTICE_TAG = "my_practice_Fragment";

    private AndroidTrainFragment trainFragment;
    private MyPracticeFragment practiceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);

        createFragment(savedInstanceState);
        mViewPager.addOnPageChangeListener(this);

        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

    }

    private void createFragment(Bundle savedInstanceState) {
        final FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState != null) {

            trainFragment = (AndroidTrainFragment) manager.findFragmentByTag(TRAIN_TAG);
            practiceFragment = (MyPracticeFragment) manager.findFragmentByTag(PRACTICE_TAG);

        } else {
            trainFragment = (AndroidTrainFragment) manager.findFragmentByTag(TRAIN_TAG);
            practiceFragment = (MyPracticeFragment) manager.findFragmentByTag(PRACTICE_TAG);

            if (trainFragment == null) {
                trainFragment = new AndroidTrainFragment();
            }
            if (practiceFragment == null) {
                practiceFragment = new MyPracticeFragment();
            }

            final FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.main_viewpager, trainFragment, TRAIN_TAG);
            transaction.add(R.id.main_viewpager, practiceFragment, PRACTICE_TAG);
            transaction.hide(trainFragment);
            transaction.hide(practiceFragment);
            transaction.commit();

        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        switch (position) {
            case 0:
                getSupportActionBar().setTitle(getString(R.string.app_name));
                break;
            case 1:
                getSupportActionBar().setTitle(getString(R.string.title_fragment_my_practice));
                break;
            default:
                getSupportActionBar().setTitle(getString(R.string.app_name));
                break;

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPageAdapter extends FragmentStatePagerAdapter {


        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.i("wb", "[getItem]" + position);
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = trainFragment;
                    break;
                case 1:
                    fragment = practiceFragment;
                    break;
                default:
                    break;
            }

            return fragment;
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
