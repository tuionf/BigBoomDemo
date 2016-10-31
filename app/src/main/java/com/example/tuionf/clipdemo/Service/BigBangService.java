package com.example.tuionf.clipdemo.Service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.tuionf.clipdemo.Activity.BigBangActivity;

public class BigBangService extends Service {
    private static final String TAG = "hhp";
    public BigBangService() {
        Log.d(TAG, "BigBangService: "+"初始化方法1111");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        listenClipboard();
        return super.onStartCommand(intent, flags, startId);
    }

    //TODO  通知栏常驻

    /*
    * 剪切板监听
    * */
    private void listenClipboard(){
        Log.d(TAG, "BigBangService  listenClipboard: ");
        final ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                Toast.makeText(getApplicationContext(), "监听到剪切板的变化", Toast.LENGTH_SHORT).show();

                if (BigBangActivity.isShowing){
                    Intent intent = new Intent("INTENT.FINISH_ACTIVITY");
                    sendBroadcast(intent);
                }else {
                    //跳转到 BigBangActivity
                    Intent intent = new Intent(getApplicationContext(),BigBangActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //TODO    考虑其他办法
                    intent.putExtra("clip",clipboardManager.getText().toString());
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}


