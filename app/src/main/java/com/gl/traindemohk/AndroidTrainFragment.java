package com.gl.traindemohk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.train._4graphics_anim.AnimationActivity;

/**
 * Created by wb on 17-7-26.
 */

public class AndroidTrainFragment extends Fragment implements AdapterView.OnItemClickListener {

    static final String TAG = AndroidTrainFragment.class.getSimpleName();
    private ListView mListView;

    private String[] data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_train, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) view.findViewById(R.id.main_list_view);
        mListView.setOnItemClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
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
                intent.setClass(getActivity(), AnimationActivity.class);
                break;

        }
        startActivity(intent);
    }
}
