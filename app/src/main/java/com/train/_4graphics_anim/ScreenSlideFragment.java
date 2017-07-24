package com.train._4graphics_anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gl.traindemohk.R;

public class ScreenSlideFragment extends Fragment {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_screen_slide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = view.findViewById(R.id.screen_slide_view_pager);
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(getFragmentManager());
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private static final String TAG = "ScreenSlidePage_Fragment";

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = getFragmentManager().findFragmentByTag(TAG);
            if (fragment == null)
                fragment = new ScreenSlidePageFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    // 用PageTransformer自定义动画
    /*
    原理：
    要展示不同于默认滑屏效果的动画，我们需要实现ViewPager.PageTransformer接口，然后把它补充到ViewPager里就行了。
    这个接口只暴露了一个方法，transformPage()。每次界面切换，这个方法都会为每个可见页面（通常只有一个页面可见）和刚消失的相邻页面调用一次。

    在transformPage()的实现中，基于当前屏幕显示的页面的position决定哪些页面需要被动画转换，这样我们就能创建自己的动画。
    position参数表示特定页面相对于屏幕中的页面的位置。
    当某一页面填充屏幕，它的值为0。当页面刚向屏幕右侧方向被拖走，它的值为1。如果用户在页面1和页面2间滑动到一半，
    那么页面1的position为-0.5并且页面2的position为 0.5。根据屏幕上页面的position，我们可以通过setAlpha()，setTranslationX()或setScaleY()这些方法设定页面属性来自定义滑动动画。

    当我们实现了PageTransformer后，用我们的实现调用setPageTransformer()来应用这些自定义动画。例如，如果我们有一个叫做ZoomOutPageTransformer的PageTransformer，可以这样设置自定义动画：

    ViewPager mPager = (ViewPager) findViewById(R.id.pager);
    ...
    mPager.setPageTransformer(true, new ZoomOutPageTransformer());
     */

    // 动画目标：当在相邻界面滑动时，这个Page Transformer使页面收缩并褪色。当页面越靠近中心，它将渐渐还原到正常大小并且图像渐入。

    class ZoomOutPageTransformer implements ViewPager.PageTransformer {

        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            int pageHeight = page.getHeight();

            if (position < -1) { // [-Infinity(负无穷大),-1)
                // This page is way off-screen to the left.  左边离开屏幕
                page.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well  做成缩小动画
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

                // 定义X 位移

                // 垂直间距
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                // 水平间距
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;

                if (position < 0) {
                    page.setTranslationX(horzMargin - vertMargin / 2);

                } else {
                    page.setTranslationX(-horzMargin + vertMargin / 2);
                }
                // 定义缩放
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);

                // 定义透明度
                page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // [1, 无穷大]
                page.setAlpha(0);

            }
        }
    }

    // 动画2：这个Page Transformer使用默认动画的屏幕左滑动画。但是为右滑使用一种“潜藏”效果的动画。潜藏动画将page淡出，并且线性缩小它。


}
