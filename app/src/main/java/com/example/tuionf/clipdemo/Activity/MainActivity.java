package com.example.tuionf.clipdemo.Activity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuionf.clipdemo.R;
import com.example.tuionf.clipdemo.Service.BigBangService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button put;
    private Button get;
    private Button start;
    private EditText et;
    private static final String TAG = "hhp";

    private ClipboardManager mClipboardManager;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String sss = msg.getData().getString("result");
            Log.d(TAG, "handleMessage: "+sss);
            et.setText(sss);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        put = (Button) findViewById(R.id.put);
        get = (Button) findViewById(R.id.get);
        start = (Button) findViewById(R.id.start);
        et = (EditText) findViewById(R.id.testEt);

        put.setOnClickListener(this);
        get.setOnClickListener(this);
        start.setOnClickListener(this);
        findViewById(R.id.testBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setSplitResult1(et.getText().toString());
            }
        });
    }

//    private void setSplitResult1(final String s){
//        //        HttpNet.setSplitChat(s);
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                HTTPRequestSplitChar.setSplitChar(s);
//                Message msg = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("result","默认值");
//                msg.setData(bundle);
//                mHandler.sendMessage(msg);
//            }
//
//
//        }).start();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.put:
                put();
                break;
            case R.id.get:
                get();
                break;
            case R.id.start:
                Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,BigBangService.class);
                startService(i);
//                Intent i = new Intent(MainActivity.this,BigBangActivity.class);
//                Log.d(TAG, "onClick: start");
//                startActivity(i);

                break;
            default:
                break;
        }
    }

    /*
    * 往剪切板放入数据
    * */
    private void put(){

        /*
        * 往ClipboardManager中可放的数据类型有三种:  Text intent url
        * */

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("aaa","absdfghh");
        mClipboardManager.setPrimaryClip(clipData);
    }

    /*
    * 从剪切板取出数据
    * */
    private void get(){
        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData.Item  item = null;

        if ( !mClipboardManager.hasPrimaryClip()) {
            Toast.makeText(MainActivity.this,"剪切板无数据",Toast.LENGTH_SHORT).show();
            return;
        }

        //文本类型
        if (mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
            ClipData mClipDataText = mClipboardManager.getPrimaryClip();
            item = mClipDataText.getItemAt(0);

            //此处是TEXT文本信息
            if(item.getText() == null){
                Toast.makeText(getApplicationContext(), "剪贴板中无文本内容", Toast.LENGTH_SHORT).show();
                return ;
            }else{
                Toast.makeText(getApplicationContext(), item.getText(), Toast.LENGTH_SHORT).show();
            }

            //Intent类型
        }else if(mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)){

        //url类型
        }else if(mClipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_URILIST)){

        }

    }
}
