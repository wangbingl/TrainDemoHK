package com.train._5network.operations;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 王斌 on 2017/7/30.
 */

public class ConnectingToNetActivity extends AppCompatActivity implements DownloadCallback {
    /*
    	This lesson teaches you to

            1. Design Secure Network Communication
            2. Choose an HTTP Client
            3. Perform Network Operations Asynchronously
            4. Implement a Headless Fragment to Manage Network Operations Using an AsyncTask
            5. Connect and Download Data
            6. Convert the InputStream to a String
            7.Persist Background Work during Configuration Changes


     */

    private NetworkFragment mNetworkFragment;
    private boolean mDownloading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(),"https://developers.google.cn/");
    }

    private void startDownload(){
        if (!mDownloading && mNetworkFragment != null){
            mNetworkFragment.startDownload();
            mDownloading= true;
        }
    }

    @Override
    public void updateFromDownload(Object result) {
        // Update your UI here based on result of download.
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch (progressCode){
            case Progress.ERROR:
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_ISUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment !=null){
            mNetworkFragment.cancelDownload();
        }
    }
}
