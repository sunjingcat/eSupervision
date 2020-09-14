package com.zz.supervision;

import android.Manifest;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.EventBusSimpleInfo;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.UpdateManager;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.core.http.utils.ToastUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getCApi;

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
        PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionUtils.OnPermissionChangedListener() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied() {

            }
        });
        new UpdateManager(this).checkUpdate();
    }

    @Override
    protected void initToolBar() {

    }

    @OnClick({R.id.main_group_1, R.id.main_group_2, R.id.main_group_3, R.id.main_group_4, R.id.main_group_5, R.id.main_group_6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_group_1:
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent();
//                        intent.setClass(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onDenied() {

                    }
                });

                break;
            case R.id.main_group_2:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_3:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_4:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_5:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.main_group_6:
                showToast("暂未开放，敬请期待");
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
            putClientId();
        }
    }
    public void putClientId() {
        Map<String,Object> map = new HashMap<>();
        map.put("cId", CacheUtility.spGetOut("cId",""));
        RxNetUtils.request(getCApi(ApiService.class).putClientId(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT login_data) {
                if (login_data.isSuccess()) {

                }else {

                }
            }
            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
            }
        }, null);
    }

}
