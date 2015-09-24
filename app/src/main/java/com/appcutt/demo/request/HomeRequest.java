package com.appcutt.demo.request;

import android.content.Context;
import android.os.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyangjinmiao on 18/5/15.
 */
public class HomeRequest extends AbsBusinessRequest {

    public HomeRequest(Context context, Map<String, String> params, Message message) {
        super(context, params, message);
    }

    @Override
    protected String createRequestHost() {
        return "http://www.androideng.com/at/test/test.php";
    }

    @Override
    protected Object parseResponse(Object data) throws Exception {

        List<String> ds = new ArrayList<String>();

        JSONObject result = (JSONObject) data;

        JSONArray aaData = result.optJSONArray("aaData");

        for (int i = 0; i < aaData.length(); i++) {
            String title;

            JSONObject item = aaData.optJSONObject(i);

            title = item.optString("bltnTxt");

            ds.add(title);
        }

        return ds;
    }
}
