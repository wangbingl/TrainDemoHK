package com.train._4graphics_anim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;

import com.gl.traindemohk.R;

public class DisplayBitmapActivity extends AppCompatActivity {
    //高效加载大图


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bitmap);

        if (savedInstanceState == null) {
            DisplayBitmapFragment411 fragment411 = new DisplayBitmapFragment411();
            getSupportFragmentManager().beginTransaction().add(R.id.display_bitmap_fragment_container, fragment411).commit();
        }


        /*

        ...
        RetainFragment retainFragment =
            RetainFragment.findOrCreateRetainFragment(getFragmentManager());
        mMemoryCache = retainFragment.mRetainedCache;
            if (mMemoryCache == null) {
            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            ... // Initialize cache here as usual
            }
            retainFragment.mRetainedCache = mMemoryCache;
        }
        ...


         */


    }


    // 处理配置改变
    // 屏幕方向的改变会导致Android中当前显示的Activity先被销毁然后重启。
    // 我们需要在配置改变时避免重新处理所有的图片，这样才能提供给用户一个良好的平滑过度的体验。

    // 幸运的是，在前面介绍使用内存缓存的部分，我们已经知道了如何建立内存缓存。
    // 这个缓存可以通过调用setRetainInstance(true))保留一个Fragment实例的方法把缓存传递给新的Activity。
    // 在这个Activity被重新创建之后，这个保留的Fragment会被重新附着上。这样你就可以访问缓存对象了，
    // 从缓存中获取到图片信息并快速的重新显示到ImageView上。

    public static class RetainFragment extends Fragment {
        private static final String TAG = "RetainFragment";
        public LruCache<String, Bitmap> mRetainedCache;

        public RetainFragment() {
        }


        public static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
            RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
            if (fragment == null) {
                fragment = new RetainFragment();
                fm.beginTransaction().add(fragment, TAG).commit();
            }
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }


        /*

        为了测试上面的效果，可以尝试在保留Fragment与没有这样做的情况下旋转屏幕。我们会发现当保留缓存时，从内存缓存
        中重新绘制几乎没有延迟的现象。 内存缓存中没有的图片可能存储在磁盘缓存中。如果两个缓存中都没有，则图像会像平时正常流程一样被处理。


         */
    }


}
