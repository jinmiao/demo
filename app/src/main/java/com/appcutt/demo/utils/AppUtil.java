package com.appcutt.demo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by ouyangjinmiao on 5/6/15.
 */
public class AppUtil {

    private static Context mAppContext = null;

    /**
     * 本地消息管理，采用LocalBroadcast比全局广播更高效.
     */
    private static LocalBroadcastManager mLocalBroadcastManager;

    private AppUtil() {
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public static void setAppContext(Context context) {
        mAppContext = context;
    }

    /**
     * 版本号, 例如：1.0.0
     *
     * @param context
     * @return
     */
    public static final String getAppVersion(Context context) {

        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName; // 版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 版本号，例如：1.0
     */
    public static final String getAppVersionForUser(Context context) {

        String ver = getAppVersion(context);

        if (ver == null || !ver.endsWith("0")) {
            return ver;
        }

        String[] vers = ver.split("\\.");
        if (vers != null && vers.length > 1) {
            ver = vers[0] + "." + vers[1];
        }
        return ver;
    }

    /**
     * 本地广播管理
     *
     * @return
     */
    public static LocalBroadcastManager getLocalBroadcastManager() {

        if (getAppContext() == null) {
//            logger.e("App Context is null");
        }

        if (mLocalBroadcastManager == null) {
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(getAppContext());
        }

        return mLocalBroadcastManager;
    }
}
