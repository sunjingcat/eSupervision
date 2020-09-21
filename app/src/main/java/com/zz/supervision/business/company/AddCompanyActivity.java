package com.zz.supervision.business.company;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.business.company.mvp.presenter.CompanyAddPresenter;
import com.zz.supervision.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddCompanyActivity extends MyBaseActivity<Contract.IsetCompanyAddPresenter> implements Contract.IGetCompanyAddView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    EditText etOperatorName;
    @BindView(R.id.et_socialCreditCode)
    EditText etSocialCreditCode;
    @BindView(R.id.et_licenseNumber)
    EditText etLicenseNumber;
    @BindView(R.id.et_legalRepresentative)
    EditText etLegalRepresentative;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_businessPlace)
    EditText etBusinessPlace;
    @BindView(R.id.et_businessType)
    TextView etBusinessType;
    @BindView(R.id.et_businessProject)
    TextView etBusinessProject;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_contactInformation)
    EditText etContactInformation;
    @BindView(R.id.et_fieldTime)
    TextView etFieldTime;
    ArrayList<String> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    long fieldTime;
    @Override
    protected int getContentView() {
        return R.layout.activity_company_add;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        itemRvImages.setAdapter(adapter);
        adapter.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(4) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(images) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddCompanyActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public CompanyAddPresenter initPresenter() {
        return new CompanyAddPresenter(this);
    }

    @OnClick({R.id.et_businessType, R.id.et_businessProject, R.id.et_endTime, R.id.et_fieldTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_businessType:
                break;
            case R.id.et_businessProject:
                break;
            case R.id.et_endTime:
                break;
            case R.id.et_fieldTime:
                DatePickDialog dialog1 = new DatePickDialog(AddCompanyActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog1.setYearLimt(20);
                //设置标题
                dialog1.setTitle("选择时间");
                //设置类型
                dialog1.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog1.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog1.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog1.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        fieldTime = date.getTime();
                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        etFieldTime.setText(time);
                    }
                });
                dialog1.show();
                break;
        }
    }

    @Override
    public void showPatrolInfo(CompanyBean data) {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void showImage(String url) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) return;
            /*结果回调*/
            if (requestCode == 1101) {
                //获取选择器返回的数据
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                if (images.size() > 0) {
                    this.images.clear();
                }
                this.images.addAll(images);

                upload(images);
                adapter.notifyDataSetChanged();
            }


        }
    }
    private void upload(final ArrayList<String> files) {
        final List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (String strfile : files) {
            File file = new File(strfile);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(body);
        }
        mPresenter.uploadImage(parts);
    }
}
