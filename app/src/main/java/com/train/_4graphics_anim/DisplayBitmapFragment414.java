package com.train._4graphics_anim;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.view.View;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wb on 17-7-20.
 */

public class DisplayBitmapFragment414 extends DisplayBitmapFragment {
    @Override
    protected void init(View view) {

    }

    // 管理Bitmap的内存使用

    // 管理Android 2.3.3及以下版本的内存使用
    // 在Android 2.3.3 (API level 10) 以及更低版本上，推荐使用recycle()方法。 如果在应用中显示了大量的Bitmap数据，我们很可能会遇到OutOfMemoryError的错误。 recycle()方法可以使得程序更快的释放内存。

    // Caution：只有当我们确定这个Bitmap不再需要用到的时候才应该使用recycle()。在执行recycle()方法之后，如果尝试绘制这个Bitmap， 我们将得到"Canvas: trying to use a recycled bitmap"的错误提示。
    // 下面的代码片段演示了使用recycle()的例子。它使用了引用计数的方法（mDisplayRefCount 与 mCacheRefCount）来追踪一个Bitmap目前是否有被显示或者是在缓存中。
    // 并且在下面列举的条件满足时，回收Bitmap：

    private int mCacheRefCount = 0;
    private int mDisplayRefCount = 0;
    boolean mHasBeenDisplayed = false;


    // Notify the drawable that the displayed state has changed.
    // Keep a count to determine when the drawable is no longer displayed.
    public void setIsDisplayed(boolean isDisplayed) {
        synchronized (this) {
            if (isDisplayed) {
                mDisplayRefCount++;
                mHasBeenDisplayed = true;
            } else {
                mDisplayRefCount--;
            }
        }
        // Check to see if recycle() can be called.
        checkState();
    }

    // Notify the drawable that the cache state has changed.
    // Keep a count to determine when the drawable is no longer being cached.
    public void setIsCached(boolean isCached) {
        synchronized (this) {
            if (isCached) {
                mCacheRefCount++;
            } else {
                mCacheRefCount--;
            }
        }
        // Check to see if recycle() can be called.
        checkState();
    }

    private synchronized void checkState() {
        // If the drawable cache and display ref counts = 0, and this drawable
        // has been displayed, then recycle.
        if (mCacheRefCount <= 0 && mDisplayRefCount <= 0 && mHasBeenDisplayed
                && hasValidBitmap()) {
            getBitmap().recycle();
        }
    }

    private synchronized boolean hasValidBitmap() {
        Bitmap bitmap = getBitmap();
        return bitmap != null && !bitmap.isRecycled();
    }

    Bitmap getBitmap() {
        return null;
    }


    // 管理Android 3.0及其以上版本的内存

    /*
    从Android 3.0 (API Level 11)开始，引进了BitmapFactory.Options.inBitmap字段。 如果使用了这个设置字段，
    decode方法会在加载Bitmap数据的时候去重用已经存在的Bitmap。这意味着Bitmap的内存是被重新利用的，这样可以提升性能，
    并且减少了内存的分配与回收。然而，使用inBitmap有一些限制，特别是在Android 4.4 (API level 19)之前，只有同等大小的位图才可以被重用。
     */


