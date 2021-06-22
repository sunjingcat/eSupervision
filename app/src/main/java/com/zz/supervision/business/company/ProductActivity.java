package com.zz.supervision.business.company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessProjectBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PLocation;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.business.company.mvp.presenter.ProductAddPresenter;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 *
 */
public class ProductActivity extends MyBaseActivity<Contract.IsetProductAddPresenter> implements Contract.IGetProductAddView {

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
    @BindView(R.id.ig_productionSituation)
    ItemGroup ig_productionSituation;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    ArrayList<String> localPath = new ArrayList<>();
    List<BusinessType> businessTypeList = new ArrayList<>();
    String id ="";
    @Override
    protected int getContentView() {
        return R.layout.activity_product;

    }

    @Override
    public ProductAddPresenter initPresenter() {
        return new ProductAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        itemRvImages.setAdapter(adapter);
        id = getIntent().getStringExtra("id");
        adapter.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                localPath.clear();
                int sever = 0;
                for (int i = 0; i < images.size(); i++) {
                    if (!TextUtils.isEmpty(images.get(i).getPath())) {
                        if (!images.get(i).getPath().contains("zhongzhi")) {
                            localPath.add(images.get(i).getPath());
                        } else {
                            sever++;
                        }
                    }

                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - sever) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(ProductActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
        mPresenter.getDicts("zdgyp_production_situation");
        ig_productionSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1();
            }
        });
        toolbar_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }


    void postData(){
        Map<String,Object> map = new HashMap<>();
        map.put("name",getText(ig_name));
        map.put("category",getText(ig_category));
        map.put("varietySpecModel",getText(ig_varietySpecModel));
        map.put("productionSituation",ig_productionSituation.getSelectValue());
        if (!TextUtils.isEmpty(id)){
            map.put("id",id);
        }
        mPresenter.submitData(map);

    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @Override
    public void showProductInfo(ProductBean data) {

    }

    @Override
    public void showSubmitResult(String id) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ids.add(images.get(i).getId());
        }
        mPresenter.uploadCompanyImgs(id, new Gson().toJson(ids));
    }

    @Override
    public void showResult() {
        finish();

        showToast("提交成功");
    }

    @Override
    public void showPostImage(int position, String id) {
        if (!TextUtils.isEmpty(id)) {
            images.get(position).setId(id);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDicts(String type, List<BusinessType> list) {
        businessTypeList.clear();
        businessTypeList.addAll(list);
    }

    @Override
    public void showImage(List<ImageBack> list) {
        if (list == null) return;
        showLoading("");
        for (ImageBack imageBack : list) {
            String bitmapName = "company_" + imageBack.getId() + ".png";
            String path = getCacheDir() + "/zhongzhi/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                imageBack.setPath(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                imageBack.setPath(s);
            }
        }
        images.clear();
        images.addAll(list);
        adapter.notifyDataSetChanged();
        dismissLoading();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) return;
            /*结果回调*/
            if (requestCode == 1101) {
                //获取选择器返回的数据
                ArrayList<String> selectImages = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                for (String path : selectImages) {
                    if (localPath.contains(path)) {
                        continue;
                    }
                    Luban.with(this)
                            .load(path)
                            .ignoreBy(100)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    String base = "data:image/jpg;base64," + BASE64.imageToBase64(file.getPath());
                                    ImageBack imageBack = new ImageBack();
                                    imageBack.setPath(path);
                                    imageBack.setBase64(base);
                                    images.add(imageBack);
                                    mPresenter.postImage(images.size() - 1, base);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();

                }
            }


        }
    }
    SelectPopupWindows selectPopupWindows;
    void showSelectPopWindow1() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < businessTypeList.size(); i++) {
            list.add(businessTypeList.get(i).getDictLabel());
            list1.add(businessTypeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                ig_productionSituation.setChooseContent(msg,values[index]);
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

}