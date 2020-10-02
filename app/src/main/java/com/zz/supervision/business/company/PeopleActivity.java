package com.zz.supervision.business.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.business.company.adapter.LawEnforcerAdapter;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class PeopleActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    List<LawEnforcerBean> mlist = new ArrayList<>();
    ArrayList<LawEnforcerBean> select = new ArrayList<>();
    LawEnforcerAdapter adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_people_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new LawEnforcerAdapter(R.layout.item_law_enforcer, mlist);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                if (position >= 0) {
                    LawEnforcerBean entity = mlist.get(position);
                    if (entity.isSelect()){
                        entity.setSelect(false);
                        select.remove(entity);
                    }else {
                        if (select.size()>=2)return;
                        entity.setSelect(true);
                        select.add(entity);
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });
        getDate();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        Intent intent = new Intent();
        select.clear();
        for (int i=0;i<mlist.size();i++){
            if (mlist.get(i).isSelect()){
                select.add(mlist.get(i));
            }
        }
        if (select.size()<2){
           showToast("请选择两位执法人");
           return;
        }
        intent.putExtra("select",select);
        setResult(RESULT_OK,intent);
        finish();
    }

    void getDate() {
        RxNetUtils.request(getApi(ApiService.class).getLawEnforcerList(), new RequestObserver<JsonT<List<LawEnforcerBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<LawEnforcerBean>> jsonT) {
                mlist.clear();
                mlist.addAll(jsonT.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onFail2(JsonT<List<LawEnforcerBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
}
