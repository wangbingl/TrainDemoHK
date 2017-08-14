package com.train._7userInfo_sign_In.contacts;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
// contacts framework
import android.provider.ContactsContract.Contacts;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.gl.traindemohk.R;


/**
 * displays the list of contacts
 */

public class ContactListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {





    /*
    Define global variables 全局变量

     */
    @SuppressLint("InlinedApi") // 将您的应用的minSdkVersion设置为10或更低版本会在Android Studio中生成Android Lint警告,
    // 要关闭此警告，请在定义FROM_COLUMNS之前添加注释@SuppressLint（“InlinedApi”）
    private final static String[] FROM_COLUMNS = {
            Build.VERSION.SDK_INT
                    >= Build.VERSION_CODES.HONEYCOMB ?
                    Contacts.DISPLAY_NAME_PRIMARY :
                    Contacts.DISPLAY_NAME
    };

    private final static int[] TO_IDS = {
            android.R.id.text1
    };

    ListView mContactsList;

    // Define variables for the contact the user selects
    // The contact's _ID value
    long mContactId;

    // The contact's LOOKUP_KEY
    String mContactKey;

    // A content URI for the selected contact
    Uri mContactUri;

    // An adapter that binds the result Cursor to the ListView
    private SimpleCursorAdapter mCursorAdapter;


    // Initialize the Fragment

    // Empty public constructor, required by the system
    public ContactListFragment() {
    }


    // A UI Fragment must inflate its View
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 设置布局文件
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Gets the ListView from the View list of the parent activity
        mContactsList = (ListView) view.findViewById(android.R.id.list);


        // Gets a CursorAdapter
        mCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.contacts_list_item,
                null,
                FROM_COLUMNS, TO_IDS,
                0);
        // Sets the adapter for the ListView
        mContactsList.setAdapter(mCursorAdapter);

        // Set the item click listener to be the current fragment.
        mContactsList.setOnItemClickListener(this);

        // Initializes the loader
        getLoaderManager().initLoader(0, null, this);

    }



    /*
    Cursor Loader
     */


    // Define a projection 定义查询映射


    @SuppressLint("InlinedApi")
    private static final String[] PROJECTION =
            {
                    Contacts._ID,
                    Contacts.LOOKUP_KEY,
                    Build.VERSION.SDK_INT
                            >= Build.VERSION_CODES.HONEYCOMB ?
                            Contacts.DISPLAY_NAME_PRIMARY :
                            Contacts.DISPLAY_NAME

            };

    // Define constants for the Cursor column indexes 定义Cursor的列索引常量
    // The column index for the _ID column
    private static final int CONTACT_ID_INDEX = 0;
    // The column index for the LOOKUP_KEY column
    private static final int LOOKUP_KEY_INDEX = 1;


    // Specify the selection criteria 指定查询条件

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private static final String SELECTION =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                    Contacts.DISPLAY_NAME_PRIMARY + " LIKE ?" :
                    Contacts.DISPLAY_NAME + " LIKE ?";
    // Defines a variable for the search string
    private String mSearchString;
    // Defines the array to hold values that replace the ?
    private String[] mSelectionArgs = { mSearchString };



    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        /*
         * Makes search string into pattern and
         * stores it in the selection array  将搜索字符串加入正则表达式，并存储在选择数组中
         */
        mSelectionArgs[0] = "%" + mSearchString + "%";
        // Starts the query
        return new CursorLoader(
                getActivity(),
                Contacts.CONTENT_URI,
                PROJECTION,
                SELECTION,
                mSelectionArgs,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Put the result Cursor in the adapter for the ListView
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // Delete the reference to the existing Cursor
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowID) {
        // Get the Cursor
        Cursor cursor = ((CursorAdapter)adapterView.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        mContactId = cursor.getLong(CONTACT_ID_INDEX);
        // Get the selected LOOKUP KEY
        mContactKey = cursor.getString(LOOKUP_KEY_INDEX);
        // Create the contact's content Uri
        mContactUri = Contacts.getLookupUri(mContactId, mContactKey);
        /*
         * You can use mContactUri as the content URI for retrieving
         * the details for a contact.
         */

    }





}
