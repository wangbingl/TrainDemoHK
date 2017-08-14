package com.train._7userInfo_sign_In.contacts;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.support.annotation.Nullable;


/**
 * 为了取得一个联系人的所有详情，查找ContactsContract.Data表中包含联系人LOOKUP_KEY列的任意行。
 * 因为Contacts Provider隐式地连接了ContactsContract.Contacts表和ContactsContract.Data表，
 * 所以这个LOOKUP_KEY列在ContactsContract.Data表中是可用的。关于LOOKUP_KEY列，在获取联系人名字那一课有详细的描述。
 * 检索一个联系人的所有信息会降低设备的性能，因为这需要检索ContactsContract.Data表的所有列。
 */

public class ContactDetailActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    // 步骤：
    // 1 请求权限
    // 2 设置查询映射
    // 3 定义查询标准
    // 4 定义排序顺序
    // 5 初始化查询loader
    // 6 实现onCreateLoader()方法
    // 7 实现onLoadFinished()方法和onLoaderReset()方法

    private static final String[] PROJECTION = {
            Data._ID,
            Data.MIMETYPE,
            Data.DATA1,
            Data.DATA2,
            Data.DATA3,
            Data.DATA4,
            Data.DATA5,
            Data.DATA6,
            Data.DATA7,
            Data.DATA8,
            Data.DATA9,
            Data.DATA10,
            Data.DATA11,
            Data.DATA12,
            Data.DATA13,
            Data.DATA14,
            Data.DATA15
    };

    // Defines the selection clause 查询条件
    private static final String SELECTION = Data.LOOKUP_KEY + " = ?";
    // Defines the array to hold the search criteria 查询参数
    private String[] mSelectionArgs = {""};
    /*
    * Defines a variable to contain the selection value. Once you
    * have the Cursor from the Contacts table, and you've selected
    * the desired row, move the row's LOOKUP_KEY value into this
    * variable.
    */
    private String mLookupKey;

    /*
     * Defines a string that specifies a sort order of MIME type 按数据类型排序
     */
    private static final String SORT_ORDER = Data.MIMETYPE;

    // Defines a constant that identifies the loader 区分Loader
    private static final int DETAILS_QUERY_ID = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        CursorLoader loader = null;
        switch (loaderId) {
            case DETAILS_QUERY_ID:
                // Assigns the selection parameter
                mSelectionArgs[0] = mLookupKey;
                loader = new CursorLoader(this,Data.CONTENT_URI,PROJECTION,SELECTION,mSelectionArgs,SORT_ORDER);
                break;
            default:
                break;
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()){
            case DETAILS_QUERY_ID:
                /*
              * Process the resulting Cursor here.
              */
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()){
            case DETAILS_QUERY_ID:
                /*
                 * If you have current references to the Cursor,
                 * remove them here.
                 * */
                break;
        }
    }
}
