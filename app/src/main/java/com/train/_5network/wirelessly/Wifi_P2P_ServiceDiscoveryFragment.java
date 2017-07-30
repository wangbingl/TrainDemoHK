package com.train._5network.wirelessly;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gl.traindemohk.R;

import java.util.HashMap;
import java.util.Map;

import static android.os.Looper.getMainLooper;

/**
 * Created by 王斌 on 2017/7/30.
 * <p>
 * 目的：在不接入网络的情况下，Wi-Fi P2P 服务发现也可以使我们的应用直接发现附近的设备。
 * 步骤：
 * 1. Set Up the Manifest
 * 2. Add a Local Service
 * 3. Discover Nearby Services
 */

public class Wifi_P2P_ServiceDiscoveryFragment extends Fragment {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wifi_p2p_service_discovery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView mTextView = view.findViewById(R.id.wifi_p2p_service_name);

        mManager = (WifiP2pManager) getContext().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(getContext().getApplicationContext(), getMainLooper(), null);
    }


    /**
     * 发现附近的服务
     *
     * 	1. 新建 WifiP2pServiceInfo 对象
        2. 加入相应服务的详细信息
        3.调用 addLocalService() 为服务发现注册本地服务
     */
    private void startRegistration(int SERVER_PORT) {
        //  Create a string map containing information about your service.
        Map record = new HashMap();
        record.put("listenport", String.valueOf(SERVER_PORT));
        record.put("buddyname", "John Doe" + (int) (Math.random() * 1000));
        record.put("available", "visible");

        WifiP2pDnsSdServiceInfo serviceInfo =
                WifiP2pDnsSdServiceInfo.newInstance("_test", "_presence._tcp", record);

        mManager.addLocalService(mChannel, serviceInfo, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                // Command successful! Code isn't necessarily needed here,
                // Unless you want to update the UI or add logging statements.
            }
            @Override
            public void onFailure(int arg0) {
                // Command failed.  Check for P2P_UNSUPPORTED, ERROR, or BUSY
            }
        });

    }
}
