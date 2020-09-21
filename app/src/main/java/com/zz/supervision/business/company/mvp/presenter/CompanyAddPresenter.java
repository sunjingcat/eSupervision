package com.zz.supervision.business.company.mvp.presenter;


import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.bean.UserBasicBean;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 77 on 2018/8/8.
 */

public class CompanyAddPresenter extends MyBasePresenterImpl<Contract.IGetCompanyAddView> implements Contract.IsetCompanyAddPresenter {

    public CompanyAddPresenter(Contract.IGetCompanyAddView view) {
        super(view);
    }

    @Override
    public void getData(String url, Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).getCompanyInfoList("", map), new RequestObserver<JsonT<CompanyBean>>(this) {
//            @Override
//            protected void onSuccess(JsonT<CompanyBean> jsonT) {
//                view.showPatrolInfo(jsonT.getData());
//            }
//            @Override
//            protected void onFail2(JsonT<CompanyBean> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        },mDialog);
    }

    @Override
    public void submitData(Map<String, Object> map) {
        RxNetUtils.request(getApi(ApiService.class).poatCompanyInfo(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                view.showResult();
                view.showToast(jsonT.getMessage());
            }
            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                view.showToast(stringJsonT.getMessage());
            }
        },mDialog);
    }


    @Override
    public void uploadImage(List<MultipartBody.Part> imgs) {

        RxNetUtils.request(getApi(ApiService.class).upload(imgs), new RequestObserver<JsonT<ImageBean>>(this) {

            @Override
            protected void onSuccess(JsonT<ImageBean> jsonT) {

                view.showImage(jsonT.getData().getImgName());
            }

            @Override
            protected void onFail(String message) {
                super.onFail(message);
                view.onError();
            }
        },mDialog);

    }
}

