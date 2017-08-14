package com.train._7userInfo_sign_In.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Intents;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.gl.traindemohk.R;

/**
 * Created by wb on 17-8-14.
 */

public class ContactEditorActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact_editor);
    }

    // insert a New Contact Using an Intent

    private void insertNewContact(){
        // Creates a new Intent to insert a contact
        Intent intent = new Intent(Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
    }

    private void editContacts(){

    }
}
