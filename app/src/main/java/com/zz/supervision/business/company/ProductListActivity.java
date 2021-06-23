package com.zz.supervision.business.company;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.business.company.adapter.ProductAdapter;
import com.zz.supervision.business.equipment.adapter.AccessoryAdapter;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 产品
 */
public class ProductListActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ProductAdapter adapter;
    List<ProductBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    String id;
    boolean select;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_list;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(R.layout.item_product, mlist);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (select){
                    mlist.get(position).setSelect(!mlist.get(position).isSelect());
                    adapter.notifyDataSetChanged();
                }else {
                    startActivityForResult(new Intent(ProductListActivity.this, ProductInfoActivity.class).putExtra("id", mlist.get(position).getId() + ""), 1001);
                }
            }
        });
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        id = getIntent().getStringExtra("id");
        select = getIntent().getBooleanExtra("select",false);
        toolbar_subtitle.setText(select?"确定":"新建");
        getDate();

    }
    ArrayList<ProductBean> selectList = new ArrayList<>();
    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        if (select) {
            Intent intent = new Intent();
            selectList.clear();
            for (int i = 0; i < mlist.size(); i++) {
                if (mlist.get(i).isSelect()) {
                    selectList.add(mlist.get(i));
                }
            }
            if (selectList.size() < 1) {
                showToast("请选择产品");
                return;
            }
            intent.putExtra("products", selectList);
            setResult(RESULT_OK, intent);
            finish();
        }else {
            startActivityForResult(new Intent(ProductListActivity.this, ProductActivity.class).putExtra("companyId", id),1001);

        }
    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pagenum = 1;
        getDate();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }

    public void showResult(List<ProductBean> data) {
        if (pagenum == 1) {
            mlist.clear();
        }
        mlist.addAll(data);
        adapter.notifyDataSetChanged();
        if (mlist.size() == 0) {
            llNull.setVisibility(View.VISIBLE);
        } else {
            llNull.setVisibility(View.GONE);
        }
    }

    void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        map.put("companyId", id);
        RxNetUtils.request(getApi(ApiService.class).getZdgypProductInfo( map), new RequestObserver<JsonT<List<ProductBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<ProductBean>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<ProductBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            getDate();
        }
    }
}