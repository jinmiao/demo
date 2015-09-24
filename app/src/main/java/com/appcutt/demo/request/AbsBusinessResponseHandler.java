package com.appcutt.demo.request;

import com.appcutt.libs.net.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 解析业务返回数据
 * 
 * Created by ouyangjinmiao on 18/5/15.
 */
public abstract class AbsBusinessResponseHandler extends JsonHttpResponseHandler {

    /**
     * 成功
     *
     * @param headers
     * @param jsonObj
     */
    protected abstract void onRequestSuccess(int statusCode, Header[] headers, Object jsonObj);

    /**
     * 失败
     *
     * @param headers
     * @param statusCode
     */
    protected abstract void onRequestFail(int statusCode, Header[] headers);

    /**
     * 解析服务器返回的数据
     *
     * @param jsonObj
     * @return
     */
    protected abstract Object parseResponse(Object jsonObj) throws Exception;

    @Override
    public final void onSuccess(int statusCode, Header[] headers, JSONObject jsonObj) {

        try {

            Object response = parseResponse(jsonObj);

            if (response != null) {
                onRequestSuccess(statusCode, headers, response);
            } else {
                sendRequestFail(statusCode, headers);
            }

        } catch (Exception e) {
            sendRequestFail(statusCode, headers);
        }
    }

    @Override
    public final void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {

        try {

            Object response = parseResponse(jsonArray);

            if (response != null) {
                onRequestSuccess(statusCode, headers, response);
            } else {
                sendRequestFail(statusCode, headers);
            }

        } catch (Exception e) {
            sendRequestFail(statusCode, headers);
        }
    }

    @Override
    public final void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        sendRequestFail(statusCode, headers);
    }

    /**
     * 这个方法统一输出错误日志，并上报错误日志
     *
     * @param headers
     * @param statusCode
     */
    private void sendRequestFail(int statusCode, Header[] headers) {
        onRequestFail(statusCode,headers);
    }
    
}
