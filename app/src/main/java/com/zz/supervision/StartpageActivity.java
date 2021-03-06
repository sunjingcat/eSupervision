package com.zz.supervision;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.login.LoginActivity;
import com.zz.lib.commonlib.utils.CacheUtility;
import com.zz.lib.core.ui.mvp.BasePresenter;


/**
 * 启动页
 */
public class StartpageActivity extends MyBaseActivity implements View.OnClickListener {
    private TextView skip;
    CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_startpage;
    }

    private void initSeconds() {
        /** 倒计时60秒，一次1秒 */
        timer = new CountDownTimer(3 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                skip.setText("跳过" + millisUntilFinished / 1000 + "s");

            }

            @Override
            public void onFinish() {
                skip.setText("跳过" + "0s");
//                CacheUtility.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiIyIiwiaXNfZmlyc3RfbG9naW4iOiJmYWxzZSIsImJ1aWxkaW5nX2N1cnJlbnRfY29uc3RydWN0aW9uX3NpdGVfbmFtZSI6IuWkqeeJqeacquadpeWfjiIsImJ1aWxkaW5nX3VzZXJfdHlwZSI6IjEiLCJzY29wZSI6ImJ1aWxkaW5ncyIsImJ1aWxkaW5nX2N1cnJlbnRfY29uc3RydWN0aW9uX3NpdGVfY29kZSI6IjIwMTkwMTI0MDEiLCJ1dWlkIjoiMTczNTcyMDlhZWFhNDkxMWIzMDJhZWE2ZjIyODAyNDkifQ.hpLEIw0dX2gdyhXnA7qS5Jbjjc3Eo1ta50k8B1tEIEfLofjG_9hMGVNiH7eRnu02rJ4H6TuDgSEBP5YOyYWZ_w");
//                CacheUtility.spSave(CacheUtility.KEY_PROGRAM_CODE,"2019012401");
                String token = CacheUtility.getToken();
                if (TextUtils.isEmpty(CacheUtility.getToken())) {
                    startActivity(new Intent(StartpageActivity.this, LoginActivity.class));
                } else {

                    startActivity(new Intent(StartpageActivity.this, MainActivity.class));

                }

                finish();
            }
        }.start();
    }

    @Override
    protected void initView() {
        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(this);
        initSeconds();
    }

    @Override
    protected void initToolBar() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:

                if (TextUtils.isEmpty(CacheUtility.getToken())) {
                    startActivity(new Intent(StartpageActivity.this, LoginActivity.class));
                } else {

                    startActivity(new Intent(StartpageActivity.this, MainActivity.class));

                }
                if (timer != null) {
                    timer.cancel();
                }

                finish();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
