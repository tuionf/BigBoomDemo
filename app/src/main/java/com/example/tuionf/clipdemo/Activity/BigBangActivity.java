package com.example.tuionf.clipdemo.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tuionf.clipdemo.R;
import com.example.tuionf.clipdemo.Tool.HTTPRequestSplitChar;
import com.example.tuionf.clipdemo.Tool.IResponse;

public class BigBangActivity extends AppCompatActivity {

    private static final String TAG = "hhp";
    private EditText mEt;
    private TextView mTv;
    public static boolean isShowing = false;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String sss = msg.getData().getString("result");
            Log.d(TAG, "handleMessage: "+sss);
            mEt.setText(sss);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bang);
        mEt = (EditText) findViewById(R.id.et);
        mTv = (TextView) findViewById(R.id.tv);
        isShowing = true;
        registerReceiver(receiver,new IntentFilter("INTENT.FINISH_ACTIVITY"));

        Intent intent = getIntent();
        String str = intent.getStringExtra("clip");
        setSplitResult(str);
        
    }

    private void setSplitResult(final String s){
        new Thread(new Runnable() {

            @Override
            public void run() {
                HTTPRequestSplitChar.setSplitChar(s, new IResponse() {
                    @Override
                    public void finish(String[] words) {

                    }

                    @Override
                    public void finishString(String words) {
                        Log.d(TAG, "进入 ------ finishString: "+words);
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("result",words);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);

                    }

                    @Override
                    public void failure(String errorMsg) {

                    }
                });

            }

        }).start();
//     Log.d(TAG, "setSplitResult: 最后的结果"+ Constant.result001);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(BigBangActivity.this,"收到广播",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isShowing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = false;
        unregisterReceiver(receiver);
    }
}