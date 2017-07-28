package com.gl.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

public class PropertyAnimation extends Fragment implements View.OnClickListener {

    // 目标：绘画 线性直线 y = 2x;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anim_property,container,false);
    }

    private ImageView mImageView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.image_for_property_anim);

        view.findViewById(R.id.btn_start_alpha_prop_anim).setOnClickListener(this);
        view.findViewById(R.id.btn_start_move_prop_anim).setOnClickListener(this);
        view.findViewById(R.id.btn_start_rotation_prop_anim).setOnClickListener(this);
        view.findViewById(R.id.btn_start_scale_prop_anim).setOnClickListener(this);
        view.findViewById(R.id.btn_start_set_prop_anim).setOnClickListener(this);
        view.findViewById(R.id.btn_start_xml_prop_anim).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_alpha_prop_anim:
                startAlphaAnimation();
                break;
            case R.id.btn_start_move_prop_anim:
                startMoveAnimation();
                break;
            case R.id.btn_start_rotation_prop_anim:
                startRotationAnimation();
                break;
            case R.id.btn_start_scale_prop_anim:
                startScaleAnimation();
                break;
            case R.id.btn_start_set_prop_anim:
                startSetAnimation();
                break;
            case R.id.btn_start_xml_prop_anim:
                startXmlPropAnimation();
                break;
        }
    }

    private void startXmlPropAnimation() {
        Animator anim = AnimatorInflater.loadAnimator(getActivity(), R.animator.my_property_anim);
        anim.setTarget(mImageView);
        anim.start();
    }

    private void startAlphaAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0.3f, 1f, 0f, 1f);
        animator.setDuration(1200);
        animator.start();
    }

    private void startMoveAnimation() {
        float y = mImageView.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "translationY", y, 500f, 300f, y);
        animator.setDuration(3000);
        animator.start();

    }

    private void startRotationAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView,"rotation",0f,270f,200f,360f);
        animator.setDuration(2000);
        animator.start();
    }

    private void startScaleAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "scaleX", 1f, 3f, 1f);
        animator.setDuration(1000);
        animator.start();
    }

    private void startSetAnimation() {
        float x = mImageView.getTranslationX();
        float y = mImageView.getTranslationY();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mImageView, "translationX", x, 800, x);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mImageView, "translationY", y, 800, y);
        ObjectAnimator animatorR = ObjectAnimator.ofFloat(mImageView, "rotation", 0f, 360f, 180f, 720f, 0f);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY).with(animatorR);
        animSet.setDuration(6000);
        animSet.start();

        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(getActivity(), "动画结束！", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
