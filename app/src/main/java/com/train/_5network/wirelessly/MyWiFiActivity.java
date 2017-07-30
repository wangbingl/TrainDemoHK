package com.train._5network.wirelessly;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.gl.traindemohk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王斌 on 2017/7/30.
 */

public class MyWiFiActivity extends AppCompatActivity {
    private static final String TAG = "MyWiFiActivity";
    /*
    主要步骤：
    	1. Set Up Application Permissions 设置权限
        2. Set Up the Broadcast Receiver and Peer-to-Peer Manager 设置广播接收器和 P2P 管理器
        3. Initiate Peer Discovery 初始化对等节点发现
        4. Fetch the List of Peers 获取对等节点列表
        5. Connect to a Peer 连接一个对等节点
        6.Create a Group

     */

    private ListView mListView;
    private final IntentFilter filter = new IntentFilter();
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private MyWifiReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wifi_list);
        mListView = (ListView) findViewById(R.id.wifi_device_list_view);

        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        receiver = new MyWifiReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    class MyWifiReceiver extends BroadcastReceiver {






        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){
                // Determine if Wifi P2P mode is enabled or not, alert the Activity.
                int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1);
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    setIsWifiP2pEnabled(true);
                } else {
                    setIsWifiP2pEnabled(false);
                }

            }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

            }else  if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)){

                updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

            }
        }
    }
    // 此方法在 Activity 中
    public void setIsWifiP2pEnabled(boolean b){

    }
    // 在 Fragment
    public void updateThisDevice(WifiP2pDevice device){

    }

    // 获取对等点列表
    private List peers = new ArrayList<>();
    private WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            peers.clear();
            peers.addAll(wifiP2pDeviceList.getDeviceList());
            // update listView
            if (peers.size() == 0) {
                Log.d(TAG, "No devices found");
                return;
            }

        }
    };
}
