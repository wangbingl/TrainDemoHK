package com.train._4graphics_anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import com.gl.traindemohk.R;

import java.io.File;

import static android.os.Environment.isExternalStorageRemovable;

/**
 * Created by wb on 17-7-20.
 */

public class DisplayBitmapFragment413 extends DisplayBitmapFragment {

    // 缓存Bitmap  这一课会介绍在加载多张Bitmap时使用内存缓存与磁盘缓存来提高响应速度与UI流畅度。

    // 使用内存缓存
    // LruCache类 特别适合用来缓存Bitmaps，它使用一个强引用（strong referenced）的LinkedHashMap保存最近引用的对象，
    // 并且在缓存超出设置大小的时候剔除（evict）最近最少使用到的对象。

    private LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void init(View view) {

        // Initialize memory cache

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        // 翻译：//获取最大可用VM内存，超过此数量将抛出OutOfMemory异常。 存储在千字节，因为LruCache需要在其构造函数中
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        //使用该内存缓存的可用内存的1/8。
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                // 翻译：//缓存大小将以千字节而不是东西的个数。
                return value.getByteCount() / 1024;
            }
        };


        // 第2节 Initialize disk cache on background thread
        File cacheDir = getDiskCacheDir(this.getContext(), DISK_CACHE_SUBDIR);
        new InitDiskCacheTask().execute(cacheDir);
    }

    /*
    Note:在上面的例子中, 有1/8的内存空间被用作缓存。 这意味着在常见的设备上（hdpi），最少大概有4MB的缓存空间（32/8）。
    如果一个填满图片的GridView控件放置在800x480像素的手机屏幕上，大概会花费1.5MB的缓存空间（800x480x4 bytes），因此缓存的容量大概可以缓存2.5页的图片内容。
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        final String imageKey = String.valueOf(resId);

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageResource(R.mipmap.ic_launcher);

//            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
//            task.execute(resId);
        }
    }

    // 上面的程序中 BitmapWorkerTask 需要把解析好的Bitmap添加到内存缓存中：
//    @Override
//    protected Bitmap doInBackground(Integer... params) {
//        final Bitmap bitmap = decodeSampledBitmapFromResource(
//                getResources(), params[0], 100, 100));
//        addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
//        return bitmap;
//    }


    // 使用磁盘缓存

    // 这一节的范例代码中使用了一个从Android源码中剥离出来的DiskLruCache。改进过的范例代码在已有内存缓存的基础上增加磁盘缓存的功能。  DiskLruCache采用反射获得。
    private MyDiskLruCache mDiskLruCache;
    private final Object mDiskCacheLock = new Object();
    private boolean mDiskCacheStarting = true;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    // Creates a unique subdirectory of the designated app cache directory. Tries to use external
    // but if not mounted, falls back on internal storage.
    public static File getDiskCacheDir(Context context, String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir


        final String cachePath =
                Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                        !isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() :
                        context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }

    class InitDiskCacheTask extends AsyncTask<File, Void, Void> {

        @Override
        protected Void doInBackground(File... files) {
            synchronized (mDiskCacheLock) {
                File cacheDir = files[0];
                mDiskLruCache = MyDiskLruCache.open(cacheDir, DISK_CACHE_SIZE);
                mDiskCacheStarting = false; // Finished initialization
                mDiskCacheLock.notifyAll(); // Wake any waiting threads // 唤醒任何等待的线程
            }

            return null;
        }
    }

    class BitmapWorkerTask413 extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Integer... params) {
            final String imageKey = String.valueOf(params[0]);

            // Check disk cache in background thread
            Bitmap bitmap = getBitmapFromDiskCache(imageKey);

            if (bitmap == null) { // Not found in disk cache
                // Process as normal
                bitmap = decodeSampledBitmapFromResource(getResources(), params[0], 100, 100);
            }

            // Add final bitmap to caches
            addBitmapToCache(imageKey, bitmap);
            return bitmap;
        }
    }

    public void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

        // Also add to disk cache
        synchronized (mDiskCacheLock) {
            if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
                mDiskLruCache.put(key, bitmap);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        synchronized (mDiskCacheLock) {
            // Wait while disk cache is started from background thread
            while (mDiskCacheStarting) {
                try {
                    mDiskCacheLock.wait();
                } catch (InterruptedException e) {
                }
            }
            if (mDiskLruCache != null) {
                return mDiskLruCache.get(key);
            }
        }
        return null;
    }


}
