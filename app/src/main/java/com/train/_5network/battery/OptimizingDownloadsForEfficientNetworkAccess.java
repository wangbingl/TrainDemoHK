package com.train._5network.battery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 王斌 on 2017/8/5.
 *
 * 优化下载实现有效的网络访问
 */

public class OptimizingDownloadsForEfficientNetworkAccess extends AppCompatActivity{


    // 针对下载，做如下优化：
    //    This lesson teaches you to
    //	1. Understand the radio state machine
    //	2. Understand how apps can impact the radio state machine
    //	3. Efficiently prefetch data 高效预先取数据
    //	4. Batch transfers and connections 批量传输和连接
    //	5. Reduce the number of connections you use 减少连接
    //    Use the DDMS Network Traffic Tool to identify areas of concern 监控


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
