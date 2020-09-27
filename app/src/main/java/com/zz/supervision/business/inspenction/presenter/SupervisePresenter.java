package com.zz.supervision.business.inspenction.presenter;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 77 on 2018/8/8.
 */

public class SupervisePresenter extends MyBasePresenterImpl<Contract.IGetSuperviseView> implements Contract.IsetSupervisePresenter {

    public SupervisePresenter(Contract.IGetSuperviseView view) {
        super(view);
    }

    @Override
    public void getData(String url) {
        RxNetUtils.request(getApi(ApiService.class).getSuperviseList(), new RequestObserver<JsonT<List<SuperviseBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<SuperviseBean>> jsonT) {
                view.showFoodSuperviseList(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<SuperviseBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void submitData(String id,ArrayList<SuperviseBean.PostBean> spxsInspectionPoints) {
        RxNetUtils.request(getApi(ApiService.class).submitSupervise(id,spxsInspectionPoints), new RequestObserver<JsonT>(this) {
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
    public void submitReData(String id, ArrayList<SuperviseBean.PostBean> postBeans) {
        RxNetUtils.request(getApi(ApiService.class).submitSuperviseConfirm(id,postBeans), new RequestObserver<JsonT<SuperviseBean.ResposeConfirmBean>>(this) {
            @Override
            protected void onSuccess(JsonT<SuperviseBean.ResposeConfirmBean> jsonT) {
                view.showReResult(jsonT.getData());
                view.showToast(jsonT.getMessage());
            }
            @Override
            protected void onFail2(JsonT<SuperviseBean.ResposeConfirmBean> stringJsonT) {
                super.onFail2(stringJsonT);
                view.showToast(stringJsonT.getMessage());
            }
        },mDialog);
    }
}
