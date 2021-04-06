package com.zz.supervision.business.equipment;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.InspectEdit;
import com.zz.supervision.business.equipment.adapter.InspectEditAdapter;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class InspectActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    InspectEditAdapter adapter;
    List<InspectEdit> mlist = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_inspect_add;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mlist.add(new InspectEdit("111"));
        mlist.add(new InspectEdit("222"));
        mlist.add(new InspectEdit("333"));
        adapter = new InspectEditAdapter(R.layout.item_inspect_edit,mlist);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }
}