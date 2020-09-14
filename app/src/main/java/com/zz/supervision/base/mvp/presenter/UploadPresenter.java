package com.zz.supervision.base.mvp.presenter;


import com.zz.supervision.base.mvp.Contract;
import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.List;

import okhttp3.MultipartBody;

public class UploadPresenter extends MyBasePresenterImpl<Contract.IGetUploadView> implements Contract.IsetUploadPresenter {

    public UploadPresenter(Contract.IGetUploadView view) {
        super(view);
    }


    @Override
    public void uploadImage(List<MultipartBody.Part> imgs) {
        RxNetUtils.request(getApi(ApiService.class).upload(imgs), new RequestObserver<JsonT<ImageBean>>(this) {

            @Override
            protected void onSuccess(JsonT<ImageBean> jsonT) {
                view.showImage(jsonT.getData().getImageUrl());
            }


        },mDialog);
    }
}
