package com.gl.traindemohk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.train._4graphics_anim.AnimationActivity;

/**
 * Created by wb on 17-7-26.
 */

public class AndroidTrainFragment extends BaseFragment {

    private static final String TAG = AndroidTrainFragment.class.getSimpleName();

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "[onItemClick] position:" + i);
        Intent intent = new Intent();
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                intent.setClass(getActivity(), AnimationActivity.class);
                break;

        }
        startActivity(intent);
    }

    @Override
    String[] setData() {
        return new String[]{"1.开始", "2.Android 分享操作", "3.Android多媒体", "4.Android图像与动画", "5.Android网络操作", "6.联系人"};
    }
}
