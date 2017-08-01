package com.train._5network.operations;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * Created by wb on 17-8-1.
 */

public class DownloadTask extends AsyncTask<String, Void, DownloadTask.Result> {
    private DownloadCallback<String> mCallback;

    public DownloadTask(DownloadCallback<String> callback) {
        setCallback(callback);
    }

    void setCallback(DownloadCallback<String> callback) {
        mCallback = callback;
    }

    @Override
    protected Result doInBackground(String... strings) {
        return null;
    }

    static class Result {
        public String mResultValue;
        public Exception mException;

        public Result(String resultValue) {
            mResultValue = resultValue;
        }

        public Result(Exception exception) {
            mException = exception;
        }
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            NetworkInfo networkInfo = mCallback.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() || (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                    && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                // If no connectivity, cancel task and update Callback with null data.
                mCallback.updateFromDownload(null);
                cancel(true);
            }


        }
    }
}
