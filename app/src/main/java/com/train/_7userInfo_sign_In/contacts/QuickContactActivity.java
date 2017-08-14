package com.train._7userInfo_sign_In.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.QuickContactBadge;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-8-14.
 */

public class QuickContactActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_contact);
    }

    // 设置联系人URI
    Cursor mCursor;
    int mIdColumn;// _ID
    int mLookupKeyColumn;// LOOKUP_KEY

    Uri mContactUri;

    QuickContactBadge mBadge;



}
