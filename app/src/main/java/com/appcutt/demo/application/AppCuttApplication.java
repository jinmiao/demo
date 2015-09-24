package com.appcutt.demo.application;

import android.app.Application;
import com.appcutt.demo.image.loader.ImageLoaderUtil;
import com.appcutt.demo.utils.AppUtil;

/**
 * Created by ouyangjinmiao on 4/6/15.
 */
public class AppCuttApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtil.setAppContext(this);

        // 初始化图片缓存
        ImageLoaderUtil.initImageLoader(this);
    }
}
