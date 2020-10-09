package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.widget.SignatureView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
                Bitmap imageBitmap = mView.getCachebBitmap();
                String path = BASE64.saveBitmap(imageBitmap);
                Luban.with(this)
                        .load(path)
                        .ignoreBy(64)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                Intent intent= new Intent();
                                intent.putExtra("sign",file.getAbsoluteFile());
                                setResult(RESULT_OK,intent);
                                finish();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();



                break;
            case R.id.btn_clear:
                mView.clear();

                break;
        }
    }
}
