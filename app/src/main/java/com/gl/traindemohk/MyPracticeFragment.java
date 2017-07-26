package com.gl.traindemohk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

/**
 * Created by wb on 17-7-26.
 */

public class MyPracticeFragment extends BaseFragment {
    private static final String TAG = MyPracticeFragment.class.getSimpleName();
    @Override
    String[] setData() {
        return new String[]{"1.动画","2.二维码"};
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                break;
            default:
                break;
        }
    }
}
