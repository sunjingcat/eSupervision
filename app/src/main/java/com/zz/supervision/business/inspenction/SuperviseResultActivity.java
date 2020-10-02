package com.zz.supervision.business.inspenction;

import android.widget.Button;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 监督检查结果
 */
public class SuperviseResultActivity extends MyBaseActivity {

    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_ok)
    Button btnControl;
    List<DetailBean> mlist = new ArrayList<>();
    DetailAdapter adapter;
    SuperviseBean.ResposeBean deviceInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_title)
    TextView itemTitle;


    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_result;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(R.layout.item_detail, mlist);
        infoRv.setAdapter(adapter);

        deviceInfo = (SuperviseBean.ResposeBean) getIntent().getSerializableExtra("resposeBean");
        if (deviceInfo != null) {
            showIntent(deviceInfo);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void showIntent(SuperviseBean.ResposeBean lightDevice) {
        if (lightDevice == null) return;
        itemTitle.setText(lightDevice.getCompanyInfo().getOperatorName() + "");
        mlist.clear();
        List<DetailBean> list = new ArrayList<>();
        list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
        list.add(new DetailBean("重点项目", lightDevice.getImportantCount() + ""));
        list.add(new DetailBean("重点项问题数", lightDevice.getImportantProblemCount() + ""));
        list.add(new DetailBean("一般项数", lightDevice.getGeneralCount() + ""));
        list.add(new DetailBean("一般项问题数", lightDevice.getGeneralProblemCount() + ""));
        list.add(new DetailBean("检查结果", lightDevice.getInspectionResult() + ""));


        mlist.addAll(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


}
