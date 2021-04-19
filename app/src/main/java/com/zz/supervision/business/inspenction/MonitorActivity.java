package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.MonitorBean;
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
    @BindView(R.id.ig_illegalActivity)
    ItemGroup igIllegalActivity;
    @BindView(R.id.ig_lawContent)
    ItemGroup igLawContent;
    @BindView(R.id.ig_accordContent)
    ItemGroup igAccordContent;
    @BindView(R.id.ig_reformMeasure)
    ItemGroup igReformMeasure;
    @BindView(R.id.bg)
    LinearLayout bg;
    String recordId;
    @BindView(R.id.bt_ok)
    Button btOk;

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
                    igIllegalActivity.setChooseContent(data.getIllegalActivity());
                    igLawContent.setChooseContent(data.getLawContent());
                    igAccordContent.setChooseContent(data.getAccordContent());
                    igReformMeasure.setChooseContent(data.getReformMeasure());
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
        Map<String,Object> map = new HashMap<>();
        map.put("illegalActivity",getText(igIllegalActivity));
        map.put("ig_lawContent",getText(igLawContent));
        map.put("accordContent",getText(igAccordContent));
        map.put("reformMeasure",getText(igReformMeasure));
        RxNetUtils.request(getApi(ApiService.class).postDeviceByCheck(recordId,map), new RequestObserver<JsonT<MonitorBean>>(this) {
            @Override
            protected void onSuccess(JsonT<MonitorBean> jsonT) {
                if (jsonT.isSuccess()) {
                    startActivity(new Intent(MonitorActivity.this, ShowDocActivity.class).putExtra("id", recordId).putExtra("tinspectSheetType", 2).putExtra("tinspectType", 8));
                }
            }

            @Override
            protected void onFail2(JsonT<MonitorBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }


    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                finish();
                break;
            case R.id.bt_ok:
                postData();
               break;
        }
    }
}
