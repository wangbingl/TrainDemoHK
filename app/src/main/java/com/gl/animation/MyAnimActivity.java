package com.gl.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-26.
 */

public class MyAnimActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animation);
//        TextView view = (TextView) findViewById(R.id.my_tv);
//       view.setVisibility(View.VISIBLE);
//        view.setText("你好！");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createFragments(savedInstanceState);
    }

    private static final String FRAGMENT_TAG_FRAME = "fragment_frame";
    private static final String FRAGMENT_TAG_TWEEN = "fragment_tween";
    private static final String FRAGMENT_TAG_PROPERTY = "fragment_property";
    private FrameByFrameAnimation frameFragment;
    private TweenedAnimation tweenFragment;
    private PropertyAnimation propertyFragment;

    private void createFragments(Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        frameFragment = (FrameByFrameAnimation) manager.findFragmentByTag(FRAGMENT_TAG_FRAME);
        tweenFragment = (TweenedAnimation) manager.findFragmentByTag(FRAGMENT_TAG_TWEEN);
        propertyFragment = (PropertyAnimation) manager.findFragmentByTag(FRAGMENT_TAG_PROPERTY);

        if (savedInstanceState == null) {
            if (frameFragment == null) {
                frameFragment = new FrameByFrameAnimation();
            }
            if (tweenFragment == null) {
                tweenFragment = new TweenedAnimation();
            }
            if (propertyFragment == null) {
                propertyFragment = new PropertyAnimation();
            }


            manager.beginTransaction().add(R.id.fragment_gl_anim_container, frameFragment, FRAGMENT_TAG_FRAME).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.anim_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_frame_anim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_gl_anim_container, frameFragment, FRAGMENT_TAG_FRAME).commit();
                return true;
            case R.id.action_tweened_anim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_gl_anim_container, tweenFragment, FRAGMENT_TAG_TWEEN).commit();
                return true;

            case R.id.action_property_anim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_gl_anim_container, propertyFragment, FRAGMENT_TAG_PROPERTY).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
