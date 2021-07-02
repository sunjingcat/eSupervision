package com.zz.supervision.business.company;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.business.company.adapter.ImageItemAdapter;
import com.zz.supervision.business.equipment.AddAccessoryActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.widget.ItemGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 *产品
 */
public class ProductInfoActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.ig_name)
    ItemGroup ig_name;
    @BindView(R.id.ig_category)
    ItemGroup ig_category;
    @BindView(R.id.ig_varietySpecModel)
    ItemGroup ig_varietySpecModel;
    @BindView(R.id.ig_unit)
    ItemGroup ig_unit;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.ig_productionSituation)
    ItemGroup ig_productionSituation;
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String id="";
    ProductBean productBean;
    private CustomDialog customDialog;
    @Override
    protected int getContentView() {
        return R.layout.activity_product_info;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        itemRvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
        toolbar_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ProductInfoActivity.this, ProductActivity.class).putExtra("id",id),1001);
            }
        });
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.Builder builder = new CustomDialog.Builder(ProductInfoActivity.this)
                        .setTitle("提示")
                        .setMessage("确定删除该设备？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(id);
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }
    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getZdgypProductInfo(id), new RequestObserver<JsonT<ProductBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ProductBean> jsonT) {
                productBean = jsonT.getData();
                showInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<ProductBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
    public void showInfo(ProductBean data) {
        if (data == null) return;
        productBean = data;
        ig_name.setChooseContent(data.getName());
        ig_unit.setChooseContent(data.getUnit());
        ig_category.setChooseContent(data.getCategory());
        ig_varietySpecModel.setChooseContent(data.getVarietySpecModel());
        ig_productionSituation.setChooseContent(data.getProductionSituationText(),data.getVarietySpecModel());
        getImage(data.getId());
    }
    public void getImage(String id) {
        RxNetUtils.request(getApi(ApiService.class).getImageBase64("zdgypProduct", id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    showImage(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
    public void showImage(List<ImageBack> list) {
        if (list == null) return;

        List<String> showList = new ArrayList<>();
        for (ImageBack imageBack : list) {
            String bitmapName = "company_" + imageBack.getId() + ".png";
            String path = getCacheDir() + "/zhongzhi/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                showList.add(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                showList.add(s);
            }

        }
        images.clear();
        images.addAll(showList);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (!TextUtils.isEmpty(id)) {
                getData(id);
            }
        }
    }
    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removeProductInfo(id), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                showToast("删除成功");
                finish();
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                ToastUtils.showShort(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

}