package com.appcutt.demo.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import com.appcutt.demo.utils.Constants;
import com.appcutt.demo.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

/**
 * Created by ouyangjinmiao on 18/5/15.
 */
public class BaseActivity extends Activity {

    protected Handler mRequestHandler = new ActivityRequestHandler(this);

    /**
     * 仅用于接受应用退出广播，程序退出时有机会做一些必要的清理工作
     */
    private BroadcastReceiver mBaseBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            // 退出应用
            if (Constants.ActionConstants.ACTION_EXIT_APP.equals(action)) {
                BaseActivity.super.finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter();

        // 公共广播
        intentFilter.addAction(Constants.ActionConstants.ACTION_EXIT_APP); // 退出应用

        // 窗口自定义广播
        String[] actionArray = getIntentFilterActions();
        if (actionArray != null && actionArray.length > 0) {
            for (int i = 0; i < actionArray.length; i++) {
                intentFilter.addAction(actionArray[i]);
            }
        }

        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        SystemUtil.getLocalBroadcastManager(this)
                .registerReceiver(mBaseBroadcastReceiver, intentFilter);
    }

    /**
     * 友盟 session的统计
     * 正确集成如下代码，才能够保证获取正确的新增用户、活跃用户、启动次数、使用时长等基本数据。
     * 在每个Activity的
     * onResume方法中调用 MobclickAgent.onResume(Context),
     * onPause方法中调用 MobclickAgent.onPause(Context)
     */
    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        MobclickAgent.onPause(this);
    }

    /**
     * 广播监听
     *
     * @return
     */
    protected String[] getIntentFilterActions() {
        return null;

    }

    /**
     * 处理服务器成功返回
     *
     * @param what     请求标识号
     * @param response 响应数据
     */
    protected void onSuccess(int what, Object response) {
    }

    /**
     * 处理服务器错误返回
     *
     * @param what  请求标识号
     * @param error 封装错误信息
     */
    protected void onFail(int what, int error) {
    }

    private static class ActivityRequestHandler extends RequestHandler {

        private WeakReference<BaseActivity> weakActivity;

        public ActivityRequestHandler(BaseActivity activity) {
            weakActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        protected void onSuccess(int what, Object response) {

            if (weakActivity.get() == null || weakActivity.get().isFinishing()) {
                return;
            }

            weakActivity.get().onSuccess(what, response);
        }

        @Override
        protected void onFail(int what, int error) {

            if (weakActivity.get() == null || weakActivity.get().isFinishing()) {
                return;
            }

            weakActivity.get().onFail(what, error);
        }
    }
}
