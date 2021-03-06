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
 * <p>
 * 补间动画
 *
 * 原理：它可以对View进行一系列的动画操作，包括淡入淡出、缩放、平移、旋转四种，并且我们还可以借助AnimationSet来将这些动画效果组合起来使用，除此之外还可以通过配置Interpolator来控制动画的播放速度
 */

public class TweenedAnimation extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anim_tweened,container,false);
    }
}
