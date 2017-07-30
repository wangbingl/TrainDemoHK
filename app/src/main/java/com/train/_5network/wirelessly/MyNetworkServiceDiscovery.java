package com.train._5network.wirelessly;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Created by 王斌 on 2017/7/30.
 */

public class MyNetworkServiceDiscovery extends AppCompatActivity{
    private static final String TAG = MyNetworkServiceDiscovery.class.getSimpleName();
    private ServerSocket mServerSocket;
    private int mLocalPort;
    private NsdManager.RegistrationListener mRegistrationListener;
    private String mServiceName;
    private NsdManager mNsdManager;
    private NsdManager.DiscoveryListener mDiscoveryListener;
    private NsdManager.ResolveListener mResolveListener ;
    private NsdServiceInfo mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 注册 NSD 服务
     * @param port
     */
    public void registerService(int port) {
        NsdServiceInfo serviceInfo = new NsdServiceInfo();

        serviceInfo.setServiceName("NsdChat");
        serviceInfo.setServiceType("_http._tcp.");
        serviceInfo.setPort(port);

        mNsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
        mNsdManager.registerService(serviceInfo,NsdManager.PROTOCOL_DNS_SD,mRegistrationListener);
    }

    public void initServerSocket() throws IOException {
        mServerSocket = new ServerSocket(0);
        mLocalPort = mServerSocket.getLocalPort();
    }
    
    public void initRegistrationListener(){
            mRegistrationListener = new NsdManager.RegistrationListener(){
                @Override
                public void onRegistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {
                   // Save the service name.  Android may have changed it in order to resolve a conflict
                    mServiceName = nsdServiceInfo.getServiceName();
                }

                @Override
                public void onUnregistrationFailed(NsdServiceInfo nsdServiceInfo, int i) {

                }

                @Override
                public void onServiceRegistered(NsdServiceInfo nsdServiceInfo) {

                }

                @Override
                public void onServiceUnregistered(NsdServiceInfo nsdServiceInfo) {

                }
            };
    }
    String  SERVICE_TYPE;
    public void initDiscoveryListener(){
        mDiscoveryListener = new NsdManager.DiscoveryListener(){


            @Override
            public void onDiscoveryStarted(String regType) {
                Log.d(TAG,"Service discovery Started");
            }



            @Override
            public void onServiceFound(NsdServiceInfo nsdServiceInfo) {
                Log.d(TAG,"Service discovery success "+ nsdServiceInfo);
                if (!nsdServiceInfo.getServiceType().equals(SERVICE_TYPE)){
                    Log.d(TAG,"Unknown Service Type: "+nsdServiceInfo.getServiceType());
                }else if (nsdServiceInfo.getServiceName().equals(mServiceName)){
                    Log.d(TAG,"Same machine: "+ mServiceName);
                }else if (nsdServiceInfo.getServiceName().contains("NsdChat")){
                    mNsdManager.resolveService(nsdServiceInfo,mResolveListener);
                }

            }

            @Override
            public void onServiceLost(NsdServiceInfo nsdServiceInfo) {
                Log.e(TAG, "service lost" + nsdServiceInfo);
            }

            @Override
            public void onDiscoveryStopped(String serviceTypes) {
                Log.i(TAG, "Discovery stopped: " + serviceTypes);
            }

            @Override
            public void onStartDiscoveryFailed(String serviceType, int errorCode) {
                Log.e(TAG, "Discovery failed: Error code:" + errorCode);
                mNsdManager.stopServiceDiscovery(this);
            }

            @Override
            public void onStopDiscoveryFailed(String s, int i) {

            }
        };
    }
    
    public void initResolveListener(){
        mResolveListener  = new NsdManager.ResolveListener(){

            @Override
            public void onResolveFailed(NsdServiceInfo nsdServiceInfo, int errorCode) {
                Log.e(TAG, "Resolve failed "+ errorCode);
            }

            @Override
            public void onServiceResolved(NsdServiceInfo nsdServiceInfo) {
                Log.d(TAG,"Resolve Succeeded. " + nsdServiceInfo);

                if (nsdServiceInfo.getServiceName().equals(mServiceName)){
                    Log.d(TAG,"Same IP.");
                    return;
                }
                mService = nsdServiceInfo;
                int port = mService.getPort();
                InetAddress host = mService.getHost();
            }
        };
    }
}
