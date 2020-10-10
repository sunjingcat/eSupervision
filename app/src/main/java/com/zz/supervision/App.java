package com.zz.supervision;

import android.os.Handler;
import android.util.Log;

import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.tencent.smtt.sdk.QbSdk;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.net.URLs;
import com.zz.lib.core.BaseApplication;

import com.orhanobut.logger.Logger;


public class App extends BaseApplication {
    public static AppCompatActivity context;
    public static App mApplication;

    private static Handler mHandler;  // 全局的Handler 可以防止内存泄露

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mApplication = this;
        mHandler = new Handler();
        RxNetUtils.init(this);
        RxNetUtils.defaultConfig(URLs.LMX);
        if (!BuildConfig.LOG_DEBUG) {
            Logger.clearLogAdapters();
        }
        SDKInitializer.initialize(this);

        SDKInitializer.setCoordType(CoordType.BD09LL);

        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {

            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.v("####################",b+"");
            }
        });
    }

    public static App getmApplication() {
        return mApplication;
    }

    /**
     * 全局的Handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

}
