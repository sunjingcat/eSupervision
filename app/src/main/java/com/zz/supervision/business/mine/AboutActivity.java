package com.zz.supervision.business.mine;

import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.Version;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.UpdateManager;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 关于
 */
public class AboutActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_icon)
    ImageView tvIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.bt_update)
    Button btUpdate;

    @Override
    protected int getContentView() {
        return R.layout.activity_about;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvVersion.setText(getVersioName());
//        tvInfo.setText();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @OnClick(R.id.bt_update)
    public void onViewClicked() {
        new UpdateManager(this).checkUpdate();
    }
    private String getVersioName() {
        try {
          String  versionName = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return "";
    }

    //获取app版本
    private int getVersionCode() {
        try {
            return getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return 0;
    }
}
