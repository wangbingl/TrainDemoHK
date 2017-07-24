package com.train._4graphics_anim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gl.traindemohk.R;

public class AnimationActivity extends AppCompatActivity {
    private static final String FRAGMENT_TAG_CROSS_FADE = "cross_fade_fragment";
    private static final String FRAGMENT_TAG_SCREEN_SLIDE = "screen_slide_fragment";
    private static final String FRAGMENT_TAG_CARD_FLIP = "card_flip_fragment";
    private static final String FRAGMENT_TAG_VIEW_ZOOM = "view_zoom_fragment";
    private static final String FRAGMENT_TAG_LAYOUT_CHANGE = "layout_change_fragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            CrossFadeFragment fragment = new CrossFadeFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.anim_fragment_container, fragment, FRAGMENT_TAG_CROSS_FADE).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.animation_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // 处理动作按钮的点击事件
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_cross_fade:
                return true;

            case R.id.action_screen_slide:
                fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_SCREEN_SLIDE);
                if (fragment == null) {
                    fragment = new ScreenSlideFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.anim_fragment_container, fragment, FRAGMENT_TAG_SCREEN_SLIDE).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.anim_fragment_container, fragment).commit();
                }
                return true;
            case R.id.action_card_flip:
                return true;
            case R.id.action_view_zoom:
                return true;
            case R.id.action_layout_change:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


}
