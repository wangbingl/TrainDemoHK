package com.gl.animation;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-7-26.
 */

public class AnimActivity extends FragmentActivity {
    private static final String FRAGMENT_TAG_FRAME = "fragment_frame";
    private static final String FRAGMENT_TAG_TWEEN = "fragment_tween";
    private static final String FRAGMENT_TAG_PROPERTY = "fragment_property";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_gl_anim);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_gl_anim_container, new FrameByFrameAnimation(), FRAGMENT_TAG_FRAME)
                    .commit();
        }
    }
}
