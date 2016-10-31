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

/**
 * Created by tuionf on 2016/10/30.
 */

public class HTTPRequestSplitChar{

    private static final String TAG = "hhp";
    String str = "";

    public String getStr() {
        return str;
    }

    public HTTPRequestSplitChar(String str) {
        this.str = str;
    }

    public static String setSplitChar(String str,IResponse response) {

            HttpURLConnection httpURLConnection = null;

            String tmpStrUtf8 = "";
            String result = "";

            try {
                String api_key = "y8T4r2i6qFEhkFpo0HhWDy7K0x0eJZ3UTRQIqz5v";
                tmpStrUtf8 = URLEncoder.encode(str,"utf-8");
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
                Log.d(TAG, "setSplitChar: "+url);
                URL mURL = new URL(url);
                // 2.获取HttpURLConnection实例
                httpURLConnection = (HttpURLConnection) mURL.openConnection();
                // 3. 设置请求的方法 超时时间等
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setReadTimeout(80000);

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {

                    Log.d(TAG, "setSplitChar: 成功 "+responseCode+"---"+httpURLConnection.getResponseMessage());

                    //说明网络请求成功
                    //4.获取到服务器返回的输入流
                    InputStream  inputStream = httpURLConnection.getInputStream();

                    //5.将输入流读取出来
                    String strtmp = getResultFromInputStream(inputStream);
                    Log.d(TAG, "最后的结果+++++++"+strtmp);
                    if (response != null){
                        response.finishString(strtmp);
                    }
//
                }else {
                    //说明网络请求失败
                    Log.d(TAG, "setSplitChar: 失败 "+responseCode+"---"+httpURLConnection.getResponseMessage());
                    response.failure("访问失败");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
            }

            return result;
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
