package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.MonitorBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.widget.ItemGroup;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class MonitorActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_illegalActivity)
    EditText igIllegalActivity;
    @BindView(R.id.et_lawContent)
    EditText igLawContent;
    @BindView(R.id.et_accordContent)
    EditText igAccordContent;
    @BindView(R.id.et_reformMeasure)
    EditText igReformMeasure;
    @BindView(R.id.bg)
    LinearLayout bg;
    String recordId;
    @BindView(R.id.bt_ok)
    Button btOk;
    int type;

    @Override
    protected int getContentView() {
        return R.layout.activity_monitor;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        recordId = getIntent().getStringExtra("recordId");
        type = getIntent().getIntExtra("type", 0);
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void getData() {
        RxNetUtils.request(getApi(ApiService.class).getDeviceByCheck(recordId), new RequestObserver<JsonT<MonitorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<MonitorBean> jsonT) {
                if (jsonT.isSuccess()) {
                    MonitorBean data = jsonT.getData();
                    if (data == null) return;
                    igIllegalActivity.setText(data.getIllegalActivity() + "");
                    igLawContent.setText(data.getLawContent() + "");
                    igAccordContent.setText(data.getAccordContent() + "");
                    igReformMeasure.setText(data.getReformMeasure() + "");
                }
            }

            @Override
            protected void onFail2(JsonT<MonitorBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    void postData() {
        RxNetUtils.request(getApi(ApiService.class).postDeviceByCheck(recordId, getText(igIllegalActivity),getText(igLawContent),getText(igAccordContent),getText(igReformMeasure)), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                startActivity(new Intent(MonitorActivity.this, ShowDocActivity.class).putExtra("id", recordId).putExtra("type", type).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type).putExtra("read", 1));

            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }




    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                finish();
                break;
            case R.id.bt_ok:
//                completeData();
                break;
            case R.id.bt_print:
                postData();
                break;
        }
    }
}
