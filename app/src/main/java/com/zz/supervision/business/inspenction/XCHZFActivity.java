package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.CompanyListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XCHZFActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_company)
    TextView etCompany;
    @BindView(R.id.et_Type)
    TextView etType;
    @BindView(R.id.et_people)
    TextView etPeople;
    @BindView(R.id.et_startTime)
    TextView etStartTime;
    @BindView(R.id.ed_cause)
    EditText edCause;
    @BindView(R.id.bt_ok)
    TextView btOk;

    @Override
    protected int getContentView() {
        return R.layout.activity_xchzf;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xchzf);
        ButterKnife.bind(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }

    @OnClick({R.id.et_company, R.id.et_Type, R.id.et_people, R.id.et_startTime, R.id.ed_cause, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_company:
                startActivityForResult(new Intent(this, CompanyListActivity.class),1001);
                break;
            case R.id.et_Type:

                break;
            case R.id.et_people:
                break;
            case R.id.et_startTime:
                break;
            case R.id.ed_cause:
                break;
            case R.id.bt_ok:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1001){
                 CompanyBean company= data.getParcelableExtra("company");
                 if (company !=null){
                     etCompany.setText(company.getOperatorName()+"");
                 }
            }
        }
    }
}
