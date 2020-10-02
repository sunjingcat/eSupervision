package com.zz.supervision.business.company;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.adapter.ImageItemAdapter;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.ArrayList;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class CompanyInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    TextView etOperatorName;
    @BindView(R.id.et_socialCreditCode)
    TextView etSocialCreditCode;
    @BindView(R.id.et_licenseNumber)
    TextView etLicenseNumber;
    @BindView(R.id.et_legalRepresentative)
    TextView etLegalRepresentative;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.et_businessPlace)
    TextView etBusinessPlace;
    @BindView(R.id.et_businessType)
    TextView etBusinessType;
    @BindView(R.id.et_businessProject)
    TextView etBusinessProject;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.et_contact)
    TextView etContact;
    @BindView(R.id.et_contactInformation)
    TextView etContactInformation;
    @BindView(R.id.et_fieldTime)
    TextView etFieldTime;
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String fieldTime;
    String validDate;
    String businessType = "";
    String id;
    CompanyBean companyBean;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_info;
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

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }


    public void showCompanyInfo(CompanyBean data) {
        etOperatorName.setText(data.getOperatorName() + "");
        etSocialCreditCode.setText(data.getSocialCreditCode() + "");
        etLicenseNumber.setText(data.getLicenseNumber() + "");
        etLegalRepresentative.setText(data.getLegalRepresentative() + "");
        etAddress.setText(data.getAddress() + "");
        etBusinessPlace.setText(data.getBusinessPlace() + "");
        etBusinessType.setText(data.getBusinessTypeText() + "");
        etBusinessProject.setText(data.getBusinessProjectText() + "");
        businessType = data.getBusinessType();
        validDate = data.getValidDate();
        etEndTime.setText(data.getValidDate() + "");
        etContact.setText(data.getContact() + "");
        etContactInformation.setText(data.getContactInformation() + "");
        etFieldTime.setText(data.getFieldTime() + "");
        fieldTime = data.getFieldTime();
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getCompanyInfo(id), new RequestObserver<JsonT<CompanyBean>>(this) {
            @Override
            protected void onSuccess(JsonT<CompanyBean> jsonT) {
                companyBean = jsonT.getData();
                showCompanyInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<CompanyBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        if (companyBean ==null) return;
        startActivity(new Intent(this, XCHZFActivity.class).putExtra("company",companyBean));
    }
}
