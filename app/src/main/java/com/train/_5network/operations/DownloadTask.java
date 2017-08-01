package com.train._5network.operations;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wb on 17-8-1.
 */

public class DownloadTask extends AsyncTask<String, Integer, DownloadTask.Result> {
    private DownloadCallback<String> mCallback;

    public DownloadTask(DownloadCallback<String> callback) {
        setCallback(callback);
    }

    void setCallback(DownloadCallback<String> callback) {
        mCallback = callback;
    }

    @Override
    protected Result doInBackground(String... urls) {
        Result result = null;
        if (!isCancelled() && urls !=null && urls.length >0){
            String urlString = urls[0];
            try {
                URL url = new URL(urlString);
                String resultString = downloadUrl(url);

                if (resultString != null){
                    result = new Result(resultString);
                }else{
                    throw new IOException("No response received.");
                }

            }catch (Exception e){
                result = new Result(e);
            }
        }
        return result;
    }

    /**
     * to add special behavior for canceled AsyncTask.
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
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

    /* *
    Using HttpsUrlConnection to Fetch Data

     */

    private String downloadUrl(URL url) throws IOException{
        InputStream stream  = null;
        HttpsURLConnection connection = null;
        String result = null;

        try {
            connection = (HttpsURLConnection) url.openConnection();

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);

            connection.setRequestMethod("GET");

            connection.setDoInput(true);

            connection.connect();

            publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);

            int responseCode = connection.getResponseCode();

            if (responseCode !=  HttpsURLConnection.HTTP_OK){
                throw  new IOException("Http error code: "+responseCode);
            }

            stream = connection.getInputStream();

            publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_ISUCCESS);

            if (stream != null){
                result = readStream(stream,500);
            }
        } finally {
            if (stream != null){
                stream.close();
            }
            if (connection != null){
                connection.disconnect();
            }
        }
        return  result;
    }

    private String readStream(InputStream stream, int maxReadSize) throws IOException,UnsupportedEncodingException{
        Reader reader = null;
        reader = new InputStreamReader(stream,"UTF-8");
        char[] rawBuffer = new char[maxReadSize];

        int readSize;
        StringBuffer buffer = new StringBuffer();

        while ((readSize = reader.read(rawBuffer)) != -1 && maxReadSize >0){
            if (readSize > maxReadSize){
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer,0,readSize);

            maxReadSize -= readSize;

        }

        return buffer.toString();
    }
}
