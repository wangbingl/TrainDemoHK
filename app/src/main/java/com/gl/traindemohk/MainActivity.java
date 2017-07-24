package com.gl.traindemohk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.train._4graphics_anim.AnimationActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String TAG = MainActivity.class.getSimpleName();
    private ListView mListView;
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.main_list_view);
        mListView.setOnItemClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        data = new String[]{"1.开始", "2.Android 分享操作", "3.Android多媒体", "4.Android图像与动画", "5.Android网络操作", "6.联系人"};
        adapter.addAll(data);


        mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, "[onItemClick] position:" + i);
        Intent intent = new Intent();
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                intent.setClass(this, AnimationActivity.class);
                break;

        }
        startActivity(intent);
    }
}
