package com.appcutt.demo.request;

import android.content.Context;
import android.os.Message;
import com.appcutt.demo.pojo.AdvertiseBean;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ouyangjinmiao on 18/5/15.
 */
public class AdvertiseRequest extends AbsBusinessRequest {

    public AdvertiseRequest(Context context, Map<String, String> params, Message message) {
        super(context, params, message);
    }

    @Override
    protected String createRequestHost() {
        return "http://www.androideng.com/at/test/test.php";
    }

    @Override
    protected Object parseResponse(Object data) throws Exception {

        AdvertiseResult result = new AdvertiseResult();

        JSONObject jsonObject = (JSONObject) data;

        JSONArray jsonArray = jsonObject.optJSONArray("aaData");

        List<AdvertiseBean> advertiseBeans = new ArrayList<AdvertiseBean>();

        for (int i = 0; i < jsonArray.length(); i++) {
            AdvertiseBean bean = new AdvertiseBean();

            JSONObject item = jsonArray.optJSONObject(i);

            bean.photoURL = "http://h.hiphotos.baidu.com/image/pic/item/728da9773912b31b7760b85c8418367adab4e1b3.jpg";

            advertiseBeans.add(bean);
        }

        result.advertiseBeans = advertiseBeans;

        return result;
    }

    public static class AdvertiseResult {

       public List<AdvertiseBean> advertiseBeans;

    }
}
