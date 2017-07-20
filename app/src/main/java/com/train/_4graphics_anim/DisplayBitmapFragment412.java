package com.train._4graphics_anim;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.gl.traindemohk.R;

import java.lang.ref.WeakReference;

/**
 * Created by wb on 17-7-20.
 */

public class DisplayBitmapFragment412 extends DisplayBitmapFragment {
    Bitmap mPlaceHolderBitmap;

    // 非UI线程处理Bitmap
    @Override
    protected void init(View view) {
        mPlaceHolderBitmap = decodeSampledBitmapFromResource(getResources(), R.mipmap.ic_launcher_round, 100, 100);
        loadBitmap(R.drawable.display_thumbnail_bitmap, mImageView);
    }

    public void loadBitmap(int resId, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask412_1(imageView);
        task.execute(resId);
    }

    // 1,使用AsyncTask
    public class BitmapWorkerTask412_1 extends DisplayBitmapFragment.BitmapWorkerTask {

        public BitmapWorkerTask412_1(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            data = integers[0];

            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    // 2 处理并发问题
    // 思路：ImageView保存最近使用的AsyncTask的引用，这个引用可以在任务完成的时候再次读取检查。

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    // 在执行BitmapWorkerTask 之前，你需要创建一个AsyncDrawable并且将它绑定到目标控件ImageView中：
    public void loadBitmap2(int resId, ImageView imageView) {
        if (cancelPotentialWork(resId, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask412_2(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), mPlaceHolderBitmap, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }


    /**
     * cancelPotentialWork 方法检查是否有另一个正在执行的任务与该ImageView关联了起来，如果的确是这样，
     * 它通过执行cancel()方法来取消另一个任务。在少数情况下, 新创建的任务数据可能会与已经存在的任务相吻合，
     * 这样的话就不需要进行下一步动作了。
     *
     * @param data
     * @param imageView
     * @return
     */
    public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData == 0 || bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    /**
     * 在上面的代码中有一个辅助方法：getBitmapWorkerTask()，它被用作检索AsyncTask是否已经被分配到指定的ImageView:
     *
     * @param imageView
     * @return
     */
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    //最后一步是在BitmapWorkerTask的onPostExecute() 方法里面做更新操作:
    public class BitmapWorkerTask412_2 extends DisplayBitmapFragment.BitmapWorkerTask {

        public BitmapWorkerTask412_2(ImageView imageView) {
            super(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            data = integers[0];
            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
