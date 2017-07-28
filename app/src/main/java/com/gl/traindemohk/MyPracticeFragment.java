package com.gl.traindemohk;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.gl.animation.MyAnimActivity;

/**
 * Created by wb on 17-7-26.
 */

public class MyPracticeFragment extends BaseFragment {
    private static final String TAG = MyPracticeFragment.class.getSimpleName();
    @Override
    String[] setData() {
        return new String[]{"1.动画", "2.二维码", "3.高德定位及地图", "4.微信登录、支付、公众号", "5.支付宝支付", "6.热更新、热修复技术"};
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        switch (i){
            case 0:
                intent.setClass(getActivity(), MyAnimActivity.class);
                break;

            default:
                return;

        }
        // TODO 检查跳转
        startActivity(intent);
    }
}
