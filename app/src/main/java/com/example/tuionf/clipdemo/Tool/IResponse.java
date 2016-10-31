package com.example.tuionf.clipdemo.Tool;

/**
 * Created by lizhengxian on 2016/10/23.
 */

public interface IResponse {
    void finish(String[] words);
    void finishString(String words);
    void failure(String errorMsg);
}
