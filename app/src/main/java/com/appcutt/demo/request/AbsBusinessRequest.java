package com.appcutt.demo.request;

import android.content.Context;
import android.os.Message;

import java.util.Map;

import com.appcutt.demo.request.AbsBusinessResponseHandler;
import com.appcutt.libs.net.http.AsyncHttpClient;
import com.appcutt.libs.net.http.AsyncHttpResponseHandler;
import com.appcutt.libs.net.http.JsonHttpResponseHandler;
import org.apache.http.Header;

/**
 * 所有与后台交互的抽象请求类
 *
 * Created by ouyangjinmiao on 18/5/15.
 */
public abstract class AbsBusinessRequest {


    public static final int ACTION_SUCCESS = 1;

    public static final int ACTION_FAIL = 2;

    /**
     * 用于传递从服务端返回的数据到前台，注意message的target handle需要传入 code
     * {@link android.os.Looper#getMainLooper()}.
     */
    private Message mMessage;

    protected Map<String, String> mParams;

    protected Context mContext;

    private JsonHttpResponseHandler mResponseHandler = new AbsBusinessResponseHandler() {

        @Override
        protected void onRequestSuccess(int statusCode, Header[] headers, Object jsonObj) {
            AbsBusinessRequest.this.onSuccess(headers, jsonObj);
        }

        @Override
        protected void onRequestFail(int statusCode, Header[] headers) {
            AbsBusinessRequest.this.onFail(statusCode);
        }

        @Override
        protected Object parseResponse(Object jsonObj) throws Exception {
            return AbsBusinessRequest.this.parseResponse(jsonObj);
        }
    };

    public AbsBusinessRequest(Context context) {
        this(context, null, null);
    }

    public AbsBusinessRequest(Context context, Message message) {
        this(context, null, message);
    }

    public AbsBusinessRequest(Context context, Map<String, String> params) {
        this(context, params, null);
    }

    public AbsBusinessRequest(Context context, Map<String, String> params,
                              Message message) {
        this.mContext = context;
        this.mParams = params;
        this.mMessage = message;
    }


    /**
     * 在默认线程池中执行
     */
    public void execute() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(createRequestHost(), mResponseHandler);

    }

    /**
     * 请求地址
     *
     * @return
     */
    protected abstract String createRequestHost();

    /**
     * 解析服务端返回的数据
     *
     * @param data 服务器返回数据 JsonArray 或者 JsonObject
     * @return
     */
    protected abstract Object parseResponse(Object data) throws Exception;


    /**
     * 注意传入的 {@link #mMessage}保证消息是在UI线程中处理
     *
     * @param error
     */
    public void onFail(int error) {
        sendResponseMessage(ACTION_FAIL, error);
    }

    /**
     * 成功
     *
     * @param response
     */
    public void onSuccess(Header[] headers, Object response) {
        sendResponseMessage(ACTION_SUCCESS, response);
    }

    /**
     * 返回服务端数据给前台
     *
     * @param result   成功 OR 失败
     * @param response
     */
    private void sendResponseMessage(int result, Object response) {

        if (mMessage != null) {
            mMessage.arg1 = result;
            mMessage.obj = response;
            mMessage.sendToTarget();
        }
    }
}
