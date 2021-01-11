package com.zz.supervision;

import android.content.Context;
import android.content.Intent;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.http.utils.ToastUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EventBusSimpleInfo;
import com.zz.supervision.business.company.CompanyListActivity;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.business.mine.MineActivity;
import com.zz.supervision.business.record.CheckListActivity;
import com.zz.supervision.business.record.RecordMainActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.UpdateManager;

import org.cups4j.CupsClient;
import org.cups4j.CupsPrinter;
import org.cups4j.PrintJob;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class MainActivity extends MyBaseActivity {

    private long mExitTime = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        new UpdateManager(this).checkUpdate();
    }

    @Override
    protected void initToolBar() {

    }

    @OnClick({R.id.main_group_1, R.id.main_group_2, R.id.main_group_3, R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.main_group_1:

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, XCHZFActivity.class);
                startActivity(intent);

                break;
            case R.id.main_group_2:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, CompanyListActivity.class);
                startActivity(intent1);

                break;

            case R.id.main_group_3:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, RecordMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.toolbar_subtitle:
                startActivity(new Intent(MainActivity.this, MineActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {//
                ToastUtils.showToast("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSuccessComment(EventBusSimpleInfo event) {

        String info = event.getStringData();
        if ("putCid".equals(info)) {
//            putClientId();
        }
    }

    public void putClientId() {
        Map<String, Object> map = new HashMap<>();
        map.put("cId", CacheUtility.spGetOut("cId", ""));
        RxNetUtils.request(getApi(ApiService.class).putClientId(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT login_data) {
                if (login_data.isSuccess()) {

                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
            }
        }, null);
    }

    void printer(String host,String post,String path)  {
        try {
            CupsClient cupsClient = new CupsClient(new URL(host+":"+post));
            CupsPrinter printer = cupsClient.getDefaultPrinter();

            InputStream inputStream = new FileInputStream(path);

            PrintJob.Builder builder = new PrintJob.Builder(inputStream).userName("user").copies(1);

            printer.print(builder.build());
        }catch (Exception e){

        }

    }


}
