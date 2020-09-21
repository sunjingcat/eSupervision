package com.zz.supervision.business.login.mvp.presenter;

import com.zz.supervision.bean.UserInfo;
import com.zz.supervision.bean.IpAdress;
import com.zz.supervision.business.login.mvp.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.lib.commonlib.utils.CacheUtility;

import java.util.Map;

public class LoginPresenter extends MyBasePresenterImpl<Contract.IGetLoginView> implements Contract.IsetLoginPresenter {

    public LoginPresenter(Contract.IGetLoginView view) {
        super(view);
    }
    @Override
    public void setAccount(Map<String, Object> params) {
        RxNetUtils.request(getApi(ApiService.class).login(params), new RequestObserver<JsonT<UserInfo>>(this) {
            @Override
            protected void onSuccess(JsonT<UserInfo> login_data) {
                if (login_data.isSuccess()) {
                    CacheUtility.saveToken(login_data.getData().getLoginToken());
                    view.showIntent();
                }else {

                }
            }
            @Override
            protected void onFail2(JsonT<UserInfo> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        },mDialog);

    }



}