    // 保存Bitmap供以后使用

//
//    Set<SoftReference<Bitmap>> mReusableBitmaps;
//    private LruCache<String, BitmapDrawable> mMemoryCache;
//
//    // If you're running on Honeycomb or newer, create a
//    // synchronized HashSet of references to reusable bitmaps.
//    if (Utils.hasHoneycomb()) {
//        mReusableBitmaps =
//                Collections.synchronizedSet(new HashSet<SoftReference<Bitmap>>());
//    }
//
//    mMemoryCache = new LruCache<String, BitmapDrawable>(mCacheParams.memCacheSize) {
//
//        // Notify the removed entry that is no longer being cached.
//        @Override
//        protected void entryRemoved(boolean evicted, String key,
//                BitmapDrawable oldValue, BitmapDrawable newValue) {
//            if (RecyclingBitmapDrawable.class.isInstance(oldValue)) {
//                // The removed entry is a recycling drawable, so notify it
//                // that it has been removed from the memory cache.
//                ((RecyclingBitmapDrawable) oldValue).setIsCached(false);
//            } else {
//                // The removed entry is a standard BitmapDrawable.
//                if (Utils.hasHoneycomb()) {
//                    // We're running on Honeycomb or later, so add the bitmap
//                    // to a SoftReference set for possible use with inBitmap later.
//                    mReusableBitmaps.add
//                            (new SoftReference<Bitmap>(oldValue.getBitmap()));
//                }
//            }
//        }
//    //....
//    }



//
//    // 使用已经存在的Bitmap
//    // 在运行的程序中，decode方法会检查看是否存在可重用的Bitmap。 例如:
//    public static Bitmap decodeSampledBitmapFromFile(String filename,
//                                                     int reqWidth, int reqHeight, ImageCache cache) {
//
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        // ...
//        BitmapFactory.decodeFile(filename, options);
//        // ...
//
//        // If we're running on Honeycomb or newer, try to use inBitmap.
//        if (Utils.hasHoneycomb()) {
//            addInBitmapOptions(options, cache);
//        }
//        // ...
//        return BitmapFactory.decodeFile(filename, options);
//    }
//
//    /*
//    下面的代码是上述代码片段中，addInBitmapOptions()方法的具体实现。 它会为inBitmap查找一个已经存在的Bitmap，
//    并将它设置为inBitmap的值。 注意这个方法只有在找到合适且可重用的Bitmap时才会赋值给inBitmap（我们需要在赋值之前进行检查）：
//     */
//
//
//    private static void addInBitmapOptions(BitmapFactory.Options options,
//                                           ImageCache cache) {
//        // inBitmap only works with mutable bitmaps, so force the decoder to
//        // return mutable bitmaps.
//        options.inMutable = true;
//
//        if (cache != null) {
//            // Try to find a bitmap to use for inBitmap.
//            Bitmap inBitmap = cache.getBitmapFromReusableSet(options);
//
//            if (inBitmap != null) {
//                // If a suitable bitmap has been found, set it as the value of
//                // inBitmap.
//                options.inBitmap = inBitmap;
//            }
//        }
//    }
//
//    // This method iterates through the reusable bitmaps, looking for one
//    // to use for inBitmap:
//    protected Bitmap getBitmapFromReusableSet(BitmapFactory.Options options) {
//        Bitmap bitmap = null;
//
//        if (mReusableBitmaps != null && !mReusableBitmaps.isEmpty()) {
//            synchronized (mReusableBitmaps) {
//                final Iterator<SoftReference<Bitmap>> iterator
//                        = mReusableBitmaps.iterator();
//                Bitmap item;
//
//                while (iterator.hasNext()) {
//                    item = iterator.next().get();
//
//                    if (null != item && item.isMutable()) {
//                        // Check to see it the item can be used for inBitmap.
//                        if (canUseForInBitmap(item, options)) {
//                            bitmap = item;
//
//                            // Remove from reusable set so it can't be used again.
//                            iterator.remove();
//                            break;
//                        }
//                    } else {
//                        // Remove from the set if the reference has been cleared.
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//        return bitmap;
//    }
//
//    // 最后，下面这个方法判断候选Bitmap是否满足inBitmap的大小条件:
//
//    static boolean canUseForInBitmap(
//            Bitmap candidate, BitmapFactory.Options targetOptions) {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // From Android 4.4 (KitKat) onward we can re-use if the byte size of
//            // the new bitmap is smaller than the reusable bitmap candidate
//            // allocation byte count.
//            int width = targetOptions.outWidth / targetOptions.inSampleSize;
//            int height = targetOptions.outHeight / targetOptions.inSampleSize;
//            int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
//            return byteCount <= candidate.getAllocationByteCount();
//        }
//
//        // On earlier versions, the dimensions must match exactly and the inSampleSize must be 1
//        return candidate.getWidth() == targetOptions.outWidth
//                && candidate.getHeight() == targetOptions.outHeight
//                && targetOptions.inSampleSize == 1;
//    }
//
//    /**
//     * A helper function to return the byte usage per pixel of a bitmap based on its configuration.
//     */
//    static int getBytesPerPixel(Bitmap.Config config) {
//        if (config == Bitmap.Config.ARGB_8888) {
//            return 4;
//        } else if (config == Bitmap.Config.RGB_565) {
//            return 2;
//        } else if (config == Bitmap.Config.ARGB_4444) {
//            return 2;
//        } else if (config == Bitmap.Config.ALPHA_8) {
//            return 1;
//        }
//        return 1;
//    }
}
