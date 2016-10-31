package com.example.tuionf.clipdemo.Tool;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tuionf on 2016/10/30.
 */

public class HttpNet {

    private String httpNetStr = "";

    public String getHttpNetStr() {
        return httpNetStr;
    }

    public void setHttpNetStr(String httpNetStr) {
        this.httpNetStr = httpNetStr;
    }

    public static void setSplitChat(final String s) {

        ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
        HttpURLConnection httpURLConnection = null;
        final String TAG = "hhp";


        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ");
                HttpURLConnection httpURLConnection = null;
                InputStream inputStream = null;
                String tmpStrUtf8 = "";
                String result = "";
                String api_key = "y8T4r2i6qFEhkFpo0HhWDy7K0x0eJZ3UTRQIqz5v";

                try {
                    tmpStrUtf8 = URLEncoder.encode(s, "utf-8");

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                StringBuilder urlStringBuilder = new StringBuilder();
                //TODO  单引号 双引号
                urlStringBuilder.append("http://api.ltp-cloud.com/analysis")
                        .append('?').append("api_key").append('=').append(api_key)
                        .append('&').append("text").append('=').append(tmpStrUtf8)
                        .append('&').append("pattern").append('=').append("ws")
                        .append('&').append("format").append('=').append("plain");

                //发起网络请求
                // 1.构建Url
                String url = urlStringBuilder.toString();
                Log.d(TAG, "setSplitChar: " + url);
                URL mURL = null;
                try {
                    mURL = new URL(url);
                    // 2.获取HttpURLConnection实例
                    httpURLConnection = (HttpURLConnection) mURL.openConnection();

                    // 3. 设置请求的方法 超时时间等
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setReadTimeout(80000);

                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        //说明网络请求成功
                        //4.获取到服务器返回的输入流
                        inputStream = httpURLConnection.getInputStream();

                        //5.将输入流读取出来
                        result = getResultFromInputStream(inputStream);
                        Log.d(TAG, "setSplitChar:====== " + result);

                    } else {
                        //说明网络请求失败
                        Log.d(TAG, "run: 说明网络请求失败");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    httpURLConnection.disconnect();
                }
            }

        });
    }



    public static String getResultFromInputStream(InputStream inputStream) {

        String result = "";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        //TODO  读到哪里  写到哪里
        //相当于inputStream.read（byte[] buffer, int byteOffset, int byteCount）
        // 从inputStream中读取 byteCount  长度的字节，并将其存储到字节数组buffer中，且从byteOffset开始存储
        //返回-1 表示读取结束

        try {
            while (inputStream.read(buffer) != -1){
                os.write(buffer);
                //              os.write(buffer,0,buffer.length);
            }

            result = os.toString();

            //            result = URLEncoder.encode(result,"utf-8");
            inputStream.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}
