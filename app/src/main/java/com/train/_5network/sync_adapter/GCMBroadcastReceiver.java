package com.train._5network.sync_adapter;

import android.accounts.Account;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;

/**
 * 当服务器数据变化时，运行 Sync Adapter
 * 谷歌云消息 简称 GCM
 */

public class GCMBroadcastReceiver extends BroadcastReceiver{
    public static final String AUTHORITY = "com.gl.train.dataSync.provider";

    public static final String ACCOUNT_TYPE = "com.gl.train.dataSync";
    public static final String ACCOUNT = "default_account";

    public static final Account mAccount = new Account(ACCOUNT,ACCOUNT_TYPE);

    public static final String KEY_SYNC_REQUEST = "com.gl.train.dataSync.KEY_SYNC_REQUEST";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 获取 GCM 对象
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);


        // 获取消息类型
        String messageType = gcm.getMessageType(intent);

        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType) && intent.getBooleanExtra(KEY_SYNC_REQUEST,false)){
            ContentResolver.requestSync(mAccount,AUTHORITY,null);
        }
    }

    /**
     * 模拟 代码
     */
    static class GoogleCloudMessaging{
        public static final String MESSAGE_TYPE_MESSAGE = "";
        public static GoogleCloudMessaging getInstance(Context context){
            return  new GoogleCloudMessaging();
        }
        public String getMessageType(Intent intent){
            return "";
        }
    }
}
