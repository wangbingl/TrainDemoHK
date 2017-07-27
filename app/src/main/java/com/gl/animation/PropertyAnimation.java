package com.gl.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-26.
 * <p>
 * <p>
 * 属性动画
 * <p>
 * 原理：
 * 与补间动画的区别：补间动画是只能够作用在View上的，不能非View的对象进行动画操作。
 * http://blog.csdn.net/guolin_blog/article/details/43536355
 */

public class PropertyAnimation extends Fragment {

    // 目标：绘画 线性直线 y = 2x;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anim_property,container,false);
    }

    private ImageView mImageView;
    private Button mButton;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.image_for_property_anim);
        mButton = view.findViewById(R.id.btn_start_property_anim);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
    }

    public void startAnimation(){
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView,"alpha",1f,0.3f,1f,0f,1f);
//        animator.setDuration(1200);
//        animator.start();

        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView,"rotation",0f,270f,200f,360f);
        animator.setDuration(2000);
        animator.start();
    }
}
