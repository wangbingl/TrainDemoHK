package com.train._1started;

import android.app.Activity;
import android.os.Bundle;

import com.gl.traindemohk.R;


/**
 * Action bar 最基本的形式，就是为 Activity 显示标题，并且在标题左边显示一个 app icon
 * 设置一个基本的 action bar，需要 app 使用一个 activity 主题，该主题必须是 action bar 可用的
 */

public class CustomActionBarActivity_Up30 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_action_bar);
    }
}
