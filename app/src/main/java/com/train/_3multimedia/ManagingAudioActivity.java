package com.train._3multimedia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.gl.traindemohk.R;

public class ManagingAudioActivity extends AppCompatActivity {

    // 第一节：鉴别使用的是哪个音频流(Identify Which Audio Stream to Use)
    // 第二节：使用硬件音量键来控制应用的音量
    // 第三节：使用硬件的播放控制按键（指的是耳机上的按钮）来控制应用的音频播放
    RemoteControlReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managing_audio);
        /* 来直接控制指定的音频流 该方法只需要在Activity整个生命周期中调用一次
        自此之后，不管目标Activity或Fragment是否可见，按下设备的音量键都能够影响我们指定的音频流（在这个例子中，音频流是"music"）
        */
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        IntentFilter filter = new IntentFilter("android.intent.action.MEDIA_BUTTON");
        receiver = new RemoteControlReceiver();

    }

    public class RemoteControlReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                    // Handle key press.
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
