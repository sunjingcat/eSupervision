package com.zz.supervision.business.company;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessProjectBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.business.company.mvp.presenter.CompanyAddPresenter;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
    String fieldTime;
    String validDate;
    String businessType = "";
    String businessProject = "";
    String businessProjectText = "";
    List<BusinessType> businessTypeList = new ArrayList<>();
    private  String[] PLANETS = new String[]{"食品销售经营者", "餐饮服务经营者", "单位食堂"};
    SelectPopupWindows selectPopupWindows;
    List<ImageBack> imageBacks = new ArrayList<>();

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
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    if (!BASE64.isBase64(images.get(i))) {
                        localPath.add(images.get(i));
                    } else {

                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - imageBacks.size()) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
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

    @OnClick({R.id.et_businessType, R.id.et_businessProject, R.id.et_endTime, R.id.et_fieldTime, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_businessType:
                UIAdjuster.closeKeyBoard(this);
                String array [] = new String[10];
                String values [] = new String[10];
                for (int i=0;i<businessTypeList.size();i++){
                    array[i]= businessTypeList.get(i).getDictLabel();
                    values[i]= businessTypeList.get(i).getDictValue();
                }
                selectPopupWindows = new SelectPopupWindows(this, array);
                selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etBusinessType.setText(msg);
                        businessType = values[index];
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows.dismiss();
                    }
                });
                break;
            case R.id.et_businessProject:
                if (TextUtils.isEmpty(businessType)) {
                    showToast("请先选择主体业态");
                    return;
                }
                startActivityForResult(new Intent(this, BusinessProjectActivity.class).putExtra("type", businessType), 2001);
                break;
            case R.id.et_endTime:
                DatePickDialog dialog = new DatePickDialog(AddCompanyActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog.setYearLimt(20);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        validDate = time;
                        etEndTime.setText(time);
                    }
                });
                dialog.show();
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

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        fieldTime = time;
                        etFieldTime.setText(time);
                    }
                });
                dialog1.show();
                break;
            case R.id.bt_ok:
                postData();

                break;
        }
    }


    @Override
    public void showCompanyInfo(CompanyBean data) {

    }

    @Override
    public void showSubmitResult(String id) {
        if (this.images.size() > 0) {
            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> needUpload = new ArrayList<>();
            ArrayList<String> base64 = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                if (BASE64.isBase64(images.get(i))) {
                    ids.add(imageBacks.get(i).getId());
                } else {
                    needUpload.add(images.get(i));
                }
            }
            if (needUpload.size() > 0) {
                Luban.with(this)
                        .load(needUpload)
                        .ignoreBy(100)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                base64.add("data:image/jpg;base64," + BASE64.imageToBase64(file.getPath()));
                                if (base64.size() == needUpload.size()) {
                                    String s = new Gson().toJson(base64);
                                    mPresenter.postImage(id, s, ids);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                            }
                        }).launch();
            } else {
                mPresenter.postImage(id, null, ids);
            }
        } else {

            finish();

            showToast("提交成功");
        }

    }

    @Override
    public void showResult() {

    }

    @Override
    public void showPostImage() {
        finish();
        showToast("提交成功");
    }

    @Override
    public void showBusinessType(List<BusinessType> list) {
        if (list!=null) {
            businessTypeList.clear();
            businessTypeList.addAll(list);
        }
    }

    private void postData() {
        Map<String, Object> params = new HashMap<>();
        String operatorName = etOperatorName.getText().toString();
        if (TextUtils.isEmpty(operatorName)) {
            showToast("请填写经营者名称");
            return;
        }
        params.put("operatorName", operatorName);

        String socialCreditCode = etSocialCreditCode.getText().toString();
        if (TextUtils.isEmpty(socialCreditCode)) {
            showToast("请填写社会信用代码");
            return;
        }
        params.put("socialCreditCode", socialCreditCode);

        String licenseNumber = etLicenseNumber.getText().toString();
        if (TextUtils.isEmpty(licenseNumber)) {
            showToast("请填写许可证编号");
            return;
        }
        params.put("licenseNumber", licenseNumber);

        String legalRepresentative = etLegalRepresentative.getText().toString();
        if (TextUtils.isEmpty(legalRepresentative)) {
            showToast("请填写法定代表人");
            return;
        }
        params.put("legalRepresentative", legalRepresentative);

        String address = etAddress.getText().toString();
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }


        String businessPlace = etBusinessPlace.getText().toString();
        if (TextUtils.isEmpty(businessPlace)) {
            showToast("请填写经营场所");
            return;
        }
        params.put("businessPlace", businessPlace);

        if (!TextUtils.isEmpty(businessType)) {
            params.put("businessType", businessType);
        }
        if (TextUtils.isEmpty(validDate)) {
            showToast("请选择有效期至");
            return;
        }
        params.put("validDate", validDate);
        if (!TextUtils.isEmpty(businessProject)) {
            params.put("businessProject", businessProject);
            params.put("businessProjectText", businessProjectText);
        }
        String contact = etContact.getText().toString();
        if (!TextUtils.isEmpty(contact)) {
            params.put("contact", contact);
        }

        String contactInformation = etContactInformation.getText().toString();
        if (TextUtils.isEmpty(contactInformation)) {
            showToast("请填写联系方式");
            return;
        }
        params.put("contactInformation", contactInformation);


        if (TextUtils.isEmpty(fieldTime)) {
            showToast("请选择签发时间");
            return;
        }
        params.put("fieldTime", fieldTime);
        mPresenter.submitData(params);

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
                List<String> showList = new ArrayList<>();
                for (ImageBack imageBack : imageBacks) {
                    showList.add(imageBack.getBase64());
                }
                images.addAll(showList);
                this.images.addAll(images);

                adapter.notifyDataSetChanged();
            }
            if (requestCode == 2001) {
                //获取选择器返回的数据
                ArrayList<BusinessProjectBean> projectBeans = data.getParcelableArrayListExtra("bnpj");
                String str = "";
                String content = "";
                for (int i = 0; i < projectBeans.size(); i++) {
                    if (i == projectBeans.size() - 1) {
                        str = str + projectBeans.get(i).getTitle();
                        content = content + projectBeans.get(i).getValue();
                    } else {
                        str = str + projectBeans.get(i).getTitle() + ",";
                        content = content  + projectBeans.get(i).getValue() + ",";
                    }
                }
                etBusinessProject.setText(str);
                businessProject = content;
                businessProjectText = str;
            }


        }
    }


}
