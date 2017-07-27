package com.gl.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-26.
 * 逐帧动画
 *
 * 原理：将一张张单独的图片连贯起来进行播放，类似于动画片
 */

public class FrameByFrameAnimation extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anim_frame,container,false);
    }
}
