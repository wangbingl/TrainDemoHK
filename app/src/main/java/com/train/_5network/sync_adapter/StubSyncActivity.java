package com.train._5network.sync_adapter;

import android.accounts.Account;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 *  2017/8/12.
 *  当 Content Provider 的数据变化时，运行 Sync Adapter
 */

public class StubSyncActivity extends FragmentActivity{

    public static final String SCHEME = "content://";

    public static final String AUTHORITY = "com.gl.train.dataSync.provider";
    public static final String TABLE_PATH = "data_table";

    public static final String ACCOUNT = "default_account";

    Account mAccount = new Account(ACCOUNT,"account_type");

    // 全局变量 data table 的uri
    Uri mUri;

    ContentResolver mResolver;

    public class TableObserver extends ContentObserver{

        public TableObserver(Handler handler) {
            super(handler);


        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            // Android 4.1 使用空的uri.
            onChange(selfChange,null);
        }

        @Override
        public void onChange(boolean selfChange, Uri changeUri) {
            super.onChange(selfChange, changeUri);
            /*
            请求框架运行 sync adapter，为了维护向后兼容性，假设changeUri为null.
             */
            ContentResolver.requestSync(mAccount,AUTHORITY,null);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResolver = getContentResolver();

        mUri = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(TABLE_PATH).build();


//        TableObserver observer = new TableObserver(false);
        TableObserver observer = new TableObserver(null);

        mResolver.registerContentObserver(mUri,true,observer);
    }
}
