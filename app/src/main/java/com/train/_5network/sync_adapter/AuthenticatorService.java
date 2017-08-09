package com.train._5network.sync_adapter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;


/**
 * Created by 王斌 on 2017/8/9.
 * Bind the Authenticator to the Framework
 * 将授权器绑定到框架
 */

public class AuthenticatorService extends Service{

    private StubAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        mAuthenticator = new StubAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
