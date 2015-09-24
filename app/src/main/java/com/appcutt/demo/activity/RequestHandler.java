package com.appcutt.demo.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.appcutt.demo.request.AbsBusinessRequest;

/**
 * 用于解析请求
 *
 * Created by ouyangjinmiao on 18/5/15.
 */
public abstract class RequestHandler extends Handler {

    public RequestHandler() {
        super();
    }

    public RequestHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        // success
        int result = msg.arg1;
        if (result == AbsBusinessRequest.ACTION_SUCCESS) {

            // 处理响应内容
            onSuccess(msg.what, msg.obj);
        }

        // fail
        else if (result == AbsBusinessRequest.ACTION_FAIL) {
            onFail(msg.what, msg.arg2);
        }


    }

    /**
     * 处理服务器成功返回
     *
     * @param what
     *            请求标识号
     * @param response
     *            响应数据
     */
    protected abstract void onSuccess(int what, Object response);

    /**
     * 处理服务器错误返回
     *
     * @param what
     *            请求标识号
     * @param error
     *            封装错误信息
     */
    protected abstract void onFail(int what, int error);
}
