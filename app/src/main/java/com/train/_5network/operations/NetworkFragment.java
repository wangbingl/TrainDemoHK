package com.train._5network.operations;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by wb on 17-8-1.
 * <p>
 * Implementation of headless Fragment that runs an AsyncTask to fetch data from the network.
 * <p>
 * 无布局的碎片，从网络获取数据。
 */

public class NetworkFragment extends Fragment {

    public static final String TAG = "NetworkFragment";

    private static final String URL_KEY = "UrlKey";


    private DownloadCallback mCallback;
    private DownloadTask mDownloadTask;

    private String mUrlString;

    /**
     * NetworkFragment的静态初始化器
     * <p>
     * 设置 下载的URL
     */
    public static NetworkFragment getInstance(FragmentManager fragmentManager, String url) {

        NetworkFragment networkFragment = (NetworkFragment) fragmentManager.findFragmentByTag(TAG);
        if (networkFragment == null){
            networkFragment = new NetworkFragment();

            Bundle args = new Bundle();
            args.putString(URL_KEY, url);
            networkFragment.setArguments(args);
            fragmentManager.beginTransaction().add(networkFragment, TAG).commit();
        }


        return networkFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrlString = getArguments().getString(URL_KEY);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Host Activity will handle callbacks from task.
        mCallback = (DownloadCallback) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Clear reference to host Activity to avoid memory leak.
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        // Cancel task when Fragment is destroyed.
        cancelDownload();
        super.onDestroy();


    }

    public void startDownload() {
        cancelDownload();
        mDownloadTask = new DownloadTask(mCallback);
        mDownloadTask.execute(mUrlString);
    }

    public void cancelDownload() {
        if (mDownloadTask != null) {
            mDownloadTask.cancel(true);
        }
    }
}
