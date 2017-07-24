package com.train._4graphics_anim;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gl.traindemohk.R;

public class CrossFadeFragment extends Fragment {

    // 步骤 一： 设置动画

    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_crossfade, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContentView = view.findViewById(R.id.content);
        mLoadingView = view.findViewById(R.id.loading_spinner);

        // Initially hide the content view.
        mContentView.setVisibility(View.GONE);

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);

        crossfade();
    }


    // 步骤二 ：渐变View

    private void crossfade() {
        // 淡入显示内容
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        mContentView.animate().alpha(1f).setDuration(mShortAnimationDuration).setInterpolator(null);

        // 淡出 隐藏 进度条
        mLoadingView.animate().alpha(0f).setDuration(mShortAnimationDuration).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mLoadingView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }
}
