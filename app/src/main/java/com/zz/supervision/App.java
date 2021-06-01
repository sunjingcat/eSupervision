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
import com.zz.supervision.print.transfer.ConnectionListener;
import com.zz.supervision.print.utils.ConnectionUtils;
import com.zz.supervision.utils.LocationHelper;


public class App extends BaseApplication {
    public static AppCompatActivity context;
    public static App mApplication;
    private ConnectionListener connListener;
    private int myPort;
    public static double longitude=0.0;
    public static double latitude=0.0;

    private boolean isConnectionListenerRunning = false;
    private static Handler mHandler;  // 全局的Handler 可以防止内存泄露

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        QbSdk.initX5Environment(getApplicationContext(), new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.v("####################","");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                Log.v("####################",b+"");
            }
        });
        mApplication = this;
        mHandler = new Handler();
        RxNetUtils.init(this);
        RxNetUtils.defaultConfig(URLs.LMX);
        if (!BuildConfig.LOG_DEBUG) {
            Logger.clearLogAdapters();
        }
        SDKInitializer.initialize(this);

        SDKInitializer.setCoordType(CoordType.BD09LL);
        myPort = ConnectionUtils.getPort(getApplicationContext());
        connListener = new ConnectionListener(getApplicationContext(), myPort);

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
    public void stopConnectionListener() {
        if (!isConnectionListenerRunning) {
            return;
        }
        if (connListener != null) {
            connListener.tearDown();
            connListener = null;
        }
        isConnectionListenerRunning = false;
    }

    public void startConnectionListener() {
        if (isConnectionListenerRunning) {
            return;
        }
        if (connListener == null) {
            connListener = new ConnectionListener(getApplicationContext(), myPort);
        }
        if (!connListener.isAlive()) {
            connListener.interrupt();
            connListener.tearDown();
            connListener = null;
        }
        connListener = new ConnectionListener(getApplicationContext(), myPort);
        connListener.start();
        isConnectionListenerRunning = true;
    }

    public void startConnectionListener(int port) {
        myPort = port;
        startConnectionListener();
    }

    public void restartConnectionListenerWith(int port) {
        stopConnectionListener();
        startConnectionListener(port);
    }

    public boolean isConnListenerRunning() {
        return isConnectionListenerRunning;
    }

    public int getPort(){
        return myPort;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
