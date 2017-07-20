package com.train._4graphics_anim;

import android.view.View;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-20.
 */

public class DisplayBitmapFragment411 extends DisplayBitmapFragment {
    // 1.1 读取位图的尺寸与类型
    /*
    具体做法：
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
     */

    // 1.2 加载一个按比例缩小的版本到内存中
    @Override
    protected void init(View view) {
        mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.display_thumbnail_bitmap, 100, 100));
    }


}
