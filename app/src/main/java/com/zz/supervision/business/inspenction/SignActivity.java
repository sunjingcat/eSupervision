package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ImageUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.widget.SignatureView;

import java.sql.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fl_view)
    FrameLayout flView;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_clear)
    Button btnClear;
    private SignatureView mView;
    @Override
    protected int getContentView() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mView = new SignatureView(this);
        flView.addView(mView);
        mView.requestFocus();
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.clear();
            }
        });
        btnOk.setOnClickListener((v) -> {
            Bitmap imageBitmap = mView.getCachebBitmap();
            String path = BASE64.saveBitmap(imageBitmap);
            Intent intent= new Intent();
            intent.putExtra("sign",path);
            setResult(RESULT_OK,intent);
            finish();

        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @OnClick({R.id.btn_ok, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                break;
            case R.id.btn_clear:
                break;
        }
    }
}